package graph;

import graph.Graph.Edge;

import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class GraphAlgos {

	public static void bfs(Graph g, Integer startV) {
		if (!g.hasVertex(startV))
			throw new RuntimeException("Start Vertex" + startV + " not found!");

		Queue<Integer> q = new LinkedBlockingQueue<Integer>();
		q.add(startV);
		Set<Integer> marked = new HashSet<Integer>();
		marked.add(startV);

		while (!q.isEmpty()) {
			Integer p = q.poll();

			System.out.println(p);

			for (Integer neighbourV : g.getAdjacencyList().get(p))
				if (!marked.contains(neighbourV)) {
					q.add(neighbourV);
					marked.add(neighbourV);
				}

		}

	}

	public static void dfs(Graph g, Integer startV, HashSet<Integer> marked) {
		if (!g.hasVertex(startV))
			throw new RuntimeException("Start Vertex" + startV + " not found!");

		System.out.println(startV);
		marked.add(startV);

		for (Integer neighbourV : g.getAdjacencyList().get(startV))
			if (!marked.contains(neighbourV)) {
				dfs(g, neighbourV, marked);
			}
	}

	public static Graph kruskals_mst(final Graph g) {
		Set<Graph> forest = new HashSet<Graph>();

		for (Integer v : g.getAdjacencyList().keySet()) {
			Graph h = new Graph();
			h.addVertex(v);
			forest.add(h);
		}

		Collections.sort(g.getEdgesList());

		Graph minSpanningTree = new Graph(); // a tree is a graph

		for (Edge e : g.getEdgesList()) {

			Graph uTree, vTree;
			uTree = vTree = null;
			boolean uvInDifferentTrees = true;

			for (Graph tree : forest) {

				if (tree.hasVertex(e.u))
					uTree = tree;

				if (tree.hasVertex(e.v))
					vTree = tree;

				if (uTree != null && uTree == vTree) {
					uvInDifferentTrees = false;
					break;
				}
			}

			if (uvInDifferentTrees) {
				minSpanningTree.addEdge(e);

				// as we know u and v are in different trees, we need union

				if (uTree.V() >= vTree.V()) {
					uTree.addEdge(e);
					for (Edge vEdge : vTree.getEdgesList())
						uTree.addEdge(vEdge);
					forest.remove(vTree);
					forest.add(uTree);
				} else {
					vTree.addEdge(e);
					for (Edge uEdge : uTree.getEdgesList())
						vTree.addEdge(uEdge);
					forest.remove(uTree);
					forest.add(vTree);
				}

			}
		}

		return minSpanningTree;
	}

	public static Graph prims_mst(final Graph g, Integer startV) {

		Graph minSpanningTree = new Graph();
		minSpanningTree.addVertex(startV);

		Collections.sort(g.getEdgesList());

		HashSet<Integer> visitedVertices = new HashSet<Integer>();
		while (visitedVertices.size() < g.V()) {
			for (Edge e : g.getEdgesList())
				if (minSpanningTree.hasVertex(e.u)
						&& !minSpanningTree.hasVertex(e.v)
						|| minSpanningTree.hasVertex(e.v)
						&& !minSpanningTree.hasVertex(e.u)) {
					minSpanningTree.addEdge(e);
					visitedVertices.add(e.u);
					visitedVertices.add(e.v);
				}

		}

		return minSpanningTree;
	}
}
