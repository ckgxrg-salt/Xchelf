package io.ckgxrg.xchelf.handler;

import io.ckgxrg.xchelf.data.Course;
import io.ckgxrg.xchelf.data.Courses;
import java.util.ArrayList;

/** Some utilities about conflicts. */
public class ConflictHandler {

  /**
   * Explains why a course cannot be assigned.
   *
   * @param c The course to be queried
   */
  public static void why(Course c) {
    for (Course d : Courses.getShadowMasters()) {
      ArrayList<String> arr;
      if (!(arr = findOverlap(c.getStudents(), d.getStudents())).isEmpty()) {
        System.out.print("With course " + d.name + "(Group " + d.getGroup() + ")");
        System.out.println(", the following students are overlapping:");
        for (String s : arr) {
          System.out.print(s + " ");
        }
        System.out.println();
      }
    }
  }

  /**
   * Finds out what elements are repeated in two lists.
   *
   * @param a One list
   * @param b Another list
   * @return Their intersection
   */
  public static ArrayList<String> findOverlap(ArrayList<String> a, ArrayList<String> b) {
    ArrayList<String> res = new ArrayList<String>();
    for (String s : a) {
      for (String ss : b) {
        if (s.equals(ss)) {
          res.add(s);
        }
      }
    }
    return res;
  }
}
