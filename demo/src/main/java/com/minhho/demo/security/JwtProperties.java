package com.minhho.demo.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private long accessTokenExpirationMs;
    private long refreshTokenExpirationMs;

    public long getAccessTokenExpirationMs(){
        return accessTokenExpirationMs;
    }

    public long getRefreshTokenExpirationMs(){
        return refreshTokenExpirationMs;
    }

    public void setAccessTokenExpirationMs(long accessTokenExpirationMs){
        this.accessTokenExpirationMs = accessTokenExpirationMs;
    }

    public void setRefreshTokenExpirationMs(long refreshTokenExpirationMs){
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

}
