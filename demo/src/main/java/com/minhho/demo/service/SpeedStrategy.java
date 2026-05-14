package com.minhho.demo.service;

import com.minhho.demo.model.Edge;

public class SpeedStrategy implements CostStrategy {
    private final int avgSpeedInKmPerHour;

    public SpeedStrategy(int _avgSpeedInKmPerHour) {
        avgSpeedInKmPerHour = _avgSpeedInKmPerHour;
    }

    @Override
    public double calculate(Edge edge) {
        return edge.distanceInKm() / avgSpeedInKmPerHour;
    }
}
