package com.minhho.demo.service;

import com.minhho.demo.model.Edge;

public class EcoFastCostStrategy implements CostStrategy{
    double alpha;
    double beta;
    double emissionRate;
    public EcoFastCostStrategy(double a, double b, double e){
        alpha = a;
        beta = b;
        emissionRate = e;
    }
    public double calculate(Edge edge){
        if(edge.distance() == 0){
            return 0;
        }
        double carbon = edge.distance() * emissionRate;
        double time = edge.distance() / edge.avgSpeed();
        return alpha * carbon + beta * time;
    }
}
