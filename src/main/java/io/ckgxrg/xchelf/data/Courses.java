package io.ckgxrg.xchelf.data;

import java.util.ArrayList;
import java.util.HashMap;

/** Manage all courses. */
public class Courses {

  /*
   * Fields for Courses.
   *
   * map: The CourseID-Course mapping table, this table reflects the courses that need to be
   *     actually arranged
   * trans: The NodeID-CourseID table used to solve MCP
   * shadowMasters: The list of all MasterCourses
   */
  public static HashMap<Integer, Course> map = new HashMap<Integer, Course>();

  static ArrayList<MasterCourse> shadowMasters = new ArrayList<MasterCourse>();

  /*
   * List of all available courses.
   * The Names and IDs are listed below.
   * - (Shadow) AS Physics: 0
   * - A2 Physics: 1
   * - AS Chemistry: 2
   * - A2 Chemistry: 3
   * - AS Economics: 4
   * - (Shadow) A2 Economics: 5
   * - AS Biology: 6
   * - AS Computer Science: 7
   * - AS Psychology: 8
   * - AS Accounting: 9
   * - AS Art and Design: 10
   * - (Shadow) AS Further Mathematics: 11
   *
   * The MasterCourses do not represent the shadows.
   */
  public static Course Phys = register(0, "AS Physics", 3);
  public static Course Phys2 = register(1, "A2 Physics");
  public static Course Chem = register(2, "AS Chemistry");
  public static Course Chem2 = register(3, "A2 Chemistry");
  public static Course Eco = register(4, "AS Economics");
  public static Course Eco2 = register(5, "A2 Economics", 3);
  public static Course Bio = register(6, "AS Biology");
  public static Course CS = register(7, "AS Computer Science");
  public static Course Psy = register(8, "AS Psychology");
  public static Course Acc = register(9, "AS Accounting");
  public static Course Art = register(10, "AS Art and Design");
  public static Course FM = register(11, "AS Further Mathematics", 3);

  /**
   * Creates an entry in the table.
   *
   * @param id The course ID
   * @param name The course name
   * @return The registered Course
   */
  public static Course register(int id, String name) {
    return register(id, name, 0);
  }

  /**
   * Creates an entry in the table - full.
   *
   * @param id The course ID
   * @param name The course name
   * @param shadows If not 0, this course will be a MasterCourse and this number will be its
   *     ShadowCount
   * @return The registered Course
   */
  public static Course register(int id, String name, int shadows) {
    Course c;
    if (shadows == 0) {
      c = new Course(id, name);
      map.put(id, c);
    } else {
      c = new MasterCourse(id, name);
      shadowMasters.add((MasterCourse) c);
      ((MasterCourse) c).summonShadows(shadows);
      map.put(id, c);
    }
    return c;
  }

  /**
   * Removes a course from the register.
   *
   * @param id The course to be removed
   */
  public static void leave(int id) {
    map.remove(id);
  }

  public static ArrayList<MasterCourse> getShadowMasters() {
    return shadowMasters;
  }

  /** Sort all students in all courses. */
  public static void sortAll() {
    for (Course c : map.values()) {
      c.sortStudents();
    }
  }

  /**
   * Accepts a course ID and a student name, then add the student to the course.
   *
   * @param courseId The course
   * @param student The student name
   */
  public static void addStudentById(int courseId, String student) {
    map.get(courseId).addStudent(student);
  }

  /**
   * Returns the Course instance by the given ID.
   *
   * @param courseId The course ID
   * @return The Course instance
   */
  public static Course getCourseById(int courseId) {
    return map.get(courseId);
  }
}
