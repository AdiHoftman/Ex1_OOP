NodeInfo:

This class NodeInfo is inner class in WGraph_DS, and is a private class that represents the info of the vertices in the graph.

NodeInfo Objects:

int key - represents the id of the nodes.

String info - represents the information of the nodes

double tag - represents the temporal data of the nodes.

NodeInfo implements node_info interface and implements Comparable<node_info> interface.

WGraph_DS:

This class WGraph_DS represents a unidirectional weighted graph.

WGraph_DS Objects:

HashMap<Integer,node_info> vertices – represents a hash that get an id of a node.

HashMap<node_info,HashMap<node_info, Double> edges – represents a hash that get the neighbors of node and the weight of the edge between them.

int nodeSize – represents the number of nodes in the graph.

int edgeSize - represents the number of edges in the graph.

int mC – counts all the changes in the graph.

WGraph_DS implements weighted_graph interface.

Main Methods:

getNode(int key) - return the info of the node, the complexity is O(1).

hasEdge(int node1, int node2) - return true if they have a edge, if not’ its return false, the complexity is O(1).

getEdge(int node1, int node2) – return the weight of the edge between two nodes, the complexity is O(1)..

addNode(int key) – add a new node to the graph, the complexity is O(1).

connect(int node1, int node2, double w) – add a edge between two nodes and update the weight of the edge, the complexity is O(1).

getV() -  return all the nodes in the graph, the complexity is O(1).

getV(int node_id) – return the collection of the specific node in the graph, return it’s neighbors, the complexity is O(1).

removeNode(int key) – delete the specific node from the graph and delete all the edges that getting in or out from it, and return the node that deleted, the complexity is O(1).

removeEdge(int node1, int node2) – delete the edge between two nodes, the complexity is O(1).

nodeSize() – return the number of nodes in the graph, the complexity is O(1).

edgeSize() – return the number of edges in the graph, the complexity is O(1).

getMC() – return the number of changes in the graph.

WGraph_Algo:

WGraph_Algo represents some algorithms in graph.

WGraph_Algo Objects:

weighted_graph g0 – a graph that represents the WGraph_DS and can uses it’s function.

WGraph_Algo implements weighted_graph_algorithms interface.

Main Methods:

init(weighted_graph g) - initialize the graph. 

getGraph() – return the underlying graph in this class.

copy() - return a deep copy by create a new graph and save all the object’s source graph in the new graph.

isConnected() - checks if the graph is connected or not connected, so we will go through all the nodes in the graph and checks if from one node to second node has a path, return 
true if and only if the graph is connected, base on BFS algorithm.

shortestPathDist(int src, int dest) - calculate the shortest path distance from src to dest and return the shortest path, base on Dijkstra’s algorithm.

shortestPath(int src, int dest) - calculate the shortest path distance from src to dest and return the List of nodes in the graph that passed of them until it arrive to dest.

save(String file) – saves the weighted graph to the given file name, return true if the file is save.

load(String file) – loads a graph to this graph algorithm, if it successful, the function return true, else, return false.
