package io.ckgxrg.xchelf;

import io.ckgxrg.xchelf.data.Courses;
import io.ckgxrg.xchelf.genetics.ShadowEvolution;

/*
 * Main class of the Xchelf schedule arrangement system.
 * @author ckgxrg
 * @version 0.1
 */
public class Xchelf {
  /**
   * The main entry.
   *
   * @param args Arguments passed from the command-line.
   */
  public static void main(String[] args) {
    Interpreter.parseFile("testinput");
    ShadowEvolution.populateStage();
    ShadowEvolution.iterate();
    ShadowEvolution.generateReport();
    System.out.println(Courses.Psy.getGroup());
  }
}
