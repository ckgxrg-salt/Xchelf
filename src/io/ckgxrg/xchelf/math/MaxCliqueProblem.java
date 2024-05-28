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
			//System.out.println("Root node: " + i);
			nodes = new ArrayList<Integer>();
			nowLayer = 0;
			trace(i);
		}
		return solution;
	}
	
	/*
	 * Similar as Graph.adjacents, but eliminated possibility of infinite recursion.
	 */
	private static ArrayList<Integer> enhancedAdjacents(int node){
		ArrayList<Integer> arr = g.adjacents(node);
		arr.removeAll(nodes);
		return arr;
	}
	
	// The main recursion method.
	@SuppressWarnings("unchecked")
	private static void trace(int node) {
		//System.out.println("At Node: " + node);
		if(!allConnected(node)) {
			//System.out.println("Node: " + node + " not allConnected");
			if(nodes.size() > solution.size()) {
				solution = (ArrayList<Integer>) nodes.clone();
				//System.out.println("Solution: " + nodes);
			}
			return;
		}
		nowLayer++;
		nodes.add(node);
		if(enhancedAdjacents(node).isEmpty()) {
			//System.out.println("Node: " + node + " reached the end");
			if(nodes.size() > solution.size()) {
				solution = (ArrayList<Integer>) nodes.clone();
				//System.out.println("Solution: " + nodes);
			}
			return;
		}
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
