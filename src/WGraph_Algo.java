package ex1.src;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph g0;

    public WGraph_Algo(){
        this.g0 = g0;
    }

    /**
     * Initiate the graph.
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.g0 = g;
    }

    /**
     * Get a weighted graph.
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return this.g0;
    }

    /**
     * A deep copy that initiate the weighted_graph_algorithms.
     * @return
     */
    @Override
    public weighted_graph copy() {
        weighted_graph a = new WGraph_DS(this.g0);
        return a;
    }

    /**
     * Check if the graph is connected or not, using BFS that goes all the node's graph
     * and color all of them white and start from a random vertex which we will start going over the graph,
     * color the initial vertex gray, that means that we passed of it but not finish with it, we put it in a queue.
     * While the queue isn't empty we will take out the first vertex that is there
     * and go over it's neighbors, we will put all it's neighbors in the queue and color them gray,
     * after we have finished going through all the neighbors of the initial vertex, we will color
     * the same vertex in black because we finished with it and put all the neighbors in the queue,
     * So we will also go over the next vertex, we will put in all it's neighbors in the queue if
     * it's neighbors are white and so on until we go on all the vertices.
     * @return true if the graph is connected, it means if all the vertices black
     * ,else, return false.
     */
    @Override
    public boolean isConnected() {
        if (this.g0.nodeSize() == 0 || this.g0.nodeSize() == 1) return true;
        Queue<node_info> q = new LinkedList<>();
        setTagsToZero(g0.getV());
        Iterator<node_info> t = this.g0.getV().iterator();
        node_info n = t.next();
        q.add(n);
        n.setTag(1);
        while (!q.isEmpty()) {
            node_info m = q.poll();
            for (node_info a : g0.getV(m.getKey())) {
                if (a.getTag() == 0) {
                    q.add(a);
                    a.setTag(1);
                }
            }
            m.setTag(2);
        }
        for (node_info b : this.g0.getV()) {
            if (b.getTag() == 0) return false;
        }
        return true;
    }

    /**
     * Calculate the shortest path from node src to node dest, using in dijkstra algorithm,
     * the dijkstra algorithm set infinite all the nodes and the src it's set to zero and add to the Priority Queue.
     * Now, we do a for loop that over all the neighbors of this node, it's checks if the sum fo the tag of the node that
     * we over all it's neighbors plus the weight of the edge between it's neighbor to it small from the tag of the
     * neighbor, if it is, so we set the tag of the neighbor to the smallest tag' and so on until we find the
     * shortest path to the dest.
     * @param src - start node
     * @param dest - end (target) node
     * @return the shortest path from node src to node dest.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if(g0.nodeSize() == 0 || g0.getNode(src) == null || g0.getNode(dest) == null) return -1;
        if(src == dest) return 0;
        PriorityQueue<node_info> pq = new PriorityQueue<>();
        HashMap<node_info, Boolean> visited = new HashMap<>();
        setTagsToInfinite(g0.getV());
        node_info s = g0.getNode(src);
        s.setTag(0);
        pq.add(s);
        while(!pq.isEmpty()) {
            node_info min = pq.poll();
            if (!visited.containsKey(min.getKey())) {
                visited.put(min, true);
                if (min == g0.getNode(dest)) return g0.getNode(dest).getTag();
                for (node_info n : g0.getV(min.getKey())) {
                    if ((min.getTag() + g0.getEdge(min.getKey(), n.getKey())) < n.getTag()) {
                        n.setTag(min.getTag() + g0.getEdge(min.getKey(), n.getKey()));
                        pq.add(n);
                    }
                }
            }
        }
        return g0.getNode(dest).getTag();
    }

    /**
     * /**
     * Calculate the shortest path from node src to node dest, using in dijkstra algorithm,
     * the dijkstra algorithm set infinite all the nodes and the src it's set to zero and add to the Priority Queue.
     * Now, we do a for loop that over all the neighbors of this node, it's checks if the sum fo the tag of the node that
     * we over all it's neighbors plus the weight of the edge between it's neighbor to it small from the tag of the
     * neighbor, if it is, so we set the tag of the neighbor to the smallest tag and so on until we find the
     * shortest path to the dest and add the nodes (that we passed of them to get the shortest path) to the list.
     * @param src - start node
     * @param dest - end (target) node
     * @return the list of nodes that give us the shortest path.
     */

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (g0.nodeSize() == 0 || g0.getNode(src) == null || g0.getNode(dest) == null) return null;
        shortestPathDist(src, dest);
        LinkedList<node_info> list = new LinkedList<>();
        PriorityQueue<node_info> pq = new PriorityQueue<>();
        node_info d = g0.getNode(dest);
        pq.add(d);
        list.addFirst(d);
        while (!pq.isEmpty()) {
            node_info m = pq.poll();

            double min = m.getTag();
            node_info s = null;
            for (node_info n : g0.getV(m.getKey())) {
                if ((n.getTag() + g0.getEdge(n.getKey(), m.getKey())) == m.getTag()) {
                    min = n.getTag();
                    s = n;
                }
            }
            if (list.contains(g0.getNode(src))) return list;
            pq.add(s);
            list.addFirst(s);
        }
        return list;
    }

    /**
     * Infinites all the vertices at the graph.
     * @param c
     */
    private void setTagsToInfinite(Collection<node_info> c){
        for(node_info n : c){
            n.setTag(Double.POSITIVE_INFINITY);
        }
    }

    /**
     *  Zeros all the vertices at the graph.
     * @param c
     */
    private static void setTagsToZero (Collection<node_info> c){
        for(node_info n : c) {
            n.setTag(0);
        }
    }

    /**
     * Saves this weighted graph to the given file name
     * @param file - the file name (may include a relative path).
     * @return true if the file is save.
     */
    @Override
    public boolean save(String file) {
        if (!file.contains(file + ".txt")) {
            try {
                PrintWriter pw = new PrintWriter(new File(file));
                StringBuilder sb = new StringBuilder();
                sb.append("The Mode Count of the graph is: ");
                sb.append(g0.getMC());
                sb.append("\n");
                sb.append("The number of Nodes in the graph is: ");
                sb.append(g0.nodeSize());
                sb.append("\n");
                sb.append("The number of Edges in the graph is: ");
                sb.append(g0.edgeSize());
                sb.append("\n");
                pw.write(sb.toString());
                sb.setLength(0);
                sb.append("Nodes in the graph: ");
                sb.append("\n");
                //for (node_info n : g0.getV()) {
                Iterator<node_info> it = g0.getV().iterator();
                while (it.hasNext()) {
                    node_info n = it.next();
                    sb.append("The node's graph is: ");
                    sb.append(n.getKey());
                    sb.append(",");
                    sb.append("The tag is:");
                    n.setTag(0);
                    sb.append(n.getTag());
                    sb.append("\n");
                    pw.write(sb.toString());
                    sb.setLength(0);
                }
                sb.append("Edges in the graph: ");
                sb.append("\n");
                for (node_info n : g0.getV()) {
                    for (node_info m : g0.getV(n.getKey())) {
                        sb.append("The neighbor of node ");
                        sb.append(n.getKey());
                        sb.append(" is: ");
                        sb.append(m.getKey());
                        sb.append(" The number of edge between them is: ");
                        sb.append(g0.getEdge(n.getKey(), m.getKey()));
                        sb.append("\n");
                        pw.write(sb.toString());
                        sb.setLength(0);
                    }
                }
                pw.close();
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true if the file is load.
     */
    @Override
    public boolean load(String file) {
        if(!file.contains(file + ".txt")){
            try{
                FileReader fr = new FileReader(file);
                BufferedReader bf = new BufferedReader(fr);
                String str = "";
                str = bf.readLine();
                for(int i = 1; str != null; i++){
                    str = bf.readLine();
                    if(str != null)
                        System.out.println(i + "," + str);
                }
                bf.close();
                fr.close();
                return true;
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        return false;
    }
}