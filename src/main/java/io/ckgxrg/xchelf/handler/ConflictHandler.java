package io.ckgxrg.xchelf.handler;

import io.ckgxrg.xchelf.data.Course;
import io.ckgxrg.xchelf.data.NameRegistry;
import java.util.ArrayList;

public class ConflictHandler {

  public static void why(Course c) {
    for (Course d : NameRegistry.allCourses) {
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

  public static ArrayList<String> findOverlap(ArrayList<String> a, ArrayList<String> b) {
    ArrayList<String> res = new ArrayList<String>();
    for (String s : a) {
      for (String ss : b) {
        if (s.equals(ss)) res.add(s);
      }
    }
    return res;
  }
}
