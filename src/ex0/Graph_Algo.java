package ex0;

import jdk.swing.interop.SwingInterOpUtils;

import javax.imageio.ImageTranscoder;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.prefs.NodeChangeEvent;

public class Graph_Algo implements graph_algorithms {

    private graph gr;

    public Graph_Algo(graph g0) {
        gr = g0;
    }

    /**
     * constructor which creates a new graph With the features of Graph_DS
     */
    public Graph_Algo() {
        gr = new Graph_DS();
    }

    /**
     * this method initializes the graph
     * @param g
     */
    @Override
    public void init(graph g) {
        this.gr = g;
    }

    /**
     * this methode creates a deep copy of this graph
     * @return copy graph
     */
    @Override
    public graph copy() {
        graph copyGraph = new Graph_DS();
        Iterator<node_data> itr = this.gr.getV().iterator();

        while (itr.hasNext()) {
            node_data no = itr.next();
            NodeData temp = new NodeData();
            temp.setInfo(no.getInfo());
            temp.setTag(no.getTag());
            temp.setKey(no.getKey());
            copyGraph.addNode(temp);
        }

        Iterator<node_data> itrNi = copyGraph.getV().iterator();

        while (itrNi.hasNext()) {
            node_data nodeNi = itrNi.next();
            Iterator<node_data> Ni = gr.getNode(nodeNi.getKey()).getNi().iterator();
            while (Ni.hasNext()) {
                copyGraph.connect(nodeNi.getKey(), Ni.next().getKey());
            }
        }

        return copyGraph;
    }

    /**
     *this method checks whether the graph is connected
     * @return true or false
     */
    @Override
    public boolean isConnected() {
        if (gr == null || gr.nodeSize() == 0) return true;
        Iterator<node_data> itr4 = gr.getV().iterator(); //We will define iterator so that we can go through all the nodes.
        // because it can not go through the organs in order
        // (since the organs are in a certain order)
        while (itr4.hasNext())
            itr4.next().setInfo("white");              // We will initialize all the "Info of nodes" to be white
        int v = gr.nodeSize();
        int counter = 0;
        Iterator<node_data> I = gr.getV().iterator();  // define new iterator

        node_data first = I.next();
        ArrayDeque<node_data> q = new ArrayDeque<node_data>();
        q.push(first);                                 // push the first limb to the ArrayDeque
        first.setInfo("black");                        // We will paint "Info" in black ,so that we do not appropriate it again
        while (!q.isEmpty()) {                         //We'll go over with iterator the neighbors of the last peek
            I = q.peekLast().getNi().iterator();
            while (I.hasNext()) {
                node_data temp2 = I.next();
                if (temp2.getInfo() != "black") {
                    temp2.setInfo("black");
                    q.push(temp2);
                }
            }

            q.pollLast();
            counter++;                                 // We will add to counter 1. each time we color the "nodes" in black
        }
        return counter == v;                           // If the counter is equal to nodeSize, The graph is linked. so return true else false

    }

    /**
     * this method return the shortest path between these nodes
     * @param src - start node
     * @param dest - end (target) node
     * @return The shortest path size
     */
    @Override
    public int shortestPathDist(int src, int dest) {
        if (gr.getNode(src) == null || gr.getNode(dest) == null) return -1;
        Iterator<node_data> itr = gr.getV().iterator();
        while (itr.hasNext()) {
            node_data n = itr.next();
            n.setTag(-1);
            n.setInfo("white");
        }
        node_data first = gr.getNode(src);
        ArrayDeque<node_data> q = new ArrayDeque<node_data>();
        q.push(first);
        first.setInfo("black");
        first.setTag(0);
        while (!q.isEmpty()) {
            Iterator<node_data> itr2 = q.peekLast().getNi().iterator();
            while (itr2.hasNext()) {
                node_data temp = itr2.next();
                if (temp.getInfo().equalsIgnoreCase("white")) {
                    temp.setTag(q.peekLast().getTag() + 1);
                    q.push(temp);
                    temp.setInfo("black");
                }
            }
            q.pollLast();
        }
        node_data des = gr.getNode(dest);
        if (des.getTag() == -1) return -1;
        q.push(des);
        int count = 0;
        int min = Integer.MAX_VALUE;
        while (des.getTag() != 0) {
            Iterator<node_data> itr3 = des.getNi().iterator();
            while (itr3.hasNext()) {
                node_data temp3 = itr3.next();
                if (temp3.getTag() == des.getTag() - 1) {
                    des = temp3;
                    count++;
                }
            }
            q.pollLast();
        }
        return count;
    }

    /**
     * this methode returns the a list of the nodes of the shortest path between the nodes
     * @param src - start node
     * @param dest - end (target) node
     * @return list of nodes
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        shortestPathDist(src, dest);
        ArrayList<node_data> ret = new ArrayList<node_data>();
        node_data des = gr.getNode(dest);
        ArrayList<node_data> ls = new ArrayList<node_data>();
        ls.add(des);
        if (des.getTag() == -1) return ret;
        while (des.getTag() != 0) {
            Iterator<node_data> itr = des.getNi().iterator();
            while (itr.hasNext()) {
                node_data temp = itr.next();
                if (temp.getTag() + 1 == des.getTag()) {
                    des = temp;
                    ls.add(des);
                    break;
                }

            }
        }
        ArrayList<node_data> ls2 = new ArrayList<node_data>();
        int j = 0;
        for (int i = ls.size() - 1; i >= 0; i--)
            ls2.add(j++, ls.get(i));
        return ls2;
    }

}
