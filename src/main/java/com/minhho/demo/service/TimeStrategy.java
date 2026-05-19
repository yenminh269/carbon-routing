package com.minhho.demo.service;

import com.minhho.demo.model.Edge;
import com.minhho.demo.model.Vehicle;

public class TimeStrategy implements CostStrategy {
    private final Vehicle vehicle;

    public TimeStrategy(Vehicle _vehicle) {
        vehicle = _vehicle;
    }

    @Override
    public double calculate(Edge edge) {
        return edge.distanceInMiles() / vehicle.avgSpeedInMiles();
    }
}
