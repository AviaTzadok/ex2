package ex1.src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
/**
 * This class implements the graph interface and represents an undirected unweighted graph.
 * this graph support a large number of nodes (over 10^6, with average degree of 10)
 */
public class WGraph_DS implements weighted_graph, Comparator<node_info>,Serializable{
    private HashMap<Integer, node_info> nodes;
    private HashMap<Integer, HashMap<Integer, Double>> Neighbor;
    private int EdgeNum;
    private int MC;
    /**
     * constructor who creates a new graph Type of HashMap
     */
    public WGraph_DS() {
        this.nodes = new HashMap<Integer, node_info>();
        this.Neighbor = new HashMap<Integer, HashMap<Integer, Double>>();
        this.MC = 0;
        this.EdgeNum = 0;
    }

    /**
     * This method helps the priority queue **function** to know which organ to put in the priority queue first
     * because our class does not know how to do this we build a function that will know how to implement the priority queue
     * This function puts organs in a queue in the order chosen
     * in our case we chose to go to the neighboring rib with the smaller weight
     * and put the information in the queue and then to the neighboring rib with the larger weight
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(node_info o1, node_info o2) {
        if(o1.getTag()>o2.getTag()) return 1;
        if(o1.getTag()<o2.getTag()) return -1;
        if(o1==o2) return 0;
        return 0;
    }

    /**
     * This method helps us to check if the new graph we copied in the copy method is indeed the same as the original graph
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WGraph_DS)) return false;
        WGraph_DS gr = (WGraph_DS) (obj);
        if (this.nodeSize() != gr.nodeSize()) return false;
        if (this.edgeSize() != gr.edgeSize()) return false;
        for (node_info M : gr.getV()) {
            if (!this.nodes.containsKey(M.getKey()))
                return false;
            for (node_info L : gr.getV(M.getKey())) {
                if (!this.hasEdge(M.getKey(), L.getKey())) return false;
            }
        }
        for (node_info N : gr.getV()) {
            if (gr.getNode(N.getKey()).getTag() != this.nodes.get(N.getKey()).getTag()) return false;
            if (gr.getNode(N.getKey()).getInfo() != this.nodes.get(N.getKey()).getInfo()) return false;

        }
            return true;
        }

    /**
     * Internal class within a graph class,
     * which contains all the attributes of the graph
     * so that the user does not create a new graph these internal class creates it
     */
    private class NodeInfo implements node_info {
        private int key;
        private String info;
        private double tag;

        public NodeInfo(int k) {
            this.key = k;
//            this.info = "";
//            this.tag = 0;
        }

        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }
    }//close the privet class

    /**
     * this method return this node
     * @param key - the node_id
     * @return node
     */
    @Override
    public node_info getNode(int key) {
        if (nodes.containsKey(key)) {
            return nodes.get(key);
        }
        return null;
    }
    /**
     * this method Checks if there is a edge between this nodes
     * @param node1
     * @param node2
     * @return true/false
     */
    @Override
    public boolean hasEdge(int node1, int node2) {

        return nodes.containsKey(node1) && nodes.containsKey(node2) && Neighbor.get(node1).containsKey(node2);
    }

    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1,node2)) {
            return Neighbor.get(node1).get(node2).doubleValue();
        }

        return -1;
    }
    /**
     * this method add node to the graph
     * @param
     */
    @Override
    public void addNode(int key) {
        if (!nodes.containsKey(key)) {
            NodeInfo n = new NodeInfo(key);
            nodes.put(key, n);
            Neighbor.put(key,new HashMap<Integer, Double>());

            MC++;
        }

    }
    /**
     * this method add an edge between these nodes
     * @param node1
     * @param node2
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (nodes.containsKey(node1) && nodes.containsKey(node2) && node1!=node2 && w > 0) {
            if(!hasEdge(node1,node2)){
                Neighbor.get(node1).putIfAbsent(node2, w);
                Neighbor.get(node2).putIfAbsent(node1, w);
                EdgeNum++;
                MC++;
            } if(hasEdge(node1,node2)&& Neighbor.get(node1).get(node2)!=w){
                Neighbor.get(node1).put(node2,w);
                Neighbor.get(node2).put(node1, w);
                MC++;
            }

        }
    }
    /**
     * This method return a pointer for the
     * collection representing all the nodes in the graph
     * @return
     */
    @Override
    public Collection<node_info> getV() {
        if (nodes != null) return nodes.values();
        return null;
    }
    /**
     * this method return collection of Neighbors of this node
     * @param node_id
     * @return
     */
    @Override
    public Collection<node_info> getV(int node_id) {
       Collection<node_info> V = new ArrayList<>();
        if (Neighbor.get(node_id) != null) {
            for (Integer M : Neighbor.get(node_id).keySet()) {
                V.add(nodes.get(M));
            }
        }
        return V;
    }
    /**
     * this method remove this node from the graph
     * @param key
     * @return
     */
    @Override
    public node_info removeNode(int key) {
       if(!nodes.containsKey(key)) return null;
        for (node_info M: getV(key)){
            Neighbor.get(M.getKey()).remove(key);
            EdgeNum--;
        }
        Neighbor.remove(key);
        MC++;
        return nodes.remove(key) ;

    }
    /**
     * this method remove this edge from the graph
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if(nodes.containsKey(node1)  && nodes.containsKey(node2)  && hasEdge(node1,node2)) {
            Neighbor.get(node1).remove(node2);
            Neighbor.get(node2).remove(node1);
            EdgeNum--;
            MC++;
        }


    }
    /**
     * this methode return the number of nodes in the grsph
     * @return map size
     */
    @Override
    public int nodeSize() {
        if (nodes != null) return nodes.size();{
    }
    return 0;
}
    /**
     * this methode return the number of edges in the graph
     * @return edge size
     */
    @Override
    public int edgeSize() {
        return EdgeNum;
    }
    /**
     * this methode return the number of All changes in the graph
     * @return MCcount
     */
    @Override
    public int getMC() {
        return MC;
    }
}
