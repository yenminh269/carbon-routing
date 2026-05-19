package com.minhho.demo;

import com.minhho.demo.algorithm.DijkstraRouter;
import com.minhho.demo.model.Edge;
import com.minhho.demo.model.Node;
import com.minhho.demo.model.Path;
import com.minhho.demo.model.Vehicle;
import com.minhho.demo.service.CarbonStrategy;
import com.minhho.demo.service.RouteProcessor;
import com.minhho.demo.service.TimeStrategy;
import com.minhho.demo.shared.GraphRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CostStrategyTest {
    Node n0 = new Node("A", 0.5, 0.5);
    Node n2 = new Node("C", 0.9, 0.1);
    GraphRepository graphRepo = new GraphRepository();
    Map<String, List<Edge>> graph = graphRepo.getGraph();
    RouteProcessor routeProcessor = new RouteProcessor();

    @Test
    void canary(){
        assertTrue(true);
    }

    @BeforeEach
    void setup() {
        routeProcessor.setRoutingService(new DijkstraRouter());
    }

    @Test
    void returnShortestPath_whenTimeStrategyGetPassed(){
        Vehicle vehicle  = new Vehicle("Honda Civic", 120, 30);;
        routeProcessor.setStrategy(new TimeStrategy(vehicle));

        Path expected = new Path(List.of(n0, n2), List.of( new Edge(n2, 5)),
                5, 600, 0.17);

        assertEquals(expected, routeProcessor.findShortestPath("A", "C", graph, vehicle));
    }

    @Test
    void returnShortestPath_whenCarbonStrategyGetPassed(){
        Vehicle vehicle  = new Vehicle("Tesla", 0, 30);
        routeProcessor.setStrategy(new CarbonStrategy(vehicle));

        Path expected = new Path(List.of(n0, n2), List.of( new Edge(n2, 5)),
                5, 0, 0.17);

        assertEquals(expected, routeProcessor.findShortestPath("0", "2", graph, vehicle));
    }
}
