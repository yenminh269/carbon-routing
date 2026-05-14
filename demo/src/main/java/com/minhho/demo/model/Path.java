package com.minhho.demo.model;

import java.util.List;

public record Path(List<Node> nodes, double totalCost) {}
