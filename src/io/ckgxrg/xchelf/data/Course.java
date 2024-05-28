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

	/*
	 * A course entry.
	 */
	public Course(int id, String courseName) {
		name = courseName;
		group = Group.UNKNOWN;
		this.id = id;
		this.students = new ArrayList<String>();
	}
	
	/*
	 * Enroll a student to the course.
	 */
	public void addStudent(String name) {
		students.add(name);
	}
	
	/*
	 * Return the list of students
	 */
	public ArrayList<String> students() {
		return students;
	}
	
	/*
	 * Returns the course name.
	 * DO NOT use, use NameRegistry.courseName() instead.
	 * @see NameRegistry.courseName()
	 */
	@Deprecated
	public String getName() {
		return this.name;
	}
	public Group getGroup() {
		return this.group;
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
