package com.minhho.demo.security;

import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    private final JwtProperties jwtProperties;
    
    public JwtService(JwtProperties jwtProperties){
        this.jwtProperties = jwtProperties;
    }

}
