package com.minhho.demo.algorithm;
import com.minhho.demo.model.*;
import com.minhho.demo.service.CostStrategy;
import com.minhho.demo.shared.GraphRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class DijkstraRouter implements Router{
    GraphRepository graphRepository =  new GraphRepository();

    @Override
    public Path findShortestPath(String startId, String endId, CostStrategy strategy, Vehicle vehicle) {
        Map<String, Double> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        Map<String, Edge> prevEdge = new HashMap<>();

        PriorityQueue<NodeDistance> pq =
                new PriorityQueue<>(Comparator.comparingDouble(NodeDistance::distance));

        dist.put(startId, 0.0);
        pq.offer(new NodeDistance(startId, 0.0));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            String u = current.id();

            if (u.equals(endId)) break;

            List<Edge> edges = graphRepository.getEdges(u);
            if (edges == null) continue;

            for (Edge edge : edges) {

                String v = edge.to().id();

                double cost = strategy.calculate(edge);

                double newDist = dist.get(u) + cost;

                if (newDist < dist.getOrDefault(v, Double.MAX_VALUE)) {
                    dist.put(v, newDist);
                    prev.put(v, u);
                    prevEdge.put(v, edge);
                    pq.offer(new NodeDistance(v, newDist));
                }
            }
        }

        return buildPath(startId, endId, prev, prevEdge, vehicle);
    }

    private Path buildPath(String startId, String endId, Map<String, String> prev,
                           Map<String, Edge> prevEdge, Vehicle vehicle) {

        if (!startId.equals(endId) && !prev.containsKey(endId)) {
            throw new IllegalArgumentException("No path found");
        }

        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        String currentId = endId;

        while (!currentId.equals(startId)) {

            nodes.add(graphRepository.getNode(currentId));

            Edge edge = prevEdge.get(currentId);
            if (edge != null) {
                edges.add(edge);
            }

            currentId = prev.get(currentId);
        }

        nodes.add(graphRepository.getNode(startId));

        Collections.reverse(nodes);
        Collections.reverse(edges);

        double totalDistance = 0;
        double totalTime = 0;
        double totalCarbon = 0;

        for (Edge edge : edges) {
            totalDistance += edge.distanceInMiles();
            totalTime += edge.distanceInMiles() / vehicle.avgSpeedInMiles();
            totalCarbon += edge.distanceInMiles() * vehicle.emissionRate();
        }

        return new Path(
                nodes,
                edges,
                roundValue(totalDistance),
                roundValue(totalCarbon),
                roundValue(totalTime)
        );
    }


    private double roundValue(double value){
        return  new BigDecimal(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
