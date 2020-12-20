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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EdgeData edgeData = (EdgeData) o;

        if (src != edgeData.src) return false;
        if (dest != edgeData.dest) return false;
        return Double.compare(edgeData.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = src;
        result = 31 * result + dest;
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

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

