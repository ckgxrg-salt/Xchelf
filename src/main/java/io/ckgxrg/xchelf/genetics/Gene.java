package io.ckgxrg.xchelf.genetics;

import io.ckgxrg.xchelf.data.Courses;
import io.ckgxrg.xchelf.data.MasterCourse;
import io.ckgxrg.xchelf.data.NameRegistry;
import io.ckgxrg.xchelf.data.ShadowCourse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Express an Individual's chromosome using a String format, making it easier to carry out crossover
 * between Individuals.
 */
public class Gene implements Comparable<Gene> {
  HashMap<MasterCourse, String> segments;
  int penalty;

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
    this.penalty = Integer.MAX_VALUE;
  }

  private Gene(HashMap<MasterCourse, String> segments) {
    this.segments = segments;
    this.penalty = Integer.MAX_VALUE;
  }

  /**
   * Produces a child based on both parents' segments.
   *
   * @param other The other parent
   * @return The child
   */
  public Gene mate(Gene other) {
    HashMap<MasterCourse, String> childSegments = new HashMap<MasterCourse, String>();
    double currentMutateProbablity =
        this.penalty == other.penalty
            ? ShadowEvolution.MUTATE_PROBABLITY * 200
            : ShadowEvolution.MUTATE_PROBABLITY;
    for (MasterCourse m : Courses.getShadowMasters()) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < m.getStudents().size(); i++) {
        if (Math.random() < currentMutateProbablity) {
          // System.out.print("A gene mutated! ");
          Random r = new Random();
          ArrayList<ShadowCourse> options = m.getShadows();
          sb.append(options.get(r.nextInt(options.size())).getId());
          sb.append(";");
          // System.out.println("It looks like this currently: " + sb.toString());
          continue;
        }
        if (Math.random() < 0.5) {
          sb.append(this.segments.get(m).split(";")[i]);
          sb.append(";");
        } else {
          sb.append(other.segments.get(m).split(";")[i]);
          sb.append(";");
        }
      }
      sb.deleteCharAt(sb.length() - 1);
      childSegments.put(m, sb.toString());
    }
    return new Gene(childSegments);
  }

  @Override
  public int compareTo(Gene other) {
    if (this.penalty == other.penalty) {
      return 0;
    } else if (this.penalty < other.penalty) {
      return -1;
    } else {
      return 1;
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
