package com.minhho.demo.dto;

public record RouteRequest(String source, String destination, String vehicle,
                           double emissionRate, double avgSpeedInMiles) {}
