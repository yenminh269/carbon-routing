package com.minhho.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/public/hello")
    public String publicHello(){
        return "Public access";
    }

    @GetMapping("/secure/hello")
    public String secureHello(){
        return "Authenticated access";
    }
}
