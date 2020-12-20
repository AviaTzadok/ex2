//package api;
//
//import com.google.gson.*;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.PrintWriter;
//import java.lang.reflect.Type;
//import java.util.*;
//
//public class DWGraph_Algo implements dw_graph_algorithms {
//    private directed_weighted_graph Graph;
//    private HashMap<Integer,NodeHelper> nodes;
//
//    public DWGraph_Algo() {
//        this.Graph = new DWGraph_DS();
//        this.nodes= new HashMap<Integer,NodeHelper>();
//
//    }
//    @Override
//    public void init(directed_weighted_graph g) {
//        this.Graph = g;
//    }
//
//    @Override
//    public directed_weighted_graph getGraph() {
//        return this.Graph;
//    }
//
//    @Override
//    public directed_weighted_graph copy() {
//        DWGraph_DS gr = new DWGraph_DS();
//        for (node_data N : Graph.getV()) {
//            node_data nd = new NodeData(N);
//            gr.addNode(nd);
//        }
//        for (node_data M : gr.getV()) {
//            for (edge_data f : Graph.getE(M.getKey())) {
//                gr.connect(f.getSrc(), f.getDest(), f.getWeight());
//            }
//        }
//        gr.setMC(0);
//        return gr;
//
//    }
////check
//    @Override
//    public boolean isConnected() {
//        int v = Graph.nodeSize();
//        int vn = 0;
//
//            int counter = 0;
//            if (Graph == null || Graph.nodeSize() == 0 || Graph.nodeSize() == 1) return true;
//        for (node_data n : Graph.getV()) {
//            Iterator<node_data> itr4 = Graph.getV().iterator();
//            while (itr4.hasNext())
//                itr4.next().setInfo("white");
//                Iterator<node_data> I = Graph.getV().iterator();
//                ArrayDeque<node_data> q = new ArrayDeque<node_data>();
//                q.push(n);
//                n.setInfo("black");
//                while (!q.isEmpty()) {
//                    Iterator<edge_data> I2 = Graph.getE(q.getFirst().getKey()).iterator();
//                    while (I2.hasNext()) {
//                        edge_data temp2 = I2.next();
//                        if (Graph.getNode(temp2.getDest()).getInfo() != "black") {
//                            temp2.setInfo("black");
//                            q.addLast(Graph.getNode(temp2.getDest()));
//                        }
//                    }
//                    q.pollFirst();
//                }
//                for (node_data n2: Graph.getV()) {
//                    if(n2.getInfo()=="white") return false;
//                }
//        }
//        return true;
//
//    }
//
//
//
//    private class NodeHelper {
//        double tag;
//        node_data n;
//        public NodeHelper(double tag, node_data n) {
//            this.n=n;
//            this.tag = tag;
//        }
//
//        public double getTag() {
//            return tag;
//        }
//
//        public node_data getN() {
//            return n;
//        }
//
//        public void setTag(double tag) {
//            this.tag = tag;
//        }
//
//        public void setN(node_data n) {
//            this.n = n;
//        }
//    }
//    @Override
//    public double shortestPathDist(int src, int dest) {
//        nodes=new HashMap<Integer, NodeHelper>();
//        for (node_data n: Graph.getV()) {
//            nodes.put(n.getKey(),new NodeHelper(Double.MAX_VALUE,n));
//        }
//        nodes.get(src).setTag(0);
//        PriorityQueue<NodeHelper> Q= new PriorityQueue<NodeHelper>(new NodeHelperComparator());
//        Q.add(nodes.get(src));
//        while (!Q.isEmpty()){
//            for (edge_data K: Graph.getE(Q.peek().getN().getKey())) {
//                if(nodes.get(K.getDest()).getTag()>Q.peek().getTag()+Graph.getEdge(K.getSrc(), K.getDest()).getWeight()){
//                    nodes.get(K.getDest()).setTag(Q.peek().getTag()+Graph.getEdge(K.getSrc(), K.getDest()).getWeight());
//                    nodes.get(K.getDest()).getN().setInfo(""+Q.peek().getN().getKey());
//                    Q.add(nodes.get(K.getDest()));
//                }
//            }
//            if (!Q.isEmpty()) Q.remove();
//        }
//        if(nodes.get(dest).getTag()==Double.MAX_VALUE) return -1;
//        return nodes.get(dest).getTag();
//    }
//    private class NodeHelperComparator implements Comparator<NodeHelper>{
//
//        @Override
//        public int compare(NodeHelper o1, NodeHelper o2) {
//            if(o1.getTag()<o2.getTag()) return -1;
//            if(o1.getTag()>o2.getTag()) return 1;
//            return 0;
//        }
//    }
//
//
////must check again
//    @Override
//    public List<node_data> shortestPath(int src, int dest) {
//
//        if (src == dest) {
//            LinkedList<node_data> l0 = new LinkedList<node_data>();
//            l0.add(Graph.getNode(src));
//            return l0;
//        }
//        shortestPathDist(src, dest);
//        LinkedList<node_data> l1 = new LinkedList<node_data>();
//        node_data mn = Graph.getNode(dest);
//        l1.addLast(mn);
//        directed_weighted_graph rg = revgraph();
//        while (nodes.get(mn.getKey()).getTag() != 0) {
//            Iterator<edge_data> itrr = rg.getE(mn.getKey()).iterator();
//            while (itrr.hasNext()) {
//                edge_data ee =itrr.next();
//                int nr = ee.getDest();
//                if (nodes.get(mn.getKey()).getTag() == nodes.get(nr).getTag() +  rg.getEdge(nr, mn.getKey()).getWeight()) {
//                    mn = rg.getNode(nr);
//                    l1.addFirst(mn);
//                    break;
//                }
//            }
//        }
//        return l1;
//    }
//
//    public directed_weighted_graph revgraph() {
//        DWGraph_DS rg = new DWGraph_DS();
//        node_data[]arr= Graph.getV().toArray(new node_data[0]);
//        for(int i=0;i<arr.length;i++) {
//            rg.addNode(new NodeData((arr[i])));
//        }
//        for(int i=0;i<arr.length;i++) {
//            Iterator<edge_data> itr = Graph.getE(arr[i].getKey()).iterator();
//            while (itr.hasNext()) {
//                int dn = itr.next().getDest();
//                rg.connect(dn, arr[i].getKey(), Graph.getEdge(arr[i].getKey(), (dn)).getWeight());
//            }
//        }
//        rg.setMC(Graph.getMC());
//        return rg;
//    }
//
//    @Override
//    public boolean save(String file) {
//        Gson gson = new GsonBuilder().create();
//        String json =gson.toJson(Graph);
//        try{
//            PrintWriter pw= new PrintWriter(new File((file)));
//            pw.write(json);
//            pw.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//private  class jsondeserializer implements JsonDeserializer<directed_weighted_graph> {
//
//    @Override
//    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//        JsonObject o = jsonElement.getAsJsonObject();
//        JsonArray arje = o.get("Edges").getAsJsonArray();
//        directed_weighted_graph g0 = new DWGraph_DS();
//        JsonArray arjn = o.get("Nodes").getAsJsonArray();
//        for (int i = 0; i < arjn.size(); i++) {
//            JsonObject jn = arjn.get(i).getAsJsonObject();
//            g0.addNode(new NodeData(jn.get("id").getAsInt()));
//            String sp = jn.get("pos").getAsString();
//            String sar[]=sp.split(",");
//            g0.getNode(jn.get("id").getAsInt()).setLocation(new GeoLocation(Double.parseDouble(sar[0]),Double.parseDouble(sar[1]),Double.parseDouble(sar[2])));
//        }
//        for (int j = 0; j <arje.size(); j++) {
//            JsonObject je = arje.get(j).getAsJsonObject();
//            g0.connect(je.get("src").getAsInt(),je.get("dest").getAsInt(),je.get("w").getAsDouble());
//        }
//        return g0;
//    }
//}
//    @Override
//    public boolean load(String file) {
//        GsonBuilder g=new GsonBuilder();
//        jsondeserializer des=new jsondeserializer();
//        g.registerTypeAdapter(directed_weighted_graph.class,des);
//        try {
//            Gson gson= g.create();
//            FileReader reader = new FileReader(file);
//            Graph= gson.fromJson(reader,directed_weighted_graph.class);
//            return  true;
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    public boolean load2(String file2) {
//        directed_weighted_graph gra=Graph;
//        GsonBuilder g=new GsonBuilder();
//        jsondeserializer des=new jsondeserializer();
//        g.registerTypeAdapter(directed_weighted_graph.class,des);
//        Gson gson= g.create();
//        try {
//            gra= gson.fromJson(file2,directed_weighted_graph.class);
//            Graph=gra;
//
//        } catch (Exception ex) {
//            gra=Graph;
//        ex.printStackTrace();
//            return false;
//        }
//        return true;
//
//    }
//
//    }
package api;

import com.google.gson.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.*;
import java.util.List;

public class DWGraph_Algo implements dw_graph_algorithms, Comparator<node_data> {
    private directed_weighted_graph Graph;
    private HashMap<Integer, NodeHelper> nodes;

    public DWGraph_Algo() {
        this.Graph = new DWGraph_DS();
        this.nodes = new HashMap<Integer, NodeHelper>();

    }

    @Override
    public void init(directed_weighted_graph g) {
        this.Graph = g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.Graph;
    }

    /**
     * this methode creates a deep copy of this graph
     *
     * @return copy graph
     */
    @Override
    public directed_weighted_graph copy() {
        DWGraph_DS gr = new DWGraph_DS();
        for (node_data N : Graph.getV()) {
            node_data nd = new NodeData(N);
            gr.addNode(nd);
        }
        for (node_data M : gr.getV()) {
            for (edge_data f : Graph.getE(M.getKey())) {
                gr.connect(f.getSrc(), f.getDest(), f.getWeight());
            }
        }
        gr.setMC(0);
        return gr;

    }

    /**
     * this method checks whether the graph is connected
     *
     * @return true or false
     */
    @Override
    public boolean isConnected() {
        if (getGraph()==null) return true;
        if(getGraph().nodeSize()==0 || getGraph().nodeSize()==0) return true;
        for (node_data m : Graph.getV()) {
            m.setTag(-1);
        }
        Iterator<node_data> itr = Graph.getV().iterator();
        node_data n = itr.next();
        node_data temp = n;
        LinkedList<node_data> q = new LinkedList<>();
        q.addFirst(temp);
        temp.setTag(0);
        while (!q.isEmpty()) {
            temp = q.pollFirst();
            for (edge_data e : Graph.getE(temp.getKey())) {
                node_data m = Graph.getNode(e.getDest());
                if (m.getTag() == -1) {
                    m.setTag(0);
                    q.addLast(m);
                }
            }
        }
        for (node_data r : Graph.getV()) {
            if (r.getTag() == -1)
                return false;

        }
        directed_weighted_graph rg = revgraph();
        for (node_data r : rg.getV()) {
            r.setTag(-1);
        }
        temp = n;
        temp.setTag(0);
        q.addFirst(temp);
        while (!q.isEmpty()) {
            temp = q.pollLast();
            for (edge_data e : rg.getE(temp.getKey())) {
                node_data m = rg.getNode(e.getDest());
                if (m.getTag() == -1) {
                    m.setTag(0);
                    q.addLast(m);
                }

            }
        }
        for (node_data r : rg.getV()) {
            if (r.getTag() == -1)
                return false;

        }
        return true;
    }

    @Override
    public int compare(node_data o1, node_data o2) {
        int ans = 0;
        if (o1.getWeight() > o2.getWeight())
            ans = 1;
        if (o2.getWeight() > o1.getWeight())
            ans = -1;
        return ans;
    }

    private class NodeHelper {
        double tag;
        node_data n;

        public NodeHelper(double tag, node_data n) {
            this.n = n;
            this.tag = tag;
        }

        public double getTag() {
            return tag;
        }

        public node_data getN() {
            return n;
        }

        public void setTag(double tag) {
            this.tag = tag;
        }

        public void setN(node_data n) {
            this.n = n;
        }
    }

    /**
     * this method return the shortest path between  these nodes By schematic of the weight of the ribs between the vertices
     * We implemented the priority queue function in DWGraph_DS
     * so when we created the priority queue we instructed him to take the implementation in DWGraph_DS
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return The shortest path size
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (Graph.getNode(src) == null || Graph.getNode(dest) == null)
            if (src == dest)
                return 0;
        for (node_data node : Graph.getV()) {
            node.setWeight(0);
            node.setTag(-1);

        }

        PriorityQueue<node_data> q = new PriorityQueue(new DWGraph_Algo());
        node_data n = Graph.getNode(src);
        n.setTag(0);
        q.add(n);
        while (!q.isEmpty()) {
            n = q.poll();
            for (edge_data edge : Graph.getE(n.getKey())) {
                node_data tempNode = Graph.getNode(edge.getDest());
                if (tempNode.getTag() == -1) {
                    tempNode.setTag(0);
                    tempNode.setWeight(n.getWeight() + edge.getWeight());
                    q.add(tempNode);
                } else if (tempNode.getWeight() > n.getWeight() + edge.getWeight()) {
                    tempNode.setWeight(n.getWeight() + edge.getWeight());
                }

            }
        }
        if (Graph.getNode(dest).getTag() == -1)
            return -1;
        return Graph.getNode(dest).getWeight();
    }

    private class NodeHelperComparator implements Comparator<NodeHelper> {


        @Override
        public int compare(NodeHelper o1, NodeHelper o2) {
            if (o1.getTag() < o2.getTag()) return -1;
            if (o1.getTag() > o2.getTag()) return 1;
            return 0;
        }
    }

    /**
     * this methode returns the a list of the nodes of the shortest path between the nodes
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return list of nodes
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if (Graph.getNode(src) != null && Graph.getNode(dest) != null) {
            if (src == dest) {
                LinkedList<node_data> l0 = new LinkedList<node_data>();
                l0.add(Graph.getNode(src));
                return l0;
            }
            shortestPathDist(src, dest);
            if (Graph.getNode(dest).getTag() == -1)
                return null;
            LinkedList<node_data> l1 = new LinkedList<node_data>();
            node_data mn = Graph.getNode(dest);
            l1.addLast(mn);
            directed_weighted_graph rg = revgraph();
            while (mn.getWeight() != 0) {
                Iterator<edge_data> itrr = rg.getE(mn.getKey()).iterator();
                while (itrr.hasNext()) {
                    edge_data ee = itrr.next();
                    int nr = ee.getDest();
                    if (mn.getWeight() == Graph.getNode(nr).getWeight() + rg.getEdge(mn.getKey(), nr).getWeight()) {
                        mn = rg.getNode(nr);
                        l1.addFirst(mn);
                        break;
                    }
                }
            }
            return l1;
        } else return null;
    }

    /**
     * this methode Reverses the order of the vertices we got from shortestPath
     *
     * @return
     */
    public directed_weighted_graph revgraph() {
        directed_weighted_graph rg = new DWGraph_DS();
        node_data[] arr = Graph.getV().toArray(new node_data[0]);
        for (int i = 0; i < arr.length; i++) {
            rg.addNode(new NodeData((arr[i])));
        }
        for (int i = 0; i < arr.length; i++) {
            Iterator<edge_data> itr = Graph.getE(arr[i].getKey()).iterator();
            while (itr.hasNext()) {
                edge_data e=itr.next();
                rg.connect(e.getDest(), e.getSrc(), e.getWeight());
            }
        }
        return rg;
    }

    /**
     * This method saves the graph we created in a text file on our computer by converting our programming language to a text file
     * Attached is a link to the site from which I took the code
     *
     * @param file - the file name (may include a relative path).
     * @return
     */
    @Override
    public boolean save(String file) {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(Graph);
        try {
            PrintWriter pw = new PrintWriter(new File((file)));
            pw.write(json);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public class jsondeserializer implements JsonDeserializer<directed_weighted_graph> {

        @Override
        public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject o = jsonElement.getAsJsonObject();
            JsonArray arje = o.get("Edges").getAsJsonArray();
            directed_weighted_graph g0 = new DWGraph_DS();
            JsonArray arjn = o.get("Nodes").getAsJsonArray();
            for (int i = 0; i < arjn.size(); i++) {
                JsonObject jn = arjn.get(i).getAsJsonObject();
                g0.addNode(new NodeData(jn.get("id").getAsInt()));
                String sp = jn.get("pos").getAsString();
                String sar[] = sp.split(",");
                g0.getNode(jn.get("id").getAsInt()).setLocation(new GeoLocation(Double.parseDouble(sar[0]), Double.parseDouble(sar[1]), Double.parseDouble(sar[2])));
            }
            for (int j = 0; j < arje.size(); j++) {
                JsonObject je = arje.get(j).getAsJsonObject();
                g0.connect(je.get("src").getAsInt(), je.get("dest").getAsInt(), je.get("w").getAsDouble());
            }
            return g0;
        }
    }

    /**
     * This method uploads a text file of our graph from the computer and converts it into a programming language
     * Attached is a link to the site from which I took the code
     *
     * @param file - file name
     * @return
     */
    @Override
    public boolean load(String file) {
        GsonBuilder g = new GsonBuilder();
        jsondeserializer des = new jsondeserializer();
        g.registerTypeAdapter(directed_weighted_graph.class, des);
        try {
            Gson gson = g.create();
            FileReader reader = new FileReader(file);
            Graph = gson.fromJson(reader, directed_weighted_graph.class);
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loadfs(String file2) {
        directed_weighted_graph gra = Graph;
        GsonBuilder g = new GsonBuilder();
        jsondeserializer des = new jsondeserializer();
        g.registerTypeAdapter(directed_weighted_graph.class, des);
        Gson gson = g.create();
        try {
            gra = gson.fromJson(file2, directed_weighted_graph.class);
            Graph = gra;
        } catch (Exception ex) {
            Graph = gra;
            ex.printStackTrace();
            return false;
        }
        return true;
    }

}