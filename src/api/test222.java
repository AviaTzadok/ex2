package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {

    @Test
    void getNode() {
        DWGraph_DS t = graphCreator();
        assertNotNull(t.getNode(1));
        assertNull(t.getNode(0));
    }

    @Test
    void getEdge() {
        DWGraph_DS t = graphCreator();
        assertNotNull(t.getEdge(2,1));
        assertNull(t.getEdge(1,2));
        assertEquals(t.getEdge(2,1).getWeight(),1);
        t.connect(2,1,2);
        assertNotEquals(t.getEdge(2,1).getWeight(),1);


    }

    @Test
    void addNode() {
        DWGraph_DS t = graphCreator();

        assertEquals(t.nodeSize(),5);
        int temp = t.nodeSize();
        assertEquals(t.nodeSize(),5);
        NodeData s = new NodeData(6);
        t.addNode(s);
        int temp2 =t.nodeSize();
        assertNotEquals(temp,temp2);


    }

    @Test
    void connect() {
        DWGraph_DS t = graphCreator();
        assertEquals(t.edgeSize(),2);
        assertNull(t.getEdge(1,2));
        t.connect(1,2,2);
        assertNotNull(t.getEdge(1,2));
        //what about edge from a node to itself
        assertEquals(t.edgeSize(),3);
        assertEquals(t.getEdge(1,2).getWeight(),2);
        t.connect(1,2,1);
        assertEquals(t.getEdge(1,2).getWeight(),1);
        t.connect(1,1,1);
        assertEquals(t.edgeSize(),3);

    }

    @Test
    void getV() {
        DWGraph_DS t = graphCreator();
        assertEquals(t.nodeSize(),t.getV().size());
        NodeData temp = new NodeData(1);
        assertTrue (t.getV().contains(temp));
        t.removeNode(1);
        assertEquals(t.getV().size(),4);
        t.removeNode(1);
        t.removeNode(2);
        t.removeNode(3);
        t.removeNode(4);
        t.removeNode(5);
        assertNotNull(t.getV());
        DWGraph_DS s = new DWGraph_DS();
        assertNotNull(s.getV());


    }

    @Test
    void getE() {
        DWGraph_DS t = graphCreator();
        assertNotNull(t.getE(5));
        assertNotNull(t.getE(0));
        assertEquals(t.getE(5).size(),0);
        t.connect(5,1,0);
        assertEquals(t.getE(5).size(),1);
        NodeData temp = new NodeData(1);


    }

    @Test
    void removeNode() {
        DWGraph_DS t = graphCreator();
        assertEquals(t.nodeSize(),5);//number of nodes
        assertNotNull(t.getE(1));//t contains 1
        assertEquals(t.getE(2).size(),1);//the number of edges from
        assertNotNull(t.getEdge(2,1));//there is an edge between 2>>1
        assertEquals(t.edgeSize(),2);//all the edges in the graph
        t.removeNode(1);//removing 1
        assertEquals(t.edgeSize(),1);//all the edges in the graph after 1 has been removed
        assertNull(t.getEdge(2, 1));//there isnt such edge
        assertEquals(t.getE(2).size(),0);//the current number of edges from 2
//        assertNull(t.getE(1));//the edge list from 1 is null
        assertEquals(t.nodeSize(),4);//the current number of nodes in the graph



    }

    @Test
    void removeEdge() {
        DWGraph_DS t = graphCreator();
        assertEquals(t.edgeSize(),2);
        assertNotNull(t.getEdge(2,1));
        t.removeEdge(2,1);
        assertEquals(t.edgeSize(),1);
        assertNull(t.getEdge(2,1));
        t.removeEdge(1,2);
        assertNull(t.getEdge(1,2));
        assertEquals(t.edgeSize(),1);




    }

    @Test
    void nodeSize() {
        DWGraph_DS t = graphCreator();
        assertEquals(t.nodeSize(),5);
        NodeData temp = new NodeData(6);
        t.addNode(temp);
        assertEquals(t.nodeSize(),6);
        t.removeNode(8);
        assertEquals(t.nodeSize(),6);
        t.removeNode(1);
        assertEquals(t.nodeSize(),5);



    }

    @Test
    void edgeSize() {
        DWGraph_DS t = graphCreator();
        assertEquals(t.edgeSize(),2);
        t.removeEdge(2,1);
        assertEquals(t.edgeSize(),1);
        t.connect(1,2,1);
        assertEquals(t.edgeSize(),2);
        t.removeNode(1);
        t.removeNode(4);
        assertEquals(t.edgeSize(),0);


    }

    @Test
    void getMC() {
        DWGraph_DS t = graphCreator();
        assertEquals(t.getMC(),7);
        t.removeEdge(2,1);
        assertEquals(t.getMC(),8);
        t.connect(1,2,1);
        t.connect(1,3,1);
        assertEquals(t.getMC(),10);
        t.removeNode(1);
        assertEquals(t.getMC(),13);


    }
    public static DWGraph_DS graphCreator(){
        DWGraph_DS t = new DWGraph_DS();
        for (int i = 1; i <6; i++) {
            NodeData temp = new NodeData(i);
            t.addNode(temp);
        }
        t.connect(2,1,1);
        t.connect(4,3,1);
        return t;
    }
}