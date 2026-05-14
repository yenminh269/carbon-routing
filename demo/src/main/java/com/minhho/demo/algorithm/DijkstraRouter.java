package com.minhho.demo.algorithm;
import com.minhho.demo.model.*;
import com.minhho.demo.service.CostStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class DijkstraRouter implements Router{
    @Override
    public Path findShortestPath(Node start, Node end,
                                       Map<Node, List<Edge>> graph, CostStrategy strategy, Vehicle vehicle) {
        Map<Node, Double> dist = new HashMap<>();
        Map<Node, Node> prev = new HashMap<>();
        Map<Node, Edge> prevEdge = new HashMap<>();

        PriorityQueue<NodeDistance> pq =
                new PriorityQueue<>(Comparator.comparingDouble(NodeDistance::distance));

        for (Node node : graph.keySet()) {
            dist.put(node, Double.MAX_VALUE);
        }

        dist.put(start, 0.0);
        pq.offer(new NodeDistance(start, 0.0));

        while (!pq.isEmpty()) {
            NodeDistance currentPair = pq.poll();

            Node current = currentPair.node();

            if (currentPair.distance() > dist.get(current)) {
                continue;
            }

            if (current.equals(end)) {
                break;
            }

            List<Edge> edges = graph.getOrDefault(current, List.of());

            for (Edge edge : edges) {
                Node neighbor = edge.to();

                double cost = strategy.calculate(edge);

                double newCost = dist.get(current) + cost;

                if (newCost < dist.get(neighbor)) {
                    dist.put(neighbor, newCost);
                    prev.put(neighbor, current);
                    prevEdge.put(neighbor, edge);

                    pq.offer(new NodeDistance(neighbor, newCost));
                }
            }
        }


        return buildPath(start, end, prev, prevEdge, vehicle);
    }

    private Path buildPath(Node start, Node end,
                           Map<Node, Node> prev, Map<Node, Edge> prevEdge, Vehicle vehicle) {
        if (!start.equals(end) && !prev.containsKey(end)) {
            throw new IllegalArgumentException("No path found");
        }
        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        Node current = end;

        while (!current.equals(start)) {
            nodes.add(current);
            edges.add(prevEdge.get(current));
            current = prev.get(current);
        }

        nodes.add(start);

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



        return new Path(nodes, edges, roundValue(totalDistance), roundValue(totalCarbon),
                roundValue(totalTime));
    }


    private double roundValue(double value){
        return  new BigDecimal(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
