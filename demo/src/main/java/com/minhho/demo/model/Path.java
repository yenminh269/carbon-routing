package com.minhho.demo.model;

import java.util.List;

public record Path(List<Node> nodes, List<Edge> edges, double totalCost, double totalCarbon, double totalTime) {}
