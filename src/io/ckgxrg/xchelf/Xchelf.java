package io.ckgxrg.xchelf;

import java.util.ArrayList;

import io.ckgxrg.xchelf.data.Courses;
import io.ckgxrg.xchelf.math.*;

/*
 * Main class of the Xchelf schedule arrangement system.
 * @author ckgxrg
 * @version 0.1
 */
public class Xchelf {
	public static void main(String[] args) {
		Courses.Acc.addName("A");
		Courses.Art.addName("A");
		Courses.Chem2.addName("A");
		Courses.Chem2.addName("B");
		Courses.CS.addName("B");
		Courses.FM.addName("B");
		Graph g = Courses.generateGraph();
		ArrayList<Integer> A = MaxCliqueProblem.solve(g);
		System.out.println(Courses.prettyPrint(A));
		g.splitAll(A);
		ArrayList<Integer> B = MaxCliqueProblem.solve(g);
		System.out.println(Courses.prettyPrint(B));
		g.splitAll(B);
		ArrayList<Integer> C = MaxCliqueProblem.solve(g);
		System.out.println(Courses.prettyPrint(C));
	}
}
