package com.minhho.demo.service;

import com.minhho.demo.algorithm.Router;
import com.minhho.demo.model.Edge;
import com.minhho.demo.model.Node;
import com.minhho.demo.model.Path;

import java.util.List;
import java.util.Map;

public class CostStrategy {
    private Router router;
    double alpha;
    double beta;
    double emissionRate;

//    public CostStrategy(double alphaMetric, double betaMetric, double initialEmissionRate){
//        alpha = alphaMetric;
//        beta = betaMetric;
//        emissionRate = initialEmissionRate;
//    }
//
//    public double calculate(Edge edge){
//        if(edge.distanceInKm() == 0){
//            return 0;
//        }
//        double carbon = edge.distanceInKm() * emissionRate;
//        double time = edge.distanceInKm() / edge.avgSpeedInKmPerHour();
//        return alpha * carbon + beta * time;
//    }

    public void setRoutingService(Router requestRouter){
        router = requestRouter;
    }

    public Path findRoute(Node start, Node end, Map<Node, List<Edge>> graph){
        return router.findShortestPath(start, end, graph);
    }
}
