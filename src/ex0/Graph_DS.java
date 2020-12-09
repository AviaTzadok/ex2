package ex0;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.BiConsumer;

/**
 * This class implements the graph interface and represents an undirected unweighted graph.
 * this graph support a large number of nodes (over 10^6, with average degree of 10)
 */
public class Graph_DS implements graph  {

    public Object iterator;
    private HashMap<Integer,node_data> map;
    private int size=0;
    private int MCcount=0;

    /**
     * constructor who creates a new graph Type of HashMap
     */
    public Graph_DS(){
        map=new HashMap<Integer, node_data>();

    }

    /**
     * this method return this node
     * @param key - the node_id
     * @return node
     */
    public node_data getNode(int key) {
        return map.get(key);
    }

    /**
     * this method Checks if there is a edge between this nodes
     * @param node1
     * @param node2
     * @return true/false
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if(map.get(node1).hasNi(node2)) return true;
        return false;
    }

    /**
     * this method add node to the graph
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if(n!=null){
        map.put(n.getKey(), n);
        MCcount++;
    }
    }

    /**
     * this method add an edge between these nodes
     * @param node1
     * @param node2
     */
    @Override
    public void connect(int node1, int node2) {
        if(node1==node2) return;
        if(map.get(node1)==null || map.get(node2)==null) return;
        if(map.get(node1).hasNi(node2)) return;
        map.get(node1).addNi(map.get(node2));
        map.get(node2).addNi(map.get(node1));
        size++;
        MCcount++;
    }

    /**
     * This method return a pointer for the
     * collection representing all the nodes in the graph
     * @return
     */
    @Override
    public Collection<node_data> getV() {
        if(map!=null)
        return  map.values();
        Collection<node_data> map2=new ArrayList<node_data>();
        return map2;
    }

    /**
     * this method return collection of Neighbors of this node
     * @param node_id
     * @return
     */
    @Override
    public Collection<node_data> getV(int node_id) {
        return map.get(node_id).getNi();
    }

    /**
     * this method remove this node from the graph
     * @param key
     * @return
     */
    @Override
    public node_data removeNode(int key) {
        if(map==null) return null;
        if(map.get(key)==null) return null;
        if(map.get(key).getNi().size()!=0){
            node_data[] arr=map.get(key).getNi().toArray(new node_data[0]);
            for(int i=0; i<arr.length;i++){
                arr[i].removeNode(map.get(key));
                size--;
            }

        }
        MCcount++;
        return map.remove(key);
    }

    /**
     * this method remove this edge from the graph
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if(map.get(node1).hasNi(node2)){
        map.get(node1).removeNode(map.get(node2));
        map.get(node1).removeNode(map.get(node2));
        size--;
        MCcount++;}

    }

    /**
     * this methode return the number of nodes in the grsph
     * @return map size
     */
    @Override
    public int nodeSize() {
        if(map!=null)
        return map.size();
        return 0;
    }

    /**
     * this methode return the number of edges in the graph
     * @return edge size
     */
    @Override
    public int edgeSize() {
        return size;
    }

    /**
     * this methode return the number of All changes in the graph
     * @return MCcount
     */
    @Override
    public int getMC() {
        return MCcount;
    }
}
