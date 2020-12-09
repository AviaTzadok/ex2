package ex0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class tergul {
    public static void main(String []args) {
        graph g = new Graph_DS();
        graph_algorithms ga = new Graph_Algo();
        ga.init(g);
        for (int i = 0; i < 10; i++) {
            NodeData n = new NodeData();
            n.setKey(i);
            g.addNode(n);
        }
        node_data nodes[] = g.getV().toArray(new node_data[0]);
        for (int j = 0; j < 9; j++) {
            g.connect(nodes[j].getKey(), nodes[j + 1].getKey());
        }
            System.out.println(ga.isConnected());

    }
}
