package com.minhho.demo.security;

import com.minhho.demo.entity.RefreshToken;
import com.minhho.demo.entity.User;
import com.minhho.demo.repository.RefreshTokenRepository;
import com.minhho.demo.repository.UserRepository;
import com.minhho.demo.service.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtProperties jwtProperties;
    private final JwtService jwtService;

    public RefreshTokenService(
            JwtService jwtService,
            JwtProperties jwtProperties,
            RefreshTokenRepository refreshRepo,
            UserRepository userRepo){
        this.refreshTokenRepository = refreshRepo;
        this.userRepository = userRepo;
        this.jwtProperties = jwtProperties;
        this.jwtService = jwtService;
    }

    public RefreshToken createRefreshToken(String username){
        User user = (userRepository.findByUsername(username).orElseThrow());
        refreshTokenRepository.deleteByUserId(user.getId());
        RefreshToken token = new RefreshToken(); //rotate
        token.setUser(user);
        token.setExpiryDate(Instant.now().plusMillis(jwtProperties.getRefreshTokenExpirationMs()));
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        token.setToken(jwtService.generateRefreshToken(userDetails));
        return refreshTokenRepository.save(token);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token is expired");
        }
        return refreshToken;
    }
}
