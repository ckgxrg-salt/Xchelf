package io.ckgxrg.xchelf;

import io.ckgxrg.xchelf.genetics.ShadowEvolution;
import java.util.Scanner;

/**
 * Main class of the Xchelf schedule arrangement system.
 *
 * @author ckgxrg
 * @version 1.0.0
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
    Scanner sc = new Scanner(System.in);
    String command = "";
    int remaining = 0;
    do {
      ShadowEvolution.iterate();
      System.out.println("Next Generation? (Y/n)");
      if (remaining > 0) {
        remaining--;
        System.out.println("Auto-loop: " + remaining + " left");
        continue;
      }
      if (sc.hasNextInt()) {
        remaining = sc.nextInt();
      } else if (sc.hasNext()) {
        command = sc.next();
      }
    } while (!command.equals("n"));
    sc.close();
    ShadowEvolution.generateReport();
  }
}
