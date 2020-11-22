package ex1.tests;
import ex1.src.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.*;


public class WGraph_DSTest_Adi {
     private static Random rnd = null;
     private static int errors = 0, tests = 0, numberException = 0;
     private static String log = "";

     @Test
     public void nodeSize() {
         weighted_graph g = new WGraph_DS();
         g.addNode(0);
         g.addNode(1);
         g.addNode(2);
         g.addNode(3);
         g.removeNode(0);
         g.removeNode(3);
         int v = g.nodeSize();
         Assertions.assertEquals(2, v);
     }

     @Test
     void edgeSize() {
         weighted_graph g = new WGraph_DS();
         g.addNode(0);
         g.addNode(1);
         g.addNode(2);
         g.addNode(3);
         g.connect(0, 1, 1.2);
         g.connect(2, 3, 4.5);
         g.connect(1, 3, 2.3);
         g.connect(0, 3, 0.1);
         g.removeEdge(0, 1);
         g.removeEdge(2, 3);
         g.removeEdge(1, 2);
         int e = g.edgeSize();
         Assertions.assertEquals(2, e);
     }

     @Test
     void getV() {
         weighted_graph g = new WGraph_DS();
         g.addNode(0);
         g.addNode(1);
         g.addNode(2);
         g.addNode(3);
         g.connect(0, 2, 2.4);
         g.connect(0, 3, 1.53);
         g.connect(1, 3, 3.4);
         g.connect(2, 1, 5.0);
         for (node_info n : g.getV(0)) {
             Assertions.assertNotNull(n.getKey());
             Assertions.assertEquals(g.getV(0).size(),2 );
         }

     }

     @Test
     void hasEdge() {
         weighted_graph g = new WGraph_DS();
         g.addNode(0);
         g.addNode(1);
         g.addNode(2);
         g.addNode(3);
         g.connect(0, 1, 3.4);
         g.connect(1, 2, 1.2);
         g.connect(2, 3, 1.78);
         g.connect(0, 3, 3.0);
         boolean b = g.hasEdge(0, 1);
         boolean f = g.hasEdge(0, 2);
         Assertions.assertTrue(b);
         Assertions.assertFalse(f);
     }

     @Test
     void connect() {
         weighted_graph g = new WGraph_DS();
         g.addNode(0);
         g.addNode(1);
         g.addNode(2);
         g.addNode(3);
         g.connect(0, 2, 1.2);
         g.connect(0, 1, 2.3);
         g.connect(1, 3, 3.1);
         g.connect(3, 2, 0.3);
         g.removeEdge(0, 1);
         Assertions.assertFalse(g.hasEdge(0, 1));
         g.removeEdge(2, 1);
         g.removeEdge(0, 2);
         g.connect(0, 3, 1.56);
         double w = g.getEdge(1, 3);
         Assertions.assertEquals(w, 3.1);
     }

     @Test
     void removeNode() {
         weighted_graph g = new WGraph_DS();
         g.addNode(0);
         g.addNode(1);
         g.addNode(2);
         g.addNode(3);
         g.connect(0, 2, 1.2);
         g.connect(0, 1, 2.1);
         g.connect(1, 3, 3.3);
         g.connect(0, 3, 3.4);
         g.removeNode(4);
         g.removeNode(0);
         Assertions.assertFalse(g.hasEdge(2, 0));
         int v = g.nodeSize();
         Assertions.assertEquals(3, v);
     }

     @Test
     void removeEdge() {
         weighted_graph g = new WGraph_DS();
         g.addNode(0);
         g.addNode(1);
         g.addNode(2);
         g.addNode(3);
         g.connect(0, 1, 1.2);
         g.connect(0, 2, 2.4);
         g.connect(2, 3, 3.2);
         g.connect(2, 1, 4.3);
         g.removeEdge(0, 3);
         g.removeEdge(1, 2);
         double w = g.getEdge(0, 3);
         Assertions.assertEquals(w, -1.0);
         double x = g.getEdge(2, 1);
         Assertions.assertEquals(x, -1.0);
     }
    @Test
     void getEdge(){
         weighted_graph g = new WGraph_DS();
         g.addNode(0);
         g.addNode(1);
         g.addNode(2);
         g.addNode(3);
         g.connect(0,1,2.3);
         g.connect(1,2,3.4);
         g.connect(2,3,0.1);
         double w = g.getEdge(0,1);
         Assertions.assertEquals(w,2.3);
         double x = g.getEdge(1,3);
         Assertions.assertEquals(x,-1.0);
     }
    @Test
     void getMC(){
         weighted_graph g = new WGraph_DS();
         g.addNode(0);
         g.addNode(1);
         g.addNode(2);
         g.addNode(3);
         g.connect(0,1,1.2);
         g.connect(1,2,4.5);
         g.removeEdge(1,2);
         g.removeEdge(0,1);
         g.removeNode(1);
         double w = g.getMC();
         Assertions.assertEquals(w,9.0);
     }



 }