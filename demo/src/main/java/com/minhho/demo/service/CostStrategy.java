package com.minhho.demo.service;

import com.minhho.demo.model.Edge;

public interface CostStrategy {
    double calculate(Edge edge);
}
