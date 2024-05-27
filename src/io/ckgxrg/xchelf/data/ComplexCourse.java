package io.ckgxrg.xchelf.data;

import java.util.ArrayList;

public class ComplexCourse extends Course {
	
	ArrayList<Course> actuals;
	int actualID;
	
	public ComplexCourse(int id) {
		super(id);
		actuals = new ArrayList<Course>();
		actualID = id * 1000;
	}
}
