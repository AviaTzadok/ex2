package api;
/**
 * this class Represents the edges on the graph
 */
public class EdgeData implements edge_data {
    private int src;
    private int dest;
    private int tag;
    private String info;
    private double weight;
    /**
     * constructor who creates a new edge on the graph
     *@param s
     *@param d
     *@param w
     */
    public EdgeData(int s, int d, double w) {
        this.src = s;
        this.dest = d;
        this.weight = w;
        this.tag = 0;
        this.info = "";
    }

    @Override
    public int getSrc() {
        return src;
    }

    @Override
    public int getDest() {
        return dest;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

}

