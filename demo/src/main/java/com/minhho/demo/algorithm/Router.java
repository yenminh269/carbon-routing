package com.minhho.demo.algorithm;

import com.minhho.demo.model.Edge;
import com.minhho.demo.model.Node;
import com.minhho.demo.model.Path;
import com.minhho.demo.service.CostStrategy;

import java.util.List;
import java.util.Map;

public interface Router {
    Path findShortestPath(Node start, Node end, Map<Node, List<Edge>> graph);
}
