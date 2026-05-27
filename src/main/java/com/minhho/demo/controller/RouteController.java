package com.minhho.demo.controller;

import com.minhho.demo.algorithm.DijkstraRouter;
import com.minhho.demo.dto.EdgeResponse;
import com.minhho.demo.dto.RouteRequest;
import com.minhho.demo.dto.RouteResponse;
import com.minhho.demo.model.Node;
import com.minhho.demo.model.Path;
import com.minhho.demo.model.Vehicle;
import com.minhho.demo.service.RouteProcessor;
import com.minhho.demo.service.TimeStrategy;
import com.minhho.demo.service.CarbonStrategy;
import com.minhho.demo.shared.GraphRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/route")
public class RouteController {
    @GetMapping("/time")
    public RouteResponse fasterRouteBasedOnTime(@RequestBody RouteRequest request) {
        Vehicle vehicle = new Vehicle(request.vehicle(), request.emissionRate(), request.avgSpeedInMiles());
        RouteProcessor router = new RouteProcessor();
        GraphRepository graph = new GraphRepository();
        router.setRoutingService(new DijkstraRouter());
        router.setStrategy(new TimeStrategy(vehicle));

        Path result = router.findShortestPath(request.source(), request.destination(), graph.getGraph(), vehicle);

        List<String> pathNames = result.nodes()
                .stream()
                .map(Node::id)
                .toList();

        List<EdgeResponse> edgeResponses = result.edges()
                .stream()
                .map(edge -> new EdgeResponse(
                        edge.to().id(),
                        edge.distanceInMiles()
                ))
                .toList();

        return new RouteResponse(pathNames, edgeResponses, result.totalDistance(), result.totalCarbon(), result.totalTime());
    }

    @PostMapping("/calculate")
    public Map<String, RouteResponse> calculateRoutes(@RequestBody RouteRequest request) {
        Vehicle vehicle = new Vehicle(request.vehicle(), request.emissionRate(), request.avgSpeedInMiles());
        GraphRepository graph = new GraphRepository();

        // 1. Calculate fastest route (Time Strategy)
        RouteProcessor timeProcessor = new RouteProcessor();
        timeProcessor.setRoutingService(new DijkstraRouter());
        timeProcessor.setStrategy(new TimeStrategy(vehicle));
        Path timeResult = timeProcessor.findShortestPath(request.source(), request.destination(), graph.getGraph(), vehicle);

        List<String> timePathNames = timeResult.nodes().stream().map(Node::id).toList();
        List<EdgeResponse> timeEdges = timeResult.edges().stream()
                .map(edge -> new EdgeResponse(edge.to().id(), edge.distanceInMiles()))
                .toList();
        RouteResponse timeResponse = new RouteResponse(
                timePathNames, timeEdges, timeResult.totalDistance(), timeResult.totalCarbon(), timeResult.totalTime()
        );

        // 2. Calculate greenest route (Carbon Strategy)
        RouteProcessor carbonProcessor = new RouteProcessor();
        carbonProcessor.setRoutingService(new DijkstraRouter());
        carbonProcessor.setStrategy(new CarbonStrategy(vehicle));
        Path carbonResult = carbonProcessor.findShortestPath(request.source(), request.destination(), graph.getGraph(), vehicle);

        List<String> carbonPathNames = carbonResult.nodes().stream().map(Node::id).toList();
        List<EdgeResponse> carbonEdges = carbonResult.edges().stream()
                .map(edge -> new EdgeResponse(edge.to().id(), edge.distanceInMiles()))
                .toList();
        RouteResponse carbonResponse = new RouteResponse(
                carbonPathNames, carbonEdges, carbonResult.totalDistance(), carbonResult.totalCarbon(), carbonResult.totalTime()
        );

        return Map.of("fastest", timeResponse, "greenest", carbonResponse);
    }
}
