package com.minhho.demo.dto;

import java.util.List;

public record RouteResponse(List<String> path, List<EdgeResponse> edges,
                            double totalDistance, double totalCarbon, double totalTime) {}
