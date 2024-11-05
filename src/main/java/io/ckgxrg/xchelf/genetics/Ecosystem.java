package io.ckgxrg.xchelf.genetics;

import io.ckgxrg.xchelf.data.Course;
import io.ckgxrg.xchelf.data.Courses;
import io.ckgxrg.xchelf.data.Group;
import io.ckgxrg.xchelf.data.MasterCourse;
import io.ckgxrg.xchelf.data.NameRegistry;
import io.ckgxrg.xchelf.data.ShadowCourse;
import io.ckgxrg.xchelf.math.Graph;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Ecosystem class simulates an environment where the ShadowEvolution can use to calculate
 * penalty, by substituting the current shadow arrangement inside.
 *
 * <p>Inside an Ecosystem, all courses are simple, so we can carry out MCP algorithm now.
 */
public class Ecosystem {

  HashMap<Integer, Integer> trans;
  HashMap<Integer, Course> env;

  /**
   * An ecosystem takes consideration of all simple courses recorded in Courses.map, and also adds
   * the course arrangements from the given Gene.
   *
   * @param g The Gene given
   */
  public Ecosystem(Gene g) {
    this.env = new HashMap<Integer, Course>();
    for (Integer i : Courses.map.keySet()) {
      if (!Courses.getShadowMasters().contains(Courses.map.get(i))) {
        this.env.put(i, Courses.map.get(i));
      }
    }
    HashMap<Integer, ShadowCourse> shadows = new HashMap<Integer, ShadowCourse>();
    for (MasterCourse m : g.segments.keySet()) {
      for (int i = 0; i < m.getStudents().size(); i++) {
        int id = Integer.valueOf(g.segments.get(m).split(";")[i]);
        if (!shadows.containsKey(id)) {
          shadows.put(id, new ShadowCourse(id, m));
        }
        shadows.get(id).addStudent(m.getStudents().get(i));
      }
    }
    env.putAll(shadows);
  }

  /** Debug use. */
  public void test() {
    for (Course c : env.values()) {
      System.out.println(NameRegistry.courseName(c));
    }
  }

  /**
   * Creates a graph, where NodeIDs and corresponding CourseIDs are recorded in trans Each remaining
   * course as a vertex Each pair of courses with no students overlapping is connected as an edge
   * from the current map table.
   *
   * @return The generated graph
   */
  public Graph generateGraph() {
    Graph g = new Graph(env.size());
    trans = new HashMap<Integer, Integer>();
    int index = 0;
    for (int i : env.keySet()) {
      trans.put(index, i);
      int jndex = 0;
      for (int j : env.keySet()) {
        if (!env.get(i).conflict(env.get(j))) {
          g.connect(index, jndex);
        }
        jndex++;
      }
      index++;
    }
    return g;
  }

  /**
   * Since the node ids returned from MCP is not always equivalent to course ids, use thie method to
   * ensure getting the correct ids.
   *
   * @param nodeIds The node ids from MaxCliqueProblem.
   */
  public ArrayList<Integer> translate(ArrayList<Integer> nodeIds) {
    ArrayList<Integer> res = new ArrayList<Integer>();
    for (Integer i : nodeIds) {
      res.add(trans.get(i));
    }
    return res;
  }

  /*
   * Assign a Group to the processed courses and remove them from the
   * to-be-grouped list.
   */
  public void assign(Group g, ArrayList<Integer> arr) {
    for (Integer i : arr) {
      env.get(i).group = g;
      env.remove(i);
    }
  }

  /*
   * Checks if all courses have been assigned to a group.
   */
  public boolean allAssigned() {
    for (Course c : env.values()) {
      if (c.group == Group.UNKNOWN) {
        return false;
      }
    }
    return true;
  }

  public ArrayList<Course> unassigned() {
    ArrayList<Course> res = new ArrayList<Course>();
    for (Course c : env.values()) {
      if (c.group == Group.UNKNOWN) res.add(c);
    }
    return res;
  }
}
