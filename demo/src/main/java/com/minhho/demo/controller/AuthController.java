package com.minhho.demo.controller;

import com.minhho.demo.dto.JwtResponse;
import com.minhho.demo.dto.LoginRequest;
import com.minhho.demo.entity.RefreshToken;
import com.minhho.demo.entity.User;
import com.minhho.demo.repository.RefreshTokenRepository;
import com.minhho.demo.security.JwtService;
import com.minhho.demo.security.RefreshTokenService;
import com.minhho.demo.service.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    public AuthController(
            AuthenticationManager authManager,
            JwtService jwtService,
            RefreshTokenRepository refreshTokenRepo,
            RefreshTokenService refreshTokenService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepo;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        String accessToken = jwtService.generateAccessToken((UserDetails) auth.getPrincipal());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getUsername());

        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken.getToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request){
        String requestToken = request.get("refreshToken");
        RefreshToken refreshToken = refreshTokenRepository.findByToken(requestToken)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        User user = refreshToken.getUser();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        String newAccessToken = jwtService.generateAccessToken(userDetails);
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        refreshTokenService.deleteByUsername(userDetails.getUsername());
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
