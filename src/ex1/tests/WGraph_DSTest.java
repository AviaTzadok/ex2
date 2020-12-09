package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {


    @Test
    void getNode() {
        assertTrue(gra().getNode(3) != null);
        assertTrue(gra().getNode(25) == null);

    }

    @Test
    void hasEdge() {
        Assertions.assertTrue(gra().hasEdge(1, 2));
        Assertions.assertFalse(gra().hasEdge(1, 8));
    }

    @Test
    void getEdge() {
        Assertions.assertEquals(2.0, gra().getEdge(1, 2));
        Assertions.assertNotEquals(2.0, gra().getEdge(1, 6));
        Assertions.assertEquals(-1.0, gra().getEdge(1, 8));

    }

    @Test
    void addNode() {
        WGraph_DS g = gra();
        assertEquals(15, g.getMC());

        g.addNode(10);
        assertTrue(g.getNode(10) != null);

        g.addNode(3);
        assertTrue(g.getNode(3) != null);

        assertEquals(16, g.getMC());

    }

    @Test
    void connect() {
        WGraph_DS g = gra();
        assertEquals(15, g.getMC());

        g.connect(2, 5, 7.0);
        assertEquals(7.0, g.getEdge(2, 5));
        assertNotEquals(6.0, gra().getEdge(2, 5));
        g.connect(1, 2, 7);
        g.connect(1, 2, 7);

        assertNotEquals(2.0, g.getEdge(1, 2));
        assertEquals(7.0, g.getEdge(1, 2));
        assertEquals(17, g.getMC());

    }

    @Test
    void getV() {
        WGraph_DS g = gra();
        g.getV();
        assertNotNull(g.getNode(2));
        assertNull(g.getNode(12));
    }

    @Test
    void testGetV() {
        WGraph_DS g = gra();
        ArrayList<node_info> ni = (ArrayList<node_info>) g.getV(6);
        for (node_info M : ni) {
            assertTrue(g.hasEdge(6, M.getKey()));
        }
        ArrayList<node_info> ni2 = (ArrayList<node_info>) g.getV(12);
        assertTrue(ni2.size() == 0);
    }

    @Test
    void removeNode() {
        WGraph_DS g = gra();
        assertEquals(15, g.getMC());
        ArrayList<node_info> ni = (ArrayList<node_info>) g.getV(1);
        assertTrue(ni.size() == 3);

        g.removeNode(2);
        assertTrue(g.getNode(2) == null);
        ArrayList<node_info> ni2 = (ArrayList<node_info>) g.getV(1);
        assertTrue(ni2.size() == 2);
        assertEquals(16, g.getMC());

    }

    @Test
    void removeEdge() {
        WGraph_DS g = gra();
        assertEquals(15, g.getMC());

        assertTrue(g.hasEdge(1,2));
        g.removeEdge(1,2);
         assertFalse(g.hasEdge(1,2));
         g.removeEdge(1,8);
        assertEquals(16, g.getMC());

    }

    @Test
    void nodeSize() {
        WGraph_DS g = gra();
        assertEquals(8.0, g.nodeSize());

    }

    @Test
    void edgeSize() {
        WGraph_DS g = gra();
        assertEquals(6.0, g.edgeSize());
    }

    @Test
    void getMC() {
        WGraph_DS g = gra();
        assertEquals( g.getMC(),15);

    }
    public static  WGraph_DS gra(){
        WGraph_DS gr = new WGraph_DS();
        gr.addNode(0);
        gr.addNode(1);
        gr.addNode(2);
        gr.addNode(3);
        gr.addNode(4);
        gr.addNode(5);
        gr.addNode(6);
        gr.addNode(7);
        gr.connect(1,2,2);
        gr.connect(3,4,5);
        gr.connect(1,3,6);
        gr.connect(5,6,3);
        gr.connect(6,7,8);
        gr.connect(5,6,9);
        gr.connect(1,6,9);

  return gr;  }

}