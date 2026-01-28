package com.minhho.demo.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;

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


}
