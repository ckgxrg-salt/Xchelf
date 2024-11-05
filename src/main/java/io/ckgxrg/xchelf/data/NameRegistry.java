package io.ckgxrg.xchelf.data;

import java.util.HashMap;
import java.util.HashSet;

/** A place to store student names for query. */
public class NameRegistry {

  public static HashMap<String, Course> A = new HashMap<String, Course>();
  public static HashMap<String, Course> B = new HashMap<String, Course>();
  public static HashMap<String, Course> C = new HashMap<String, Course>();

  public static HashSet<String> students = new HashSet<String>();

  /**
   * Registers the arrangement.
   *
   * @param student The student name
   * @param c The course instance
   * @param g The course group
   * @return Whether this entry succeeded or not.
   */
  public static boolean entry(String student, Course c, Group g) {
    students.add(student);
    switch (g) {
      case Group.A:
        if (A.get(student) != null) {
          /*System.out.println(
          "Refusing, "
              + student
              + " already chose "
              + courseName(A.get(student))
              + " at group "
           + g);*/
          return false;
        }
        A.put(student, c);
        break;
      case Group.B:
        if (B.get(student) != null) {
          /*System.out.println(
          "Refusing, "
              + student
              + " already chose "
              + courseName(B.get(student))
              + " at group "
              + g);*/
          return false;
        }
        B.put(student, c);
        break;
      case Group.C:
        if (C.get(student) != null) {
          /*System.out.println(
          "Refusing, "
              + student
              + " already chose "
              + courseName(C.get(student))
              + " at group "
              + g);*/
          return false;
        }
        C.put(student, c);
        break;
      default:
        return false;
    }
    return true;
  }

  public static void reset() {
    A.clear();
    B.clear();
    C.clear();
    students.clear();
  }

  /*
   * Queries a student's course arrangement of a group.
   */
  public static Course courseOf(String student, Group g) {
    switch (g) {
      case Group.A:
        return A.get(student);
      case Group.B:
        return B.get(student);
      case Group.C:
        return C.get(student);
      default:
        System.out.println("WARNING: Not registered yet");
        return null;
    }
  }

  public static String listAllInGroup(Group g, String delimiter) {
    StringBuilder sb = new StringBuilder();
    switch (g) {
      case Group.A:
        HashSet<Course> valueA = new HashSet<Course>(A.values());
        for (Course co : valueA) {
          sb.append(courseName(co));
          sb.append(delimiter);
        }
        break;
      case Group.B:
        HashSet<Course> valueB = new HashSet<Course>(B.values());
        for (Course co : valueB) {
          sb.append(courseName(co));
          sb.append(delimiter);
        }
        break;
      case Group.C:
        HashSet<Course> valueC = new HashSet<Course>(C.values());
        for (Course co : valueC) {
          sb.append(courseName(co));
          sb.append(delimiter);
        }
        break;
      default:
        System.err.println("WTF");
    }
    return sb.toString();
  }

  /**
   * There is a potential problem of null pointers when a course is not chosen. Use this method
   * instead of Course.getName() to prevent error.
   *
   * @param c The course instance
   * @return The course name, if exists, or "Empty"
   */
  @SuppressWarnings("deprecation")
  public static String courseName(Course c) {
    if (c == null) {
      return "Empty";
    }
    return c.getName();
  }

  /**
   * Same as above.
   *
   * @param id The course ID
   * @return The course name, if exists, or "Empty"
   */
  public static String courseName(int id) {
    return courseName(Courses.getCourseById(id));
  }
}
