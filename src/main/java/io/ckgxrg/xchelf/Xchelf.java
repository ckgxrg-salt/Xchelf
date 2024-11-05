package io.ckgxrg.xchelf;

import io.ckgxrg.xchelf.data.Course;
import io.ckgxrg.xchelf.data.Courses;
import io.ckgxrg.xchelf.data.Group;
import io.ckgxrg.xchelf.data.NameRegistry;
import io.ckgxrg.xchelf.genetics.ShadowEvolution;

/*
 * Main class of the Xchelf schedule arrangement system.
 * @author ckgxrg
 * @version 0.1
 */
public class Xchelf {

  public static void handleComplex() {
    for (Course c : Courses.getShadowMasters()) {
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

  /**
   * The main entry.
   *
   * @param args Arguments passed from the command-line.
   */
  public static void main(String[] args) {
    Interpreter.parseFile("testinput");
    ShadowEvolution.populateStage();
    ShadowEvolution.stableStage();
  }
}
