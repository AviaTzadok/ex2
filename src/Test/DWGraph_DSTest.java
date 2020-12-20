package Test;


import api.DWGraph_DS;
import api.NodeData;
import api.node_data;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {

    @Test
    void getNode() {
        DWGraph_DS g = gra();
        assertEquals(15, g.getMC());
        NodeData n10=new NodeData(10);
        g.addNode(n10);
        assertTrue(g.getNode(10) != null);
        NodeData n3=new NodeData(3);
        g.addNode(n3);
        assertTrue(g.getNode(3) != null);

        assertEquals(16, g.getMC());

    }

    @Test
    void addNode() {
        DWGraph_DS g = gra();
        assertEquals(15, g.getMC());
        NodeData n10=new NodeData(10);
        g.addNode(n10);
        assertTrue(g.getNode(10) != null);
        NodeData n3=new NodeData(3);

        g.addNode(n3);
        assertTrue(g.getNode(3) != null);

        assertEquals(16, g.getMC());

    }

    @Test
    void connect() {
        DWGraph_DS g = gra();
        assertEquals(15, g.getMC());

        g.connect(2, 5, 7.0);
        assertEquals(7.0, g.getEdge(2, 5).getWeight());
        assertNotEquals(6.0, g.getEdge(2, 5).getWeight());
        g.connect(1, 2, 7);
        g.connect(1, 2, 7);

        assertNotEquals(2.0, g.getEdge(1, 2).getWeight());
        assertEquals(7.0, g.getEdge(1, 2).getWeight());
        assertEquals(17, g.getMC());

    }

    @Test
    void removeNode() {
        DWGraph_DS g = gra();
        assertEquals(15, g.getMC());
        assertTrue(g.getE(1).size() == 3);
        g.removeNode(2);
        assertTrue(g.getNode(2)== null);
        assertTrue(g.getE(2).size()==0);
        assertEquals(16, g.getMC());
    }

    @Test
    void removeEdge() {
        DWGraph_DS g = gra();
        assertEquals(15, g.getMC());
        assertNotNull(g.getEdge(1,2));
        g.removeEdge(1,2);
        assertNull(g.getEdge(1,2));
        g.removeEdge(1,8);
        assertEquals(16, g.getMC());

    }

    @Test
    void nodeSize() {
        DWGraph_DS g = gra();
        assertEquals(8.0, g.nodeSize());
    }

    @Test
    void edgeSize() {
        DWGraph_DS g = gra();
        assertEquals(6.0, g.edgeSize());
    }

    @Test
    void getMC() {
        DWGraph_DS g = gra();
        assertEquals( g.getMC(),15);
    }


    public static DWGraph_DS gra(){
        DWGraph_DS gr = new DWGraph_DS();
        NodeData n0 =new NodeData(0);
        NodeData n1 =new NodeData(1);
        NodeData n2 =new NodeData(2);
        NodeData n3 =new NodeData(3);
        NodeData n4 =new NodeData(4);
        NodeData n5 =new NodeData(5);
        NodeData n6 =new NodeData(6);
        NodeData n7 =new NodeData(7);
        gr.addNode(n0);
        gr.addNode(n1);
        gr.addNode(n2);
        gr.addNode(n3);
        gr.addNode(n4);
        gr.addNode(n5);
        gr.addNode(n6);
        gr.addNode(n7);
        gr.connect(1,2,2);
        gr.connect(3,4,5);
        gr.connect(1,3,6);
        gr.connect(5,6,3);
        gr.connect(6,7,8);
        gr.connect(5,6,9);
        gr.connect(1,6,9);

        return gr;  }





}