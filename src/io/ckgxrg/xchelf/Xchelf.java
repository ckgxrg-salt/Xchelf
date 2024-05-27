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
		g.connect(6, 8);
		g.connect(8, 1);
		g.connect(8, 13);
		g.connect(13, 6);
		g.connect(13, 1);
		g.connect(1, 9);
		g.connect(13, 9);
		System.out.println(MaxCliqueProblem.solve(g));
	}
}
