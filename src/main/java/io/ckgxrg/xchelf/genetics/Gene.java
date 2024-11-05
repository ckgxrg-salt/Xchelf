package io.ckgxrg.xchelf.genetics;

import io.ckgxrg.xchelf.data.Courses;
import io.ckgxrg.xchelf.data.MasterCourse;
import io.ckgxrg.xchelf.data.NameRegistry;
import java.util.HashMap;

/**
 * Express an Individual's chromosome using a String format, making it easier to carry out crossover
 * between Individuals.
 */
public class Gene {
  HashMap<MasterCourse, String> segments;

  /** Creates a Gene based on the current layout in the MasterCourses and ShadowCourses. */
  public Gene() {
    Individual.random();
    segments = new HashMap<MasterCourse, String>();
    for (MasterCourse m : Courses.getShadowMasters()) {
      StringBuilder sb = new StringBuilder();
      for (String stu : m.getStudents()) {
        sb.append(m.whichShadow(stu));
        sb.append(";");
      }
      sb.deleteCharAt(sb.length() - 1);
      segments.put(m, sb.toString());
    }
  }

  /** Debug use. */
  public void print() {
    System.out.println("Gene with " + segments.size() + " segments: ");
    for (MasterCourse m : segments.keySet()) {
      System.out.println("Segment for MasterCourse " + NameRegistry.courseName(m));
      for (String stu : m.getStudents()) {
        System.out.print(stu + " ");
      }
      System.out.println("\n" + segments.get(m));
    }
  }
}
