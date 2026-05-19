package com.minhho.demo.service;

import com.minhho.demo.model.Edge;
import com.minhho.demo.model.Vehicle;

public class CarbonStrategy implements CostStrategy {
    private final Vehicle vehicle;

    public CarbonStrategy(Vehicle _vehicle) {
        vehicle = _vehicle;
    }

    @Override
    public double calculate(Edge edge) {
        return edge.distanceInMiles() * vehicle.emissionRate();
    }
}
