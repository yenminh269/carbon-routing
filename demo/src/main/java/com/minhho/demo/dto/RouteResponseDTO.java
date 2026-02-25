package com.minhho.demo.dto;

import java.util.List;

public record RouteResponseDTO(List<Integer> nodeId, double totalCArbon, double totalTime) {}
