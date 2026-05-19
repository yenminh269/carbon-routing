package com.minhho.demo.shared;

import com.minhho.demo.model.Edge;
import com.minhho.demo.model.Node;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GraphRepository {
    private final Map<String, Node> nodes = new HashMap<>();
    private final Map<String, List<Edge>> adjacencyList = new HashMap<>();

    public GraphRepository() {
        Node n0 = new Node("A", 0.5, 0.5);
        Node n1 = new Node("B", 0.6, 0.2);
        Node n2 = new Node("C", 0.9, 0.1);

        nodes.put("A", n0);
        nodes.put("B", n1);
        nodes.put("C", n2);

        adjacencyList.put("A", List.of(new Edge(n1, 3), new Edge(n2, 5)));
        adjacencyList.put("B", List.of(new Edge(n0, 3), new Edge(n2, 7)));
        adjacencyList.put("C", List.of(new Edge(n0, 5), new Edge(n1, 7)));
    }

    public Node getNode(String id) {
        return nodes.get(id);
    }

    public List<Edge> getEdges(String nodeId) {
        return adjacencyList.get(nodeId);
    }

    public Map<String, List<Edge>> getGraph() {
        return adjacencyList;
    }
}