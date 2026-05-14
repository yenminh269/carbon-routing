package com.minhho.demo.service;

import com.minhho.demo.model.Edge;

public interface CostStrategy {
    public double calculate(Edge edge);
}
