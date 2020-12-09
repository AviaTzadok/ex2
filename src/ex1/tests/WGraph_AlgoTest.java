package ex1.tests;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import ex1.src.weighted_graph_algorithms;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {


    @Test
    void copy() {
        weighted_graph gr = gra2();
        weighted_graph_algorithms agr1 = new WGraph_Algo();
        agr1.init(gr);
        weighted_graph_algorithms agr2 = new WGraph_Algo();
        weighted_graph gr2=agr1.copy();
        agr2.init(agr1.copy());
        assertEquals(gr,gr2);

    }

    @Test
    void isConnected() {
        weighted_graph gr = gra2();
        weighted_graph_algorithms agr1 = new WGraph_Algo();
        agr1.init(gr);

       assertTrue( agr1.isConnected());
       gr.removeEdge(1,2);
        gr.removeEdge(7,2);
        assertFalse(agr1.isConnected());

    }

    @Test
    void shortestPathDist() {
        weighted_graph gr = gra2();
        weighted_graph_algorithms agr1 = new WGraph_Algo();
        agr1.init(gr);
        assertEquals(agr1.shortestPathDist(7,5),17);

    }

    @Test
    void shortestPath() {
        weighted_graph gr = gra2();
        weighted_graph_algorithms agr1 = new WGraph_Algo();
        agr1.init(gr);
        assertEquals(agr1.shortestPathDist(5,7),17);
        LinkedList<node_info> H= new LinkedList<node_info>();
        H.add(gra2().getNode(5));
        H.add(gra2().getNode(6));
        H.add(gra2().getNode(7));
        LinkedList<node_info> H2= new LinkedList<node_info>();
        H2=(LinkedList)agr1.shortestPath(5,7);
        assertEquals(H.size(),H2.size());
        for(int i=0; i<H.size();i++){
            assertEquals(H.get(i).getKey(),H2.get(i).getKey());

        }

    }

    @Test
    void save_load() {
        weighted_graph gr=gra2();
        weighted_graph_algorithms agr=new WGraph_Algo();
        agr.init(gr);
        agr.save("C:\\Users\\97254\\OneDrive\\שולחן העבודה\\graph.txt");
        agr.load("C:\\Users\\97254\\OneDrive\\שולחן העבודה\\graph.txt");
        weighted_graph gr2=agr.getGraph();
        assertEquals(gr,gr2);
    }

    public static WGraph_DS gra2(){
        WGraph_DS gr = new WGraph_DS();
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
        gr.connect(7,2,3);


        return gr;  }
}