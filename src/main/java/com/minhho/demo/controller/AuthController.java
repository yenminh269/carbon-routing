package com.minhho.demo.controller;

import com.minhho.demo.dto.JwtResponse;
import com.minhho.demo.dto.LoginRequest;
import com.minhho.demo.entity.RefreshToken;
import com.minhho.demo.entity.User;
import com.minhho.demo.repository.RefreshTokenRepository;
import com.minhho.demo.security.JwtService;
import com.minhho.demo.security.RefreshTokenService;
import com.minhho.demo.service.UserDetailsImpl;
import com.minhho.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public AuthController(
            AuthenticationManager authManager,
            JwtService jwtService,
            RefreshTokenRepository refreshTokenRepo,
            RefreshTokenService refreshTokenService,
            UserService userService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepo;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request){
        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username is already taken!"));
        }
        userService.createUser(request.getUsername(), request.getPassword(), Set.of("EMPLOYEE"));
        return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
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

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication){
        if (authentication == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Unauthorized"));
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(Map.of(
            "username", userDetails.getUsername(),
            "roles", userDetails.getAuthorities().stream()
                    .map(org.springframework.security.core.GrantedAuthority::getAuthority)
                    .toList()
        ));
    }
}
