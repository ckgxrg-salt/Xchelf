# Xchelf
An experimental schedule arrangement system for JNFLSIC.
The name itself has no meanings, probably.

## Problem
Any elective courses may be either Group A, B or C in the school.
When students choose a course, they add their name on the list.
Then the staff will have to find out how to arrange the courses in to the Groups.
However, this involves very complex analysis, since both the Group arrangement and student schedule are unknown at that time.
Moreover, some courses may not only belong to a single group, but can be divided to classes that are in different groups.

## Method
Courses are divided to Courses and ShadowCourses.
A Course is a simple one, that only belongs to a single group.
A ShadowCourse belongs to a MasterCourse, where
- The MasterCourse is a placeholder, it's not on the schedule.
- The ShadowCourses will be actually arranged.
- Initially, we only know who chose the respective MasterCourse, the internal arrangement between shadows is unknown.

Use a mathematical graph to illustrate the courses, where:
- Every course is a node.
- Two courses are connected(so they have an edge) if no any students overlaps.
- All connected courses can be arranged to a single group.
To do so, convert this to a maximum clique problem, so we can identify the largest set of courses that are all connected, so they can be arranged to a group.
Repeat this for 3 times to get the final schedule.

However, ShadowCourses cannot be done by this, because their internal arrangement(which students belong to which shadow) is also unknown.
Use a genetic algorithm, where the chromosome is the internal arrangement.
The fitness can be calculated by the process above, and count the flaws(the courses that cannot be arranged to a group).
Then iterate, until finding a best solution, and put this back to the above method to get the final schedule.

## Details
The mutate probablity is basically not so high, but when both parents' penalties are the same(i.e. the current generation likely lacks of diversity), the mutate probablity will be multiplied by 100 to ensure diversity(the multiplier will be even higher if the generations are still not diverse enough).
Currently, a course failed to assign makes a +10 penalty to a Gene, and a student failed to take their wanted course makes a +2 penalty to a Gene.
Genes, should the MIN_CAPACITY of population is satisfied, die naturally after 10 generations, to leave space for the mutated new generations. This helped greatly in preserving diversity so the converging rate greatly improved.

## Progress
Simple courses can already be arranged, and is undergoing test in JNFLSIC.
