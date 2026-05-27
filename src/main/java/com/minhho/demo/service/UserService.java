package com.minhho.demo.service;

import com.minhho.demo.entity.User;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    User createUser(String username, String password, Set<String> roles);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    void seedRolesAndAdmin();
}
