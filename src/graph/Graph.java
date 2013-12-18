package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Graph {

	class Edge implements Comparable<Edge> {
		public int u, v, wt;

		Edge(int u, int v, int wt) {
			this.u = u;
			this.v = v;
			this.wt = wt;
		}

		@Override
		public int compareTo(Edge o) {
			Edge e1 = (Edge) o;
			if (e1.wt == this.wt)
				return 0;
			return e1.wt < this.wt ? 1 : -1;
		}

		@Override
		public String toString() {
			return String.format("Vertex1:%d \t Vertex2:%d \t Cost:%d\n", u, v,
					wt);

		}
	}

	// symbol table: key = string vertex, value = set of neighboring vertices
	private HashMap<Integer, HashSet<Integer>> adjacencyList;

	// edges
	private ArrayList<Edge> edges;

	/**
	 * Create an empty graph with no vertices or edges.
	 */
	public Graph() {
		adjacencyList = new HashMap<Integer, HashSet<Integer>>();
		edges = new ArrayList<Edge>();
	}

	/**
	 * Create an graph from given input stream using given delimiter.
	 */
	public Graph(Scanner in, String delimiter) {
		adjacencyList = new HashMap<Integer, HashSet<Integer>>();
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] names = line.split(delimiter);
			for (int i = 1; i < names.length; i += 2) {
				addEdge(Integer.parseInt(names[0]), Integer.parseInt(names[i]),
						Integer.parseInt(names[i + 1]));
			}
		}
	}

	/**
	 * Number of vertices.
	 */
	public int V() {
		return adjacencyList.size();
	}

	/**
	 * Number of edges.
	 */
	public int E() {
		return edges.size();
	}

	/**
	 * get adjacency list.
	 */
	public HashMap<Integer, HashSet<Integer>> getAdjacencyList() {
		return adjacencyList;
	}

	/**
	 * get edges list.
	 */
	public ArrayList<Edge> getEdgesList() {
		return edges;
	}

	/**
	 * Degree of this vertex.
	 */
	public int degree(Integer v) {
		if (!adjacencyList.containsKey(v))
			throw new RuntimeException(v + " is not a vertex");
		else
			return adjacencyList.get(v).size();
	}

	/**
	 * Add edge v-w to this graph (if it is not already an edge)
	 */
	public void addEdge(Integer u, Integer v, Integer wt) {
		if (!hasEdge(u, v))
			edges.add(new Edge(u, v, wt));
		if (!hasVertex(u))
			addVertex(u);
		if (!hasVertex(v))
			addVertex(v);
		adjacencyList.get(u).add(v);
		adjacencyList.get(v).add(u);
	}

	public void addEdge(Edge e) {
		if (!hasEdge(e.u, e.v))
			edges.add(new Edge(e.u, e.v, e.wt));
		if (!hasVertex(e.u))
			addVertex(e.u);
		if (!hasVertex(e.v))
			addVertex(e.v);
		adjacencyList.get(e.u).add(e.v);
		adjacencyList.get(e.v).add(e.u);
	}

	/**
	 * Add vertex v to this graph (if it is not already a vertex)
	 */
	public void addVertex(Integer v) {
		if (!hasVertex(v))
			adjacencyList.put(v, new HashSet<Integer>());
	}

	/**
	 * Return the set of vertices as an Iterable.
	 */
	public Iterable<Integer> vertices() {
		return adjacencyList.keySet();
	}

	/**
	 * Return the set of neighbors of vertex v as in Iterable.
	 */
	public Iterable<Integer> adjacentTo(Integer v) {
		// return empty set if vertex isn't in graph
		if (!hasVertex(v))
			return new HashSet<Integer>();
		else
			return adjacencyList.get(v);
	}

	/**
	 * Is v a vertex in this graph?
	 */
	public boolean hasVertex(Integer v) {
		return adjacencyList.containsKey(v);
	}

	/**
	 * Is v-w an edge in this graph?
	 */
	public boolean hasEdge(Integer v, Integer w) {
		if (!hasVertex(v))
			return false;
		return adjacencyList.get(v).contains(w);
	}

	/**
	 * Return a string representation of the graph.
	 */
	public String toString() {
		String s = "";
		for (Integer v : adjacencyList.keySet()) {
			s += v + ": ";
			for (Integer w : adjacencyList.get(v)) {
				s += w + " ";
			}
			s += "\n";
		}
		return s;
	}

	public static void main(String[] args) {
		Graph G = new Graph();
		G.addEdge(1, 2, 1);
		G.addEdge(1, 3, 1);
		G.addEdge(1, 7, 1);
		G.addEdge(1, 8, 1);
		G.addEdge(3, 7, 1);
		G.addEdge(3, 4, 1);
		G.addEdge(7, 9, 2); // this edge is costly, wont be included in min
							// spanning tree
		G.addEdge(4, 9, 1);

		// print out graph
		System.out.println(G);
		
		System.out.println("-------bfs--------");
		GraphAlgos.bfs(G, 1);
		System.out.println("-------dfs--------");
		GraphAlgos.dfs(G, 1, new HashSet<Integer>());
		System.out.println("-------kruskal--------");
		Graph kruskal_mst = GraphAlgos.kruskals_mst(G);
		System.out.println(kruskal_mst);
		System.out.println("-------prims--------");
		Graph prims_mst = GraphAlgos.prims_mst(G, 1);
		System.out.println(prims_mst);
		System.out.println("---------------");
	}

}
