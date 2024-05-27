package io.ckgxrg.xchelf.math;

import java.util.ArrayList;

/*
 * Use the back trace algorithm to find the largest
 * clique of the graph.
 */
public class MaxCliqueProblem {
	
	static Graph g;
	static int nowLayer;
	
	// Nodes that has been traversed.
	static ArrayList<Integer> nodes;
	static ArrayList<Integer> solution;
	
	public static ArrayList<Integer> solve(Graph graph) {
		// First assume the root node R is connected to Node 0.
		g = graph;
		solution = new ArrayList<Integer>();
		for(int i = 0; i < g.size; i++) {
			nodes = new ArrayList<Integer>();
			nowLayer = 0;
			System.out.println("Major Node: " + i);
			trace(i);
		}
		return solution;
	}
	
	// The main recursion method.
	@SuppressWarnings("unchecked")
	private static void trace(int node) {
		System.out.println("Starting at Layer: " + nowLayer);
		System.out.println("Tracing Node: " + node);
		if(!allConnected(node)) {
			System.out.println("Node: " + node + " is not allConnected, Reverting");
			if(nodes.size() > solution.size()) {
				solution = (ArrayList<Integer>) nodes.clone();
				System.out.println("Found a solution at: " + node);
			}
			return;
		}
		nowLayer++;
		nodes.add(node);
		for(int i : g.adjacents(node)) {
			if(nodes.contains(i)) continue;
			trace(i);
		}
	}
	
	// Verifies if a node is connected to all previous nodes.
	private static boolean allConnected(int node) {
		for(int i : nodes) {
			if(!g.connected(node, i)) return false;
		}
		return true;
	}
}
