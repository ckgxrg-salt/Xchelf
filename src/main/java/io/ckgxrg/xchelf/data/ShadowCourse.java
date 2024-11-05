package io.ckgxrg.xchelf.data;

/** Course: Shadows Die Twice. */
public class ShadowCourse extends Course {

  MasterCourse master;

  /**
   * Creates a shadow.
   *
   * @param id The course id
   * @param master The MasterCourse, this instance's name will be inherited from it
   */
  public ShadowCourse(int id, MasterCourse master) {
    super(id, NameRegistry.courseName(master));
    this.master = master;
    this.name += (" " + id);
  }

  public void setMaster(MasterCourse master) {
    this.master = master;
  }

  public MasterCourse getMaster() {
    return master;
  }
}
