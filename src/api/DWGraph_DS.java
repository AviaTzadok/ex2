package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph{
    private HashMap<Integer, node_data> nodes;
    private HashMap<Integer, HashMap<Integer, edge_data>> edge;
    private int EdgeNum;
    private int MC;

    /**
     * constructor who creates a new graph Type of HashMap
     */
    public DWGraph_DS(){
        this.nodes= new HashMap<Integer, node_data>();
        this.edge= new HashMap<Integer, HashMap<Integer, edge_data>>();
        this.EdgeNum=0;
        this.MC=0;
    }

    /**
     * this method return this node
     * @param key - the node_id
     * @return node
     */
    @Override
    public node_data getNode(int key) {
        if(!nodes.containsKey(key)) return null;
        return nodes.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        if(!edge.containsKey(src) || !edge.containsKey(dest)) return null;
        return edge.get(src).get(dest);
    }
    /**
     * this method add node to the graph
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if(n!=null) {
            if (!(nodes.containsKey(n.getKey()))) {
                NodeData h = new NodeData(n.getKey());
                nodes.put(n.getKey(), h);
                edge.put(n.getKey(), new HashMap<Integer, edge_data>());
                MC++;
                h.setLocation(n.getLocation());
                h.setInfo(n.getInfo());
                h.setTag(n.getTag());
                h.setWeight(n.getWeight());
            }
        }
    }
    /**
     * this method add an edge between these nodes ,And set the edge value
     * @param src
     * @param dest
     * @param w
     */
    @Override
    public void connect(int src, int dest, double w) {
        if (nodes.containsKey(src) && nodes.containsKey(dest) && src!=dest && w >= 0) {
            if(!edge.get(src).containsKey(dest)){
                EdgeData e= new EdgeData(src,dest,w);
                edge.get(src).putIfAbsent(dest,e);
                EdgeNum++;
                MC++;
            }else if(getEdge(src,dest).getWeight()!=w){
                EdgeData e= new EdgeData(src,dest,w);
                edge.get(src).put(dest,e);
                MC++;
            }
        }
    }


    @Override
    public Collection<node_data> getV() {
        if(nodes==null) {
            Collection<node_data> co = new ArrayList<node_data>();
            return co;
        }
        return nodes.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        if(edge.get(node_id)==null) {
            Collection<edge_data> co = new ArrayList<edge_data>();
            return co;
        }
        return edge.get(node_id).values();
    }
    /**
     * this method remove this node from the graph
     * @param key
     * @return
     */
    @Override
    public node_data removeNode(int key) {
        if(!nodes.containsKey(key)) return null;
        if(edge.get(key)==null){
            MC++;
            return removeNode(key);
        }
        for (node_data n: getV()) {
            if (n.getKey() != key) {
                for (edge_data m : getE(n.getKey())) {
                    if (m.getDest() == key) {
                        edge.get(m.getDest()).remove(key);
                        EdgeNum--;
                    }
                }
            }
        }
        edge_data[] edges=getE(key).toArray(new edge_data[0]);
        for (int i = 0; i < edges.length; i++) {
            edge.get(key).remove(edges[i].getDest());
            EdgeNum--;
        }
        edge.remove(key);
        MC++;
        return nodes.remove(key) ;
    }
    /**
     * this method remove this edge from the graph
     * @param src
     * @param dest
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if (!nodes.containsKey(src) && !nodes.containsKey(dest)) return null;
        if(!edge.get(src).containsKey(dest)) return null;
        EdgeNum--;
        MC++;
        return  edge.get(src).remove(dest);
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

    public void setMC(int mc) {
        this.MC=mc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DWGraph_DS that = (DWGraph_DS) o;

        if (EdgeNum != that.EdgeNum) return false;
        if (!nodes.equals(that.nodes)) return false;
        return edge.equals(that.edge);
    }

    @Override
    public int hashCode() {
        int result = nodes.hashCode();
        result = 31 * result + edge.hashCode();
        result = 31 * result + EdgeNum;
        return result;
    }
}