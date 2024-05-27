package io.ckgxrg.xchelf.data;

import java.util.HashMap;

/*
 * Manage all courses.
 */
public class Courses {

	/*
	 * List of all available courses.
	 * The Names and IDs are listed below.
	 * Complex AS Physics: 0
	 * A2 Physics: 1
	 * AS Chemistry: 2
	 * A2 Chemistry: 3
	 * AS Economics: 4
	 * Complex A2 Economics: 5
	 * AS Biology: 6
	 * AS Computer Science: 7
	 * AS Psychology: 8
	 * AS Accounting: 9
	 * AS Art and Design: 10
	 * Complex AS Further Mathematics: 11
	 * 
	 * The Complex courses do not represent the actual course.
	 * @see io.ckgxrg.xchelf.data.ComplexCourse.getActual();
	 */
	public static Course Phys = register(0);
	public static Course Phys2 = register(1);
	public static Course Chem = register(2);
	public static Course Chem2 = register(3);
	public static Course Eco = register(4);
	public static Course Eco2 = register(5);
	public static Course Bio = register(6);
	public static Course CS = register(7);
	public static Course Psy = register(8);
	public static Course Acc = register(9);
	public static Course Art = register(10);
	public static Course FM = register(11);
	
	public static HashMap<Integer, Course> map;

	public static Course register(int id) {
		Course c = new Course(id);
		map.put(id, c);
		return c;
	}
}
