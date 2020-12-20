package api;

/**
 * this class Represents the nods on the graph
 */
public class NodeData implements node_data {
    private int key;
    private int tag;
    private double weight;
    private String info;
    private  geo_location location;
    /**
     * constructor who creates a new node on the graph
     *@param key
     */
public NodeData(int key){
    this.key=key;
    this.tag=0;
    this.info="";
    this.location=new GeoLocation();
}

    public NodeData(node_data n){
        this.key=n.getKey();
        this.tag=n.getTag();
        this.info=n.getInfo();
        this.weight=n.getWeight();
        setLocation(n.getLocation());
    }


    @Override
    public int getKey() {
        return this.key;
    }

    public void setKey(int k){
    this.key=k;
    }

    @Override
    public geo_location getLocation() {

        return this.location;
    }

    @Override
    public void setLocation(geo_location p) {
     this.location = p;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double w) {
    this.weight=w;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
    this.info=s;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
    this.tag=t;
    }
}
