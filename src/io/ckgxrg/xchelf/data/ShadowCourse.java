package io.ckgxrg.xchelf.data;

import java.util.ArrayList;

/*
 * A ShadowCourse is a Course that, depending on number of people chosen it, can have multiple classes.
 * The master is a placeholder of the ShadowCourse, it will not be arranged into the schedule.
 * Each of the shadows reflect a "class" of the ShadowCourse.
 * Shadows have IDs that is like 1002, 405... while the master's is similar to ordinary courses.
 */
public class ShadowCourse extends Course {
	
	ArrayList<ShadowCourse> actuals;
	int actualID;
	int shadows;
	ShadowCourse master;
	
	public ShadowCourse(int id, String courseName) {
		super(id, courseName);
		actuals = new ArrayList<ShadowCourse>();
		actualID = (id + 1) * 100;
	}
	
	ShadowCourse shadow(int number) {
		shadows = number;
		for(int i = 0; i < number; i++) {
			actuals.add((ShadowCourse) Courses.register(actualID + i, this.name + " " + (i + 1), -1));
			actuals.get(i).setMaster(this);
		}
		Courses.leave(id);
		this.name += " Master (Hidden)";
		return this;
	}
	
	@Override
	public void addStudent(String name) {
		super.addStudent(name);
		for(Course c : actuals) {
			c.addStudent(name);
		}
	}
	
	public void setMaster(ShadowCourse master) {
		this.master = master;
	}
	public ShadowCourse getMaster() {
		return master;
	}
	public ArrayList<ShadowCourse> getShadows(){
		return actuals;
	}
	
	@Override
	public boolean conflict(Course other) {
		if(other instanceof ShadowCourse && ((ShadowCourse) other).getMaster().equals(this.master)) {
			return false;
		}
		return super.conflict(other);
	}
}
