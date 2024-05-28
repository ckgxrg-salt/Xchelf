package io.ckgxrg.xchelf;

import java.util.ArrayList;

import io.ckgxrg.xchelf.data.Courses;
import io.ckgxrg.xchelf.data.Group;
import io.ckgxrg.xchelf.math.*;

/*
 * Main class of the Xchelf schedule arrangement system.
 * @author ckgxrg
 * @version 0.1
 */
public class Xchelf {
	
	public static void firstAttempt() {
		Graph g = Courses.generateGraph();
		ArrayList<Integer> A = Courses.translate(MaxCliqueProblem.solve(g));
		System.out.println("Group A: " + Courses.prettyPrint(A));
		Courses.assign(Group.A, A);
		g = Courses.generateGraph();
		
		ArrayList<Integer> B = Courses.translate(MaxCliqueProblem.solve(g));
		System.out.println("Group B: " + Courses.prettyPrint(B));
		Courses.assign(Group.B, B);
		g = Courses.generateGraph();
		
		ArrayList<Integer> C = Courses.translate(MaxCliqueProblem.solve(g));
		System.out.println("Group C: " + Courses.prettyPrint(C));
		Courses.assign(Group.C, C);
		
		if(Courses.allAssigned()) {
			System.out.println("First attempt is exhausive, exiting.");
		} else {
			System.out.println("Conflicts found.");
		}
	}
	
	public static void main(String[] args) {
		Courses.FM.addName("A");
		Courses.Art.addName("A");
		Courses.Chem2.addName("A");
		Courses.Chem2.addName("B");
		Courses.CS.addName("B");
		Courses.Acc.addName("B");
		Courses.Phys2.addName("C");
		Courses.Acc.addName("C");
		Courses.Eco2.addName("C");
		firstAttempt();
	}
}
