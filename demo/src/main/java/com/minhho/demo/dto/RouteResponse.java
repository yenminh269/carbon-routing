package com.minhho.demo.dto;

import java.util.List;

public record RouteResponse(List<String> path, double distance, double carbon, double time) {}
