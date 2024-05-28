package io.ckgxrg.xchelf.data;

import java.util.ArrayList;

public class ComplexCourse extends Course {
	
	ArrayList<Course> actuals;
	int actualID;
	
	public ComplexCourse(int id, String courseName) {
		super(id, courseName);
		actuals = new ArrayList<Course>();
		actualID = (id + 1) * 100;
	}
}
