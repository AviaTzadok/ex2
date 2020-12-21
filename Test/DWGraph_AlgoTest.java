package Test;

import api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {


    @Test
    void copy() {
        directed_weighted_graph g = gra2();
        dw_graph_algorithms gAlgo = new DWGraph_Algo();
        gAlgo.init(g);
        directed_weighted_graph g2 = gAlgo.copy();
        assertEquals(g2, g);
        g.removeNode(3);
        assertNotEquals(g2, g);
        NodeData temp = new NodeData(3);
        g.addNode(temp);
        assertNotEquals(g2, g);
        g.connect(4, 3, 1);
        assertNotEquals(g, g2);

    }

    @Test
    void isConnected() {
        directed_weighted_graph gr = gra2();
        dw_graph_algorithms agr1 = new DWGraph_Algo();
        agr1.init(gr);
        assertFalse(agr1.isConnected());
        gr.connect(7, 6, 0);
        assertFalse(agr1.isConnected());
        gr.connect(7, 6, 1);
        assertFalse(agr1.isConnected());
        DWGraph_DS g = new DWGraph_DS();
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.connect(1,2,3);
        g.connect(2,3,1);
        g.connect(3,1,4);
        agr1.init(g);
        assertTrue(agr1.isConnected());
        g.removeEdge(1,2);
        assertFalse(agr1.isConnected());

    }

    @Test
    void shortestPathDist() {
        directed_weighted_graph gr = gra2();
        dw_graph_algorithms agr1 = new DWGraph_Algo();
        agr1.init(gr);
        assertEquals(agr1.shortestPathDist(7, 2), 3);

    }

    @Test
    void shortestPath() {
        directed_weighted_graph gr = gra2();
        dw_graph_algorithms agr1 = new DWGraph_Algo();
        agr1.init(gr);
        assertEquals(agr1.shortestPathDist(7, 2), 3);
        assertEquals(agr1.shortestPath(7, 2).size(), 2);
    }

    @Test
    void save_load() {
        directed_weighted_graph gr = gra2();
        dw_graph_algorithms agr = new DWGraph_Algo();
        agr.init(gr);
        agr.save("graph");
        agr.load("data/A0");
    }


    public static DWGraph_DS gra2() {
        DWGraph_DS gr = new DWGraph_DS();
        NodeData n0 = new NodeData(0);
        NodeData n1 = new NodeData(1);
        NodeData n2 = new NodeData(2);
        NodeData n3 = new NodeData(3);
        NodeData n4 = new NodeData(4);
        NodeData n5 = new NodeData(5);
        NodeData n6 = new NodeData(6);
        NodeData n7 = new NodeData(7);
        gr.addNode(n0);
        gr.addNode(n1);
        gr.addNode(n2);
        gr.addNode(n3);
        gr.addNode(n4);
        gr.addNode(n5);
        gr.addNode(n6);
        gr.addNode(n7);
        gr.connect(1, 2, 2);
        gr.connect(3, 4, 5);
        gr.connect(1, 3, 6);
        gr.connect(5, 6, 3);
        gr.connect(6, 7, 8);
        gr.connect(5, 6, 9);
        gr.connect(1, 6, 9);
        gr.connect(7, 2, 3);

        return gr;
    }
}