package com.minhho.demo;

import com.minhho.demo.model.Edge;
import com.minhho.demo.model.Node;
import com.minhho.demo.service.EcoFastCostStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CostStrategyTest {
    @Test
    void testEcoFastCostStrategy(){
        Node from = new Node(1, 0,0);
        Node to = new Node(2, 1,1);
        Edge edge = new Edge(from, to, 10, 50);  //10km - 50km/h
        EcoFastCostStrategy strategy = new EcoFastCostStrategy(0.8, 0.2, 0.192);
        double expected = (10 * 0.192 * 0.8) + ((10.0 / 50) * 0.2);
        assertEquals(expected, strategy.calculate(edge), 0.001);

    }

}
