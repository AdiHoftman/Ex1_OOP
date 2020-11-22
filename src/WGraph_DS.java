package ex1.src;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class WGraph_DS implements weighted_graph {
    private HashMap<Integer, node_info> vertices;
    private HashMap<node_info, HashMap<node_info, Double>> edges;
    private int nodeSize;
    private int edgeSize;
    private int mC;

    public WGraph_DS() {
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
        this.nodeSize = 0;
        this.edgeSize = 0;
        this.mC = 0;
    }

    public WGraph_DS(weighted_graph g) {
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
        for (node_info n : g.getV()) {
            node_info t = new NodeInfo(n.getKey());
           this.vertices.put(t.getKey(), t);
           this.edges.put(t, new HashMap<>());
        }
        for (node_info n : g.getV()){
            for (node_info m : g.getV(n.getKey())){
                if(g.getEdge(n.getKey(), m.getKey()) > -1.0){
                    this.connect(n.getKey(), m.getKey(), g.getEdge(n.getKey(), m.getKey()));
                }
            }
        }
        this.nodeSize = g.nodeSize();
        this.edgeSize = g.edgeSize();
        this.mC = g.getMC();
    }

    /**
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        return this.vertices.get(key);
    }

    /**
     * checks if between two nodes have an edge or not.
     * @param node1
     * @param node2
     * @return true if have an edge, else return false.
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if(this.edges.get(this.getNode(node1)) != null
                && this.edges.get(this.getNode(node1)).get(this.getNode(node2)) != null)
            return true;
        return false;
    }

    /**
     * return the weight of the edge between two nodes.
     * @param node1
     * @param node2
     * @return the weight of the edge.
     */
    @Override
    public double getEdge(int node1, int node2) {
        if(this.hasEdge(node1,node2))
            return this.edges.get(this.getNode(node1)).get(this.getNode(node2));
        return -1;
    }

    /**
     * add a node to the graph.
     * @param key
     */
    @Override
    public void addNode(int key) {
        if(vertices.containsKey(key)) return;
        this.vertices.put(key, new NodeInfo(key));
        this.edges.put(this.getNode(key), new HashMap<>());
        nodeSize++;
        mC++;
    }

    /**
     * add a edge between two nodes on the graph.
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if ((this.vertices.containsKey(node1)) && (this.vertices.containsKey(node2)) && ((node1) != (node2))) {
            if(this.hasEdge(node1, node2)) {
                this.getEdge(node1, node2);
                mC++;
            }
            if (!this.hasEdge(node1, node2)) {
                this.edges.get(this.getNode(node1)).put(this.getNode(node2), w);
                this.edges.get(this.getNode(node2)).put(this.getNode(node1), w);
                edgeSize++;
                mC++;
            }
        }
    }

    /**
     * @return the nodes in the graph.
     */
    @Override
    public Collection<node_info> getV() {
        return this.vertices.values();
    }

    /**
     * @param node_id
     * @return the collection of specified node_id.
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if (this.edges.containsKey(this.getNode(node_id)))
            return this.edges.get(this.getNode(node_id)).keySet();
        return null;
    }

    /**
     * remove a node from the graph.
     * @param key
     * @return the node that removed from the graph.
     */
    @Override
    public node_info removeNode(int key) {
        if(this.vertices.containsKey(key)){
            node_info n = this.getNode(key);
            if(this.edges.containsKey(this.getNode(key))) {
                Iterator<node_info> it = this.getV(key).iterator();
                while (it.hasNext()) {
                    node_info i = it.next();
                    removeEdge(key, i.getKey());
                    it = this.getV(key).iterator();
                }
                this.edges.get(this.getNode(key)).remove(key);
            }
            this.vertices.remove(key);
            mC++;
            nodeSize--;
            return n;
        }
        return null;
    }

    /**
     * remove edge between two nodes in the graph.
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if(this.edges.get(this.getNode(node1)) != null){
            if(node1 == node2) {
                if(hasEdge(node1,node1)){
                    this.edges.get(this.getNode(node1)).remove(node1);
                    mC++;
                    edgeSize--;
                }
            }
            if(this.hasEdge(node1,node2)) {
                this.edges.get(this.getNode(node1)).remove(getNode(node2));
                this.edges.get(this.getNode(node2)).remove(getNode(node1));
                mC++;
                edgeSize--;
            }
        }
    }

    /**
     * @return the number of nodes in the graph.
     */
    @Override
    public int nodeSize() {
        return this.nodeSize;
    }

    /**
     * @return the number of edges in the graph.
     */
    @Override
    public int edgeSize() {
        return this.edgeSize;
    }

    /**
     * @return the mode count of the graph.
     */
    @Override
    public int getMC() {
        return this.mC;
    }

    public String toString(){
        String v = vertices.toString();
        String e = edges.toString();
        return v + e;
    }

    /**
     * Equals between two graphs.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return nodeSize == wGraph_ds.nodeSize &&
                edgeSize == wGraph_ds.edgeSize&&
                Objects.equals(vertices, wGraph_ds.vertices) &&
                Objects.equals(edges, wGraph_ds.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices, edges, nodeSize, edgeSize);
    }

    private class NodeInfo implements node_info, Comparable<node_info> {
        private int key;
        private String info;
        private double tag;

        public NodeInfo(int key) {
            this.key = key;
            this.info = "";
            this.tag = 0;
        }

        public NodeInfo(node_info n ){
            this.key = n.getKey();
            this.info = n.getInfo();
            this.tag = n.getTag();
        }

        /**
         * @return the key of node.
         */
        @Override
        public int getKey() {
            return this.key;
        }

        /**
         * @return the info of nodes.
         */
        @Override
        public String getInfo() {
            return this.info;
        }

        /**
         * set info of node.
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        /**
         * @return the tag of node.
         */
        @Override
        public double getTag() {
            return this.tag;
        }

        /**
         * set a tag of node.
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        public String toString() {
            String s;
            return s = "" + key;
        }

        /**
         * Equals between two nodes.
         * @param o
         * @return
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeInfo nodeInfo = (NodeInfo) o;
            return key == nodeInfo.key &&
                    Objects.equals(info, nodeInfo.info);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, info);
        }

        @Override
        public int compareTo(node_info o) {
            if (o.getTag() > this.getTag()) return -1;
            return 1;
        }
    }
}