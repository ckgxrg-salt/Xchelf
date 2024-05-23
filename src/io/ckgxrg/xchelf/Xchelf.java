package io.ckgxrg.xchelf;

import io.ckgxrg.xchelf.math.*;

/*
 * Main class of the Xchelf schedule arrangement system.
 * @author ckgxrg
 * @version 0.1
 */
public class Xchelf {
	public static void main(String[] args) {
		Graph g = new Graph(20);
		g.connect(1, 6);
		g.split(2, 7);
		System.out.println(g.toString());
	}
}
