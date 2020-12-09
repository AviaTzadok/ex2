package ex0;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * This class implements the NodeData interface and defines the attributes of the node.
 * Key, Info, Tag, neighbors of the node.
 * and functions on the node_data in the graph++++++
 */
public class NodeData implements node_data {
    private int key;
    private HashMap<Integer,node_data> mapNi;
    private int tag;
    private String Info;
    private static int keyNew;

    /**
     * constructor who creates a new node_data
     */
    public NodeData(){
        mapNi=new HashMap<Integer, node_data>();
        key=keyNew++;
    }

    /**
     * this method Changes the value of this node
     * @param k
     */
    public void setKey(int k){
        this.key=k;
    }

    /**
     * this method returns the key of this node
     * @return the  key of this node_data
     */
    @Override
    public int getKey() {

        return key;
    }

    /**
     * this method returns the list of neighbors of this node
     * @return mapNi
     */
    @Override
    public Collection<node_data> getNi() {
        if(mapNi!=null)
        return mapNi.values();
        Collection<node_data> temp=new ArrayList<node_data>();//???
        return temp;
    }

    /**
     * this method checks if this node and the node with this this key are adjacent
     * @param key
     * @return true or false
     */
    @Override
    public boolean hasNi(int key) {
        if(mapNi.get(key)==null)
        return false;
        return true;
    }

    /**
     * this method Adds a t to the neighbour's list
     * @param t
     */
    @Override
    public void addNi(node_data t) {
        if(t.getKey()!=key)
        mapNi.put(t.getKey(),t);
    }

    /**
     * this method removes this node from the graph
     * @param node
     */
    @Override
    public void removeNode(node_data node) {
        mapNi.remove(node.getKey());
    }

    /**
     * this method returns the Info of this node
     * @return info
     */
    @Override
    public String getInfo() {
        return Info;
    }

    /**
     * this method Changes the info of this node
     * @param s
     */
    @Override
    public void setInfo(String s) {
        Info=s;
    }

    /**
     * this method returns the Tag of this node
     *
     * @return tag
     */
    @Override
    public int getTag() {
        return tag;
    }

    /**
     * this method changes the value of this node
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        tag=t;
    }
}
