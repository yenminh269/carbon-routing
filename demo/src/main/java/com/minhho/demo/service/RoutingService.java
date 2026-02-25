package com.minhho.demo.service;

import com.minhho.demo.algorithm.DijkstraRouter;
import com.minhho.demo.model.Node;
import com.minhho.demo.model.Path;

public class RoutingService {
    private DijkstraRouter router;
    public Path findRoute(Node start, Node end, CostStrategy strategy){
        return router.findShortestPath(start, end, strategy);
    }
}
