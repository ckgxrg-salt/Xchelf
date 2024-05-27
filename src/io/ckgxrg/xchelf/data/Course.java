package io.ckgxrg.xchelf.data;

import java.util.ArrayList;

/*
 * Represents a course to be chosen.
 */
public class Course {
	
	public int id;
	ArrayList<String> students;
	Group group;

	public Course(int id) {
		group = Group.UNKNOWN;
		this.id = id;
	}
	
	public void addName(String name) {
		students.add(name);
	}
}
