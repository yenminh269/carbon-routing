package com.minhho.demo.controller;

import com.minhho.demo.algorithm.DijkstraRouter;
import com.minhho.demo.dto.RouteRequest;
import com.minhho.demo.dto.RouteResponse;
import com.minhho.demo.model.Edge;
import com.minhho.demo.model.Node;
import com.minhho.demo.model.Path;
import com.minhho.demo.model.Vehicle;
import com.minhho.demo.service.RouteProcessor;
import com.minhho.demo.service.TimeStrategy;
import com.minhho.demo.shared.GraphRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/route")
public class RouteController {
    @PostMapping("/time")
    public RouteResponse fastest(@RequestBody RouteRequest request) {
        Vehicle vehicle = new Vehicle(request.vehicle(), 50, 0.2);
        RouteProcessor router = new RouteProcessor();
        GraphRepository graph = new GraphRepository();
        router.setRoutingService(new DijkstraRouter());
        router.setStrategy(new TimeStrategy(vehicle));

        List<Edge> edges = graph.getEdges(request.source());

        Path result = router.findShortestPath(request.source(), request.destination(), graph.getGraph(), vehicle);

        List<String> pathNames = result.nodes()
                .stream()
                .map(Node::id)
                .toList();

        return new RouteResponse(pathNames, result.totalDistance(), result.totalCarbon(), result.totalTime());
    }

}
