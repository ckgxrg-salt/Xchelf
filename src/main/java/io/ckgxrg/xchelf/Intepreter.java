package io.ckgxrg.xchelf;

import io.ckgxrg.xchelf.data.Courses;
import io.ckgxrg.xchelf.data.NameRegistry;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/** Parsing data from plain text for testing. */
public class Intepreter {
  /**
   * Read content of a plain text file and add students to courses. format: Name ID ID ID
   *
   * @param path Path to the text file
   */
  public static void parseFile(String path) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(path));
      String str;
      while ((str = br.readLine()) != null) {
        String[] all = str.split(" ");
        String name = all[0];
        for (int i = 1; i < all.length; i++) {
          int currentId = Integer.valueOf(all[i]);
          Courses.addStudentById(currentId, name);
          System.out.println(name + " selected course " + NameRegistry.courseName(currentId));
        }
      }
      br.close();
      Courses.sortAll();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
