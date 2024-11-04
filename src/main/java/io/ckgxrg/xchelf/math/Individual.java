package io.ckgxrg.xchelf.math;

import io.ckgxrg.xchelf.data.MasterCourse;
import io.ckgxrg.xchelf.data.NameRegistry;
import io.ckgxrg.xchelf.data.ShadowCourse;
import java.util.ArrayList;
import java.util.Random;

/** A wrapper to enclose data related to a complex course. To be used in the Genetic Algorithm. */
public class Individual {

  ArrayList<MasterCourse> masters;

  /**
   * Creates an Individual.
   *
   * @param masters The list of MasterCourses
   */
  public Individual(ArrayList<MasterCourse> masters) {
    this.masters = masters;
    random();
  }

  /** Forms the initial Individual, whose chromosome is totally up to entry sequence. */
  @Deprecated
  void sequence() {
    for (MasterCourse m : masters) {
      m.summonShadows();
      ArrayList<String> students = m.getStudents();
      int portion = students.size() / m.getShadowCount();
      for (int i = 0; i < m.getShadowCount(); i++) {
        if (i == m.getShadowCount() - 1) {
          for (int j = portion * i; j < students.size(); j++) {
            m.getShadows().get(i).addStudent(students.get(j));
          }
        } else {
          for (int j = portion * i; j < portion * (1 + i); j++) {
            m.getShadows().get(i).addStudent(students.get(j));
          }
        }
      }
    }
  }

  /** Forms the initial Individual, whose chromosome is totally random. */
  @SuppressWarnings("unchecked")
  void random() {
    for (MasterCourse m : masters) {
      m.summonShadows();
      ArrayList<String> students = (ArrayList<String>) m.getStudents().clone();
      int portion = students.size() / m.getShadowCount();
      Random r = new Random();
      for (int i = 0; i < m.getShadowCount(); i++) {
        if (i == m.getShadowCount() - 1) {
          for (int j = 0; j < students.size(); j++) {
            m.getShadows().get(i).addStudent(students.get(j));
          }
        } else {
          for (int j = 0; j < portion; j++) {
            int index = r.nextInt(students.size());
            m.getShadows().get(i).addStudent(students.get(index));
            students.remove(index);
          }
        }
      }
    }
  }

  /** Prints the internal structure. */
  public void test() {
    for (MasterCourse m : masters) {
      System.out.println("In MasterCourse " + NameRegistry.courseName(m));
      for (ShadowCourse s : m.getShadows()) {
        System.out.println("In ShadowCourse " + NameRegistry.courseName(s));
        for (String name : s.getStudents()) {
          System.out.print(name + " ");
        }
        System.out.println();
      }
    }
  }

  public void crossover() {}

  /*
   * Clips a range of the arrangement of shadows and interchange them.
   */
  public void interchange(Individual other) {}
}
