package com.minhho.demo.config;

import com.minhho.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserService userService){
        return args -> {
            userService.seedRolesAndAdmin();
            System.out.println("Default roles and admin user seeded");
        };
    }
}
