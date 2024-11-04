package io.ckgxrg.xchelf.data;

public class ShadowCourse extends Course {

  MasterCourse master;

  public ShadowCourse(int id, MasterCourse master) {
    super(id, NameRegistry.courseName(master));
    this.master = master;
  }

  public void setMaster(MasterCourse master) {
    this.master = master;
  }

  public MasterCourse getMaster() {
    return master;
  }

  @Override
  public boolean conflict(Course other) {
    if (other instanceof ShadowCourse && ((ShadowCourse) other).getMaster().equals(this.master)) {
      return false;
    }
    return super.conflict(other);
  }
}
