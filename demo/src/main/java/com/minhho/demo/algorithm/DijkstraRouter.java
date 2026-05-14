package com.minhho.demo.algorithm;
import com.minhho.demo.model.Edge;
import com.minhho.demo.model.Node;
import com.minhho.demo.model.NodeDistance;
import com.minhho.demo.model.Path;
import com.minhho.demo.service.CostStrategy;
import java.util.*;


public class DijkstraRouter implements Router{
    @Override
    public List<Node> findShortestPath(Node start, Node end, Map<Node, List<Edge>> graph, CostStrategy strategy) {
        Map<Node, Double> dist = new HashMap<>();
        Map<Node, Node> prev = new HashMap<>();

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

            if (current.equals(end)) {
                break;
            }

            List<Edge> edges = graph.getOrDefault(current, new ArrayList<>());

            for (Edge edge : edges) {
                Node neighbor = edge.to();

                double cost = strategy.calculate(edge);

                double newCost = dist.get(current) + cost;

                if (newCost < dist.get(neighbor)) {

                    dist.put(neighbor, newCost);
                    prev.put(neighbor, current);

                    pq.offer(new NodeDistance(neighbor, newCost));
                }
            }
        }

        return buildPath(start, end, prev, dist);
    }

    private List<Node> buildPath(Node start, Node end, Map<Node, Node> previous, Map<Node, Double> distance) {
        List<Node> nodes = new ArrayList<>();

        Node current = end;

        while (current != null) {
            nodes.add(current);
            current = previous.get(current);
        }

        Collections.reverse(nodes);

        return nodes;
    }
}
