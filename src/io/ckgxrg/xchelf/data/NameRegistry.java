package io.ckgxrg.xchelf.data;

import java.util.HashMap;
import java.util.HashSet;

/*
 * A place to store student names for query.
 */
public class NameRegistry {

	public static HashMap<String, Course> A = new HashMap<String, Course>();
	public static HashMap<String, Course> B = new HashMap<String, Course>();
	public static HashMap<String, Course> C = new HashMap<String, Course>();
	
	public static HashSet<String> students = new HashSet<String>();
	public static HashSet<Course> allCourses = new HashSet<Course>();
	
	public static void entry(String student, Course c, Group g) {
		students.add(student);
		switch(g) {
		case Group.A :
			if(A.get(student) != null && !(A.get(student) instanceof ComplexCourse)) {
				//System.out.println("Refusing, this student already chose a course at this group.");
				break;
			}
			A.put(student, c);
			break;
		case Group.B :
			if(B.get(student) != null && !(B.get(student) instanceof ComplexCourse)) {
				//System.out.println("Refusing, this student already chose a course at this group.");
				break;
			}
			B.put(student, c);
			break;
		case Group.C :
			if(C.get(student) != null && !(C.get(student) instanceof ComplexCourse)) {
				//System.out.println("Refusing, this student already chose a course at this group.");
				break;
			}
			C.put(student, c);
			break;
		default :
			//System.out.println("How do we get here");
		}
	}
	
	public static Course courseOf(String student, Group g) {
		switch(g) {
		case Group.A :
			return A.get(student);
		case Group.B :
			return B.get(student);
		case Group.C :
			return C.get(student);
		default:
			System.out.println("WARNING: Not registered yet");
			return null;
		}
	}
	
	/*
	 * There is a potential problem of null pointers when a course is not chosen.
	 * Use this method instead of Course.getName() to prevent error.
	 */
	@SuppressWarnings("deprecation")
	public static String courseName(Course c) {
		if(c == null) return "Empty";
		return c.getName();
	}
}
