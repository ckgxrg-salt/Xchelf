package io.ckgxrg.xchelf.math;

import java.util.ArrayList;

/*
 * Use the back trace algorithm to find the largest
 * clique of the graph.
 */
public class MaxCliqueProblem {
	
	static Graph g;
	static int numNodes;
	static int nowLayer;
	
	// Nodes that has been traversed.
	static ArrayList<Integer> nodes;
	static ArrayList<Integer> solution;
	
	public static void solve(Graph g) {
		// First assume the root node R is connected to Node 0.
		nowLayer = 0;
		nodes = new ArrayList<Integer>();
		solution = new ArrayList<Integer>();
		trace(0);
		free();
	}
	
	@SuppressWarnings("unchecked")
	private static void trace(int node) {
		if(!allConnected(node)) {
			if(nodes.size() > solution.size()) solution = (ArrayList<Integer>) nodes.clone();
			nowLayer--;
			return;
		}
		nowLayer++;
		nodes.add(node);
		for(int i : g.adjacents(node)) {
			trace(i);
		}
	}
	
	private static boolean allConnected(int node) {
		for(int i : nodes) {
			if(!g.connected(node, i)) return false;
		}
		return true;
	}
	
	// Empties the leftovers.
	private static void free() {
		numNodes = nowLayer = 0;
		nodes = null;
		g = null;
	}
}
