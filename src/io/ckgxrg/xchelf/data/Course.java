package io.ckgxrg.xchelf.data;

import java.util.ArrayList;

/*
 * Represents a course to be chosen.
 */
public class Course {
	
	public int id;
	public String name;
	ArrayList<String> students;
	Group group;

	public Course(int id, String courseName) {
		name = courseName;
		group = Group.UNKNOWN;
		this.id = id;
		this.students = new ArrayList<String>();
	}
	
	public void addName(String name) {
		students.add(name);
	}
	
	public String getName() {
		return this.name;
	}
	
	/*
	 * If there is any student overlapping in the 2 courses,
	 * the 2 courses cannot be in the same group.
	 */
	public boolean conflict(Course other) {
		for(String s : other.students) {
			if(this.students.contains(s)) {
				return true;
			}
		}
		return false;
	}
}
