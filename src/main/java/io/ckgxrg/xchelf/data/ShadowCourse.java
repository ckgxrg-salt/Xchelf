package io.ckgxrg.xchelf.data;

public class ShadowCourse extends Course {

  MasterCourse master;

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
