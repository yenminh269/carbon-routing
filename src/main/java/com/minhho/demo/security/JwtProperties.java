package com.minhho.demo.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private long accessTokenExpirationMs;
    private long refreshTokenExpirationMs;
    private String secret;

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

    public String getSecret(){
        return secret;
    }

    public void setSecret(String key){
        secret = key;
    }

}
