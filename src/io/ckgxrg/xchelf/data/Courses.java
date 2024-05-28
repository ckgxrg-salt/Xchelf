package io.ckgxrg.xchelf.data;

import java.util.ArrayList;
import java.util.HashMap;

import io.ckgxrg.xchelf.math.Graph;

/*
 * Manage all courses.
 */
public class Courses {

	public static HashMap<Integer, Course> map = new HashMap<Integer, Course>();
	static HashMap<Integer, Integer> trans;
	
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
	public static Course Phys = register(0, "AS Physics");
	public static Course Phys2 = register(1, "A2 Physics");
	public static Course Chem = register(2, "AS Chemistry");
	public static Course Chem2 = register(3, "A2 Chemistry");
	public static Course Eco = register(4, "AS Economics");
	public static Course Eco2 = register(5, "A2 Economics");
	public static Course Bio = register(6, "AS Biology");
	public static Course CS = register(7, "AS Computer Science");
	public static Course Psy = register(8, "AS Psychology");
	public static Course Acc = register(9, "AS Accounting");
	public static Course Art = register(10, "AS Art and Design");
	public static Course FM = register(11, "AS Further Mathematics");
	
	public static Course register(int id, String name) {
		Course c = new Course(id, name);
		map.put(id, c);
		return c;
	}
	
	public static Graph generateGraph() {
		Graph g = new Graph(map.size());
		trans = new HashMap<Integer, Integer>();
		int index = 0;
		for(int i : map.keySet()) {
			trans.put(index, i);
			int jndex = 0;
			for(int j : map.keySet()) {
				if(!map.get(i).conflict(map.get(j))) {
					g.connect(index, jndex);
				} else {
				}
				jndex++;
			}
			index++;
		}
		return g;
	}
	
	public static String prettyPrint(ArrayList<Integer> courses) {
		StringBuilder sb = new StringBuilder("[ ");
		for(Integer i : courses) {
			sb.append(map.get(i).getName());
			sb.append(", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append(" ]");
		return sb.toString();
	}
	
	/*
	 * Since the node ids returned from MCP is not always equivalent to course ids,
	 * use thie method to ensure getting the correct ids.
	 * @param nodeIDs The node ids from MaxCliqueProblem.
	 */
	public static ArrayList<Integer> translate(ArrayList<Integer> nodeIDs){
		ArrayList<Integer> res = new ArrayList<Integer>();
		for(Integer i : nodeIDs) {
			res.add(trans.get(i));
		}
		return res;
	}
	
	public static void assign(Group g, ArrayList<Integer> arr) {
		for(Integer i : arr) {
			map.get(i).group = g;
			map.remove(i);
		}
	}
	public static boolean allAssigned() {
		for(Course c : map.values()) {
			if(c.group == Group.UNKNOWN) return false;
		}
		return true;
	}
}
