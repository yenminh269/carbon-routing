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
        Node n0 = new Node("0", 0.5, 0.5);
        Node n1 = new Node("1", 0.6, 0.2);
        Node n2 = new Node("2", 0.9, 0.1);

        nodes.put("0", n0);
        nodes.put("1", n1);
        nodes.put("2", n2);

        adjacencyList.put("0", List.of(new Edge(n1, 3), new Edge(n2, 5)));
        adjacencyList.put("1", List.of(new Edge(n0, 3), new Edge(n2, 7)));
        adjacencyList.put("2", List.of(new Edge(n0, 5), new Edge(n1, 7)));
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