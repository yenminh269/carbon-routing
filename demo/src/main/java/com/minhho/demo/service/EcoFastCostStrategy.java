package com.minhho.demo.service;

import com.minhho.demo.model.Edge;

public class EcoFastCostStrategy implements CostStrategy{
    double alpha;
    double beta;
    double emissionRate;
    public EcoFastCostStrategy(double alphaMetric, double betaMetric, double initialEmissionRate){
        alpha = alphaMetric;
        beta = betaMetric;
        emissionRate = initialEmissionRate;
    }
    public double calculate(Edge edge){
        if(edge.distanceInKm() == 0){
            return 0;
        }
        double carbon = edge.distanceInKm() * emissionRate;
        double time = edge.distanceInKm() / edge.avgSpeedInKmPerHour();
        return alpha * carbon + beta * time;
    }
}
