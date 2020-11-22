package ex1.tests;
import ex1.src.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.*;

public class WGraph_AlgoTest_Adi {
    private static Random _rnd = null;

    @Test
    void copy(){
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,0.1);
        g.connect(1,2,2.1);
        g.connect(2,3,2.3);
        g.connect(0,2,4.5);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        weighted_graph go = ga.copy();
        Assertions.assertEquals(false, g.getNode(3) == go.getNode(3));
        Assertions.assertEquals(true, g.getEdge(0,1) == go.getEdge(0,1));
    }

    @Test
    void isConnected1(){
        weighted_graph g = graph_creator(100,400,1);
        g.removeEdge(0,1);
        g.removeEdge(2,20);
        g.removeEdge(14,8);
        g.removeNode(20);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        boolean b = ga.isConnected();
        Assertions.assertEquals(true, b);
    }

    @Test
    void isConnected2(){
        weighted_graph g = graph_creator(1000,5500,1);
        g.removeEdge(100,101);
        g.removeNode(100);
        g.removeEdge(900,999);
        g.removeNode(250);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        boolean b = ga.isConnected();
        Assertions.assertEquals(true, b);
    }
    @Test
    void isConnected3(){
        weighted_graph g = graph_creator(10, 10,1);
        g.removeEdge(1,9);
        g.removeNode(1);
        g.removeNode(5);
        g.removeNode(4);
        g.removeNode(8);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        boolean b = ga.isConnected();
        Assertions.assertEquals(false, b);
    }

    @Test
    void shortestPathDist1(){
        weighted_graph g = graph_creator(15,45,1);
        g.removeNode(12);
        g.removeEdge(4,14);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        double w = ga.shortestPathDist(14,10);
        Assertions.assertEquals(w, 0.26961664358834336);
    }

    @Test
    void shortestPathDist2(){
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(0,1, 1.2);
        g.connect(1,2,3.4);
        g.connect(2,0,7.0);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        double w = ga.shortestPathDist(0,2);
        Assertions.assertEquals(w, 4.6);
    }

    @Test
    void shortestPathDist3(){
        weighted_graph g = graph_creator(100,200,1);
        g.connect(0,90,1.5);
        g.connect(2,40,5.0);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        double w = ga.shortestPathDist(0,90);
        Assertions.assertEquals(w,1.3306806377064357);
    }

    @Test
    void shortestPath(){
        weighted_graph g = graph_creator(10,25,1);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        List<node_info> list = ga.shortestPath(0,9);
        int[] a = new int[list.size()];
        int i = 0;
        for(node_info n : list) {
            a[i] += n.getKey();
            Assertions.assertEquals(n.getKey(), a[i]);
            i++;
        }
    }

    @Test
    void save_load(){
        weighted_graph g = graph_creator(20,40,1);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        String s = "g.txt";
        ga.save(s);
        ga.load(s);
    }

    @Test
    void equals(){
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.connect(0,1,0.1);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        weighted_graph go = ga.copy();
        Assertions.assertEquals(g,go);
    }

    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
}
