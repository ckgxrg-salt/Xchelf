package io.ckgxrg.xchelf.data;

import java.util.ArrayList;
import java.util.Collections;

/*
 * Represents a course to be chosen.
 */
public class Course {

  public int id;
  public String name;
  ArrayList<String> students;
  public Group group;

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

  /**
   * Sort all students by the natural order, so they can be used as the generic gene sequence
   * reference.
   */
  public void sortStudents() {
    Collections.sort(students);
  }

  public void groupMove(ArrayList<String> students) {
    this.students = students;
  }

  /*
   * Return the list of students
   */
  public ArrayList<String> getStudents() {
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

  public int getId() {
    return this.id;
  }

  @Override
  public String toString() {
    return "" + this.id;
  }

  public Group getGroup() {
    return this.group;
  }

  /*
   * If there is any student overlapping in the 2 courses,
   * the 2 courses cannot be in the same group.
   */
  public boolean conflict(Course other) {
    for (String s : other.students) {
      if (this.students.contains(s)) {
        return true;
      }
    }
    return false;
  }
}
