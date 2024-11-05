package io.ckgxrg.xchelf.data;

import java.util.ArrayList;

/**
 * A ShadowCourse is a Course that, depending on number of people chose it, can have multiple
 * classes. The MasterCourse is a placeholder of the ShadowCourse, it will not be arranged into the
 * schedule. Each of the shadows reflect a "class" of the ShadowCourse. Shadows have IDs that is
 * like 1002, 405... while the master's is similar to ordinary courses.
 */
public class MasterCourse extends Course {

  ArrayList<ShadowCourse> shadows;
  int shadowInitialId;
  int shadowCount;

  /**
   * Creates a MasterCourse.
   *
   * @param id The course id
   * @param courseName The course name(without Master suffix)
   */
  public MasterCourse(int id, String courseName) {
    super(id, courseName);
    shadows = new ArrayList<ShadowCourse>();
    shadowInitialId = (id + 1) * 100;
  }

  public int getShadowCount() {
    return this.shadowCount;
  }

  /**
   * Initialises all shadows, invoke this prior to any operation to the shadows.
   *
   * @param shadowCount How many shadows this should have
   */
  public void summonShadows(int shadowCount) {
    this.shadowCount = shadowCount;
    for (int i = 0; i < shadowCount; i++) {
      ShadowCourse shadow = new ShadowCourse(shadowInitialId + i, this);
      shadows.add(shadow);
    }
  }

  public ArrayList<ShadowCourse> getShadows() {
    return shadows;
  }

  /**
   * Identifies which shadow the student is now enrolled in.
   *
   * @param student The student name
   * @return The shadow instance
   */
  public ShadowCourse whichShadow(String student) {
    for (ShadowCourse s : this.shadows) {
      if (s.getStudents().contains(student)) {
        return s;
      }
    }
    System.err.println("Not found!");
    return null;
  }
}
