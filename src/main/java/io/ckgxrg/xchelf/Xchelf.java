package io.ckgxrg.xchelf;

import io.ckgxrg.xchelf.data.Course;
import io.ckgxrg.xchelf.data.Courses;
import io.ckgxrg.xchelf.data.Group;
import io.ckgxrg.xchelf.data.NameRegistry;
import io.ckgxrg.xchelf.handler.ConflictHandler;
import io.ckgxrg.xchelf.math.*;
import java.util.ArrayList;

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

    if (Courses.allAssigned()) {
      System.out.println("First attempt is exhausive, exiting.");
    } else {
      System.out.println("Conflicts found: ");
      for (Course c : Courses.unassigned()) {
        System.out.println(NameRegistry.courseName(c));
        ConflictHandler.why(c);
      }
    }
  }

  public static void handleComplex() {
    for (Course c : NameRegistry.allCourses) {
      // System.out.println("Not at: " + c.getName());
      for (String s : c.getStudents()) {
        NameRegistry.entry(s, c, c.getGroup());
      }
    }
    for (String s : NameRegistry.students) {
      System.out.println(s + "'s Schedule: ");
      System.out.println(NameRegistry.courseName(NameRegistry.courseOf(s, Group.A)));
      System.out.println(NameRegistry.courseName(NameRegistry.courseOf(s, Group.B)));
      System.out.println(NameRegistry.courseName(NameRegistry.courseOf(s, Group.C)));
    }
  }

  public static void main(String[] args) {
    Courses.Phys2.addStudent("山羊");
    Courses.Eco.addStudent("山羊");
    Courses.FM.addStudent("山羊");
    Courses.Chem.addStudent("咕噜");
    Courses.FM.addStudent("咕噜");
    Courses.Acc.addStudent("咕噜");
    Courses.Phys.addStudent("下雨");
    Courses.FM.addStudent("下雨");
    Courses.CS.addStudent("下雨");
    Courses.FM.addStudent("海燕");
    Courses.Psy.addStudent("海燕");
    Courses.Art.addStudent("海燕");
    Courses.FM.addStudent("老郑！");
    Courses.FM.addStudent("孙笑川");
    Courses.FM.addStudent("谁问你了");
    NameRegistry.allCourses.addAll(Courses.map.values());
    Individual i = new Individual(Courses.getShadowMasters());
    i.test();
  }
}
