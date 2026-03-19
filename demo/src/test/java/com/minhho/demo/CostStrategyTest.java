package com.minhho.demo;

import com.minhho.demo.algorithm.Router;
import com.minhho.demo.model.Edge;
import com.minhho.demo.model.Node;
import com.minhho.demo.service.CostStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CostStrategyTest {
    Map<Node, List<Edge>> graph = new HashMap<>();
    CostStrategy costStrategy = new CostStrategy();
    private Router router;

    @Test
    void canary(){
        assertTrue(true);
    }

    @BeforeEach
    void init(){
        Node n0 = new Node(0, 0.5, 0.5);
        Node n1 = new Node(1, 0.5, 0.5);
        Node n2 = new Node(2, 0.5, 0.5);
        graph.put(n0, new ArrayList<>(Arrays.asList(new Edge(n1, 3),
                                                    new Edge(n2, 5))));
        graph.put(n1, new ArrayList<>(Arrays.asList(new Edge(n0, 3),
                                                    new Edge(n2, 7))));
        graph.put(n2, new ArrayList<>(Arrays.asList(new Edge(n0, 5),
                                                    new Edge(n1, 7))));
    }

    @Test
    void calculateCostWithoutStrategy(){
    }

}
