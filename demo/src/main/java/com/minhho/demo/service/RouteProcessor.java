package com.minhho.demo.service;

import com.minhho.demo.algorithm.Router;
import com.minhho.demo.model.Edge;
import com.minhho.demo.model.Node;
import com.minhho.demo.model.Path;

import java.util.List;
import java.util.Map;

public class RouteProcessor {
    Router router = null;
    CostStrategy strategy = null;

    public void setRoutingService(Router _router){ router = _router; }

    public void setStrategy(CostStrategy _strategy){ strategy = _strategy; }

    public List<Node> findShortestPath(Node start, Node end, Map<Node, List<Edge>> graph){
        return router.findShortestPath(start, end, graph, strategy);
    }
}
