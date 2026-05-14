package com.minhho.demo.service;

import com.minhho.demo.model.Edge;

public class CarbonStrategy implements CostStrategy {
    private final double emissionRate;

    public CarbonStrategy(double emissionRate) {
        this.emissionRate = emissionRate;
    }

    @Override
    public double calculate(Edge edge) {
        return edge.distanceInKm() * emissionRate;
    }
}
