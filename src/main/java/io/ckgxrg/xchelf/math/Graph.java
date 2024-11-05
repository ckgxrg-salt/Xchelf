package io.ckgxrg.xchelf.math;

import java.util.ArrayList;

/**
 * Minimal implementation of a graph in math context. Where the nodes are arranged as 0, 1, 2, ...
 * and edges without direction.
 */
public class Graph {

  Matrixnb mat;
  final int size;
  int edges;

  /**
   * Creates a graph of n nodes.
   *
   * @param n Number of nodes in the graph
   */
  public Graph(int n) {
    mat = new Matrixnb(n);
    size = n;
    edges = 0;
  }

  /**
   * Gets the length of the graph.
   *
   * @return The length
   */
  public int length() {
    return size;
  }

  /**
   * Connect 2 nodes, node1 and node2.
   *
   * @param node1 The first node to be connected
   * @param node2 The second
   */
  public void connect(int node1, int node2) {
    mat.superimpose();
    if (!mat.get(node1, node2)) {
      mat.set(node1, node2, true);
      mat.set(node2, node1, true);
      edges++;
    }
  }

  /**
   * Break 2 nodes, node1 and node2.
   *
   * @param node1 The first node to be broken
   * @param node2 The second
   */
  public void split(int node1, int node2) {
    mat.superimpose();
    if (mat.get(node1, node2)) {
      mat.set(node1, node2, false);
      mat.set(node2, node1, false);
      edges--;
    }
  }

  /**
   * Breaks all connections indicated by the list.
   *
   * @param target List of nodes to sever
   */
  public void splitAll(ArrayList<Integer> target) {
    for (int i : target) {
      mat.rmRow(i);
      mat.rmColumn(i);
    }
  }

  /** Superimposes the matrix wrapped. */
  public void superimpose() {
    mat.superimpose();
  }

  /**
   * Checks if the 2 nodes are connected.
   *
   * @param node1 The first node
   * @param node2 ...
   */
  public boolean connected(int node1, int node2) {
    mat.superimpose();
    return mat.get(node1, node2) || node1 == node2;
  }

  /**
   * Finds all connected nodes for a given node.
   *
   * @param node The node
   * @return An array list of all adjacent nodes
   */
  public ArrayList<Integer> adjacents(int node) {
    ArrayList<Integer> res = new ArrayList<Integer>();
    for (int i = 0; i < size; i++) {
      if (mat.get(node, i)) {
        res.add(i);
      }
    }
    return res;
  }

  @Override
  public String toString() {
    return mat.toString();
  }
}
