package com.minhho.demo.service;

import com.minhho.demo.algorithm.Router;
import com.minhho.demo.model.Edge;
import com.minhho.demo.model.Node;
import com.minhho.demo.model.Path;
import com.minhho.demo.model.Vehicle;

import java.util.List;
import java.util.Map;

public class RouteProcessor {
    Router router = null;
    CostStrategy strategy = null;

    public void setRoutingService(Router _router){ router = _router; }

    public void setStrategy(CostStrategy _strategy){ strategy = _strategy; }

    public Path findShortestPath(String start, String end, Map<String, List<Edge>> graph, Vehicle vehicle){
        return router.findShortestPath(start, end, strategy, vehicle);
    }
}
