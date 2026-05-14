package com.minhho.demo;

import com.minhho.demo.algorithm.DijkstraRouter;
import com.minhho.demo.model.Edge;
import com.minhho.demo.model.Node;
import com.minhho.demo.model.Path;
import com.minhho.demo.model.Vehicle;
import com.minhho.demo.service.CarbonStrategy;
import com.minhho.demo.service.RouteProcessor;
import com.minhho.demo.service.TimeStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CostStrategyTest {
    Node n0 = new Node(0, 0.5, 0.5);
    Node n1 = new Node(1, 0.5, 0.5);
    Node n2 = new Node(2, 0.5, 0.5);
    Map<Node, List<Edge>> graph = new HashMap<>();
    RouteProcessor routeProcessor = new RouteProcessor();

    @Test
    void canary(){
        assertTrue(true);
    }

    @BeforeEach
    void setup() {
        graph.put(n0, new ArrayList<>(Arrays.asList(new Edge(n1, 3), new Edge(n2, 5))));
        graph.put(n1, new ArrayList<>(Arrays.asList(new Edge(n0, 3), new Edge(n2, 7))));
        graph.put(n2, new ArrayList<>(Arrays.asList(new Edge(n0, 5), new Edge(n1, 7))));

        routeProcessor.setRoutingService(new DijkstraRouter());
    }

    @Test
    void returnShortestPath_whenTimeStrategyGetPassed(){
        Vehicle vehicle  = new Vehicle("Honda Civic", 120, 30);;
        routeProcessor.setStrategy(new TimeStrategy(vehicle));

        Path expected = new Path(List.of(n0, n2), List.of( new Edge(n2, 5)),
                5, 600, 0.17);

        assertEquals(expected, routeProcessor.findShortestPath(n0, n2, graph, vehicle));
    }

    @Test
    void returnShortestPath_whenCarbonStrategyGetPassed(){
        Vehicle vehicle  = new Vehicle("Tesla", 0, 30);
        routeProcessor.setStrategy(new CarbonStrategy(vehicle));

        Path expected = new Path(List.of(n0, n2), List.of( new Edge(n2, 5)),
                5, 0, 0.17);

        assertEquals(expected, routeProcessor.findShortestPath(n0, n2, graph, vehicle));
    }


}
