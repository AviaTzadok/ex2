package ex1.src;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph Graph;
    @Override
    public void init(weighted_graph g) {
        this.Graph=g;
    }

    @Override
    public weighted_graph getGraph() {

        return this.Graph;
    }

    /**
     * this methode creates a deep copy of this graph
     * @return copy graph
     */
    @Override
    public weighted_graph copy() {
        weighted_graph gr= new WGraph_DS();
        for (node_info N: Graph.getV()) {
           gr.addNode(N.getKey());
           gr.getNode(N.getKey()).setInfo(N.getInfo());
           gr.getNode(N.getKey()).setTag(N.getTag());
        }
        for (node_info M: gr.getV())  {
            for (node_info f: Graph.getV(M.getKey())) {
               gr.connect(M.getKey(),f.getKey(),Graph.getEdge(M.getKey(),f.getKey()));

            }

        }
        return gr;
    }
    /**
     *this method checks whether the graph is connected
     * @return true or false
     */
    @Override
    public boolean isConnected() {
        if (Graph == null || Graph.nodeSize() == 0||Graph.nodeSize()==1) return true;
        Iterator<node_info> itr4 = Graph.getV().iterator(); //We will define iterator so that we can go through all the nodes.
        // because it can not go through the organs in order
        // (since the organs are in a certain order)
        while (itr4.hasNext())
            itr4.next().setInfo("white");              // We will initialize all the "Info of nodes" to be white
        int v = Graph.nodeSize();
        int counter = 0;

        Iterator<node_info> I = Graph.getV().iterator();  // define new iterator
        node_info first = I.next();
        ArrayDeque<node_info> q = new ArrayDeque<node_info>();
        q.push(first);                                 // push the first limb to the ArrayDeque
        first.setInfo("black");                        // We will paint "Info" in black ,so that we do not appropriate it again
        while (!q.isEmpty()) {                         //We'll go over with iterator the neighbors of the last peek
            I = Graph.getV(q.peekLast().getKey()).iterator();
            while (I.hasNext()) {
                node_info temp2 = I.next();
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
     * this method return the shortest path between  these nodes By schematic of the weight of the ribs between the vertices
     * We implemented the priority queue function in WGraph_DS
     * so when we created the priority queue we instructed him to take the implementation in WGraph_DS
     * @param src - start node
     * @param dest - end (target) node
     * @return The shortest path size
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        for (node_info M: Graph.getV()) {
            M.setTag(Double.MAX_VALUE);
        }
        Graph.getNode(src).setTag(0);
        PriorityQueue<node_info> Q= new PriorityQueue<>(new WGraph_DS());
        Q.add(Graph.getNode(src));
        while (!Q.isEmpty()){
            for (node_info K: Graph.getV(Q.peek().getKey())) {
                if(K.getTag()>Q.peek().getTag()+Graph.getEdge(K.getKey(),Q.peek().getKey())){
                    K.setTag(Q.peek().getTag()+Graph.getEdge(K.getKey(),Q.peek().getKey()));
                    Q.add(K);
                }
            }
            if (!Q.isEmpty()) Q.remove();

        }
        if(Graph.getNode(dest).getTag()==Double.MAX_VALUE) return -1;
        return Graph.getNode(dest).getTag();
    }
    /**
     * this methode returns the a list of the nodes of the shortest path between the nodes
     * @param src - start node
     * @param dest - end (target) node
     * @return list of nodes
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
      if(shortestPathDist(src, dest)==-1) return null;
      if(Graph.getNode(src)==null || Graph.getNode(dest)==null) return null;
        LinkedList<node_info> arr=new LinkedList<>();
        node_info de= Graph.getNode(dest);
        arr.addFirst(de);
        while (de.getTag()!=0) {
            for (node_info M : Graph.getV(de.getKey())) {
                if (Graph.getNode(de.getKey()).getTag() ==M.getTag()+ Graph.getEdge(de.getKey(),M.getKey()) ) {
               arr.addFirst(Graph.getNode(M.getKey()));
               de=M;
                }
            }

        }
        return arr;
    }


    /**
     * This method saves the graph we created in a text file on our computer by converting our programming language to a text file
     * Attached is a link to the site from which I took the code
     * @param file - the file name (may include a relative path).
     * @return
     */

    @Override//https://www.geeksforgeeks.org/serialization-in-java/
    public boolean save(String file) {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(Graph);
            out.close();
            fileOut.close();

        }

        catch(IOException ex)
        {
            return false;
        }

        return true;
    }

    /**
     * This method uploads a text file of our graph from the computer and converts it into a programming language
     * Attached is a link to the site from which I took the code
     * @param file - file name
     * @return
     */

    @Override//https://www.geeksforgeeks.org/serialization-in-java/
    public boolean load(String file) {
        try
        {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Graph = (WGraph_DS)in.readObject();
            in.close();
            fileIn.close();

        }

        catch(IOException ex)
        {
            return false;
        }

        catch(ClassNotFoundException ex)
        {
            return false;
        }
        return true;
    }
}
