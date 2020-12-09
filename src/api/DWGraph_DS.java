package api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph{
    private HashMap<Integer, node_data> nodes;
    private HashMap<Integer, HashMap<Integer, edge_data>> edge;
    private int EdgeNum;
    private int MC;

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

    @Override
    public void addNode(node_data n) {
        if (!nodes.containsKey(n.getKey())) {
            NodeData h= new NodeData(n.getKey());
            nodes.put(n.getKey(),h);
            edge.put(n.getKey(),new HashMap<Integer, edge_data>());
            MC++;
            h.setLocation(n.getLocation());
            h.setInfo(n.getInfo());
            h.setTag(n.getTag());
            h.setWeight(n.getWeight());
        }
        }

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
        if (nodes != null) return nodes.values();
        return null;
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return edge.get(node_id).values();
    }

    @Override
    public node_data removeNode(int key) {
        if(!nodes.containsKey(key)) return null;
        for (node_data n: getV()) {
            if(n.getKey()!=key){
            for (edge_data m : getE(n.getKey())) {
                if(m.getDest()==key) {
                    edge.get(n.getKey()).remove(key);
                    EdgeNum--;
                }
            }}
            for (edge_data k: getE(key)) {
                edge.get(key).remove(k.getDest());
                EdgeNum--;
            }
        }
        edge.remove(key);
        MC++;
        return nodes.remove(key) ;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        if (!nodes.containsKey(src) && !nodes.containsKey(dest)) return null;
        EdgeNum--;
        return  edge.get(src).remove(dest);
    }

    @Override
    public int nodeSize() {
        if (nodes != null) return nodes.size();{
        }
        return 0;
    }

    @Override
    public int edgeSize() {
        return EdgeNum;
    }

    @Override
    public int getMC() {
        return MC;
    }

    public void setMC(int mc) {
        this.MC=mc;
    }
}
