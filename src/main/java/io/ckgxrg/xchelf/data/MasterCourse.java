package io.ckgxrg.xchelf.data;

import java.util.ArrayList;

/*
 * A ShadowCourse is a Course that, depending on number of people chosen it, can have multiple classes.
 * The MasterCourse is a placeholder of the ShadowCourse, it will not be arranged into the schedule.
 * Each of the shadows reflect a "class" of the ShadowCourse.
 * Shadows have IDs that is like 1002, 405... while the master's is similar to ordinary courses.
 */
public class MasterCourse extends Course {

  ArrayList<ShadowCourse> shadows;
  int shadowInitialId;
  int shadowCount;

  public MasterCourse(int id, String courseName) {
    super(id, courseName);
    shadows = new ArrayList<ShadowCourse>();
    shadowInitialId = (id + 1) * 100;
  }

  public int getShadowCount() {
    return this.shadowCount;
  }

  public void setShadowCount(int count) {
    this.shadowCount = count;
  }

  public void summonShadows() {
    if (shadowCount <= 0) System.out.println("ShadowCourse: shadow count not initialised!");
    for (int i = 0; i < shadowCount; i++) {
      ShadowCourse shadow = new ShadowCourse(shadowInitialId + i, this);
      shadows.add(shadow);
    }
    this.name += " Master";
  }

  public ArrayList<ShadowCourse> getShadows() {
    return shadows;
  }
}
