package io.ckgxrg.xchelf.data;

import java.util.ArrayList;
import java.util.Collections;

/** Represents a course to be chosen. */
public class Course {

  public int id;
  public String name;
  ArrayList<String> students;
  public Group group;

  /**
   * A course entry.
   *
   * @param id The course id
   * @param courseName The course name
   */
  public Course(int id, String courseName) {
    name = courseName;
    group = Group.UNKNOWN;
    this.id = id;
    this.students = new ArrayList<String>();
  }

  /**
   * Enroll a student to the course.
   *
   * @param name The student name
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

  /**
   * Makes a whole shift of student list.
   *
   * @param students The new student list
   */
  public void groupMove(ArrayList<String> students) {
    this.students = students;
  }

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

  /**
   * If there is any student overlapping in the 2 courses, the 2 courses cannot be in the same
   * group.
   *
   * @param other The other course
   * @return Whether the 2 courses conflict
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
