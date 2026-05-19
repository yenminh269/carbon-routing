package com.minhho.demo.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private Instant expire;

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setExpiryDate(Instant expire){
        this.expire = expire;
    }

    public Instant getExpiryDate(){
        return expire;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

}
