package io.ckgxrg.xchelf.data;

import java.util.ArrayList;

public class ComplexCourse extends Course {
	
	ArrayList<Course> actuals;
	int actualID;
	int shadows;
	ComplexCourse master;
	
	public ComplexCourse(int id, String courseName) {
		super(id, courseName);
		actuals = new ArrayList<Course>();
		actualID = (id + 1) * 100;
	}
	
	ComplexCourse shadow(int number) {
		shadows = number;
		for(int i = 0; i < number; i++) {
			actuals.add(Courses.register(actualID + i, this.name + " " + (i + 1), -1));
			((ComplexCourse) actuals.get(i)).setMaster(this);
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
	
	public void setMaster(ComplexCourse master) {
		this.master = master;
	}
	public ComplexCourse getMaster() {
		return master;
	}
	
	@Override
	public boolean conflict(Course other) {
		if(other instanceof ComplexCourse && ((ComplexCourse) other).getMaster().equals(this.master)) {
			return false;
		}
		return super.conflict(other);
	}
}
