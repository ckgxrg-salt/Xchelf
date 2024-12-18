package io.ckgxrg.xchelf.genetics;

import io.ckgxrg.xchelf.data.Course;
import io.ckgxrg.xchelf.data.Group;
import io.ckgxrg.xchelf.data.NameRegistry;
import io.ckgxrg.xchelf.math.Graph;
import io.ckgxrg.xchelf.math.MaxCliqueProblem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/** The ShadowEvolution class is a static class that handles iterations of the genetic algorithm. */
public class ShadowEvolution {
  public static final int MAX_CAPACITY = 50;
  public static final int MIN_CAPACITY = 20;
  public static final int ELITE_COUNT = 5;
  public static final double MUTATE_PROBABLITY = 0.00001;
  public static final int GENE_LIFETIME = 4;
  public static int diversity_multiplier = 1000;

  public static final int PENALTY_UNASSIGNED = 10;
  public static final int PENALTY_CLASH = 2;

  private static HashSet<Gene> population;
  private static HashSet<Gene> elites;
  public static int elites_highest_penalty;

  /**
   * Initialises the population by randomly generating Individuals, until satisfying the
   * MIN_CAPACITY.
   */
  public static void populateStage() {
    System.out.println("Evolution began: Populating initial generation...");
    population = new HashSet<Gene>();
    elites = new HashSet<Gene>();
    while (population.size() < MIN_CAPACITY) {
      population.add(new Gene());
    }
    System.out.println("Initial generation populated. ");
    ArrayList<Gene> sort = new ArrayList<Gene>(population);
    calculatePenalty(sort);
    Collections.sort(sort);
    for (int i = 0; i < ELITE_COUNT; i++) {
      elites.add(sort.get(i));
    }
    elites_highest_penalty = sort.get(ELITE_COUNT - 1).penalty;
    System.out.println("Initial elites selected. ");
    showElites();
    System.out.println("Min initial penalty: " + sort.getFirst().penalty);
    System.out.println("Max initial penalty: " + sort.getLast().penalty);
  }

  /** Calculates penalty for each Gene in the set. Very expensive. */
  public static void calculatePenalty(ArrayList<Gene> candidates) {
    for (Gene g : candidates) {
      // Skip previously calculated genes
      if (g.penalty != Integer.MAX_VALUE) {
        continue;
      }
      Ecosystem eco = new Ecosystem(g);
      Graph graph = eco.generateGraph();
      ArrayList<Integer> a = eco.translate(MaxCliqueProblem.solve(graph));
      eco.assign(Group.A, a);
      graph = eco.generateGraph();

      ArrayList<Integer> b = eco.translate(MaxCliqueProblem.solve(graph));
      // System.out.println("Group B: " + Courses.prettyPrint(B));
      eco.assign(Group.B, b);
      graph = eco.generateGraph();

      ArrayList<Integer> c = eco.translate(MaxCliqueProblem.solve(graph));
      // System.out.println("Group C: " + Courses.prettyPrint(C));
      eco.assign(Group.C, c);

      NameRegistry.reset();
      g.penalty = 0;
      g.penalty += PENALTY_UNASSIGNED * eco.unassigned().size();
      for (Course co : eco.env.values()) {
        for (String s : co.getStudents()) {
          if (!NameRegistry.entry(s, co, co.getGroup())) {
            g.penalty += PENALTY_CLASH;
          }
        }
      }
    }
  }

  /**
   * Iterates the population, do crossovers, evaluate penalty for each child, then select the next
   * generation. The population should always be within MIN_CAPACITY and MAX_CAPACITY, so after the
   * penalty is evaluated and ranked, select the first MIN_CAPACITY Individual to put them in the
   * next generation, if there are equally-ranked Individuals, put them in too, however, if the
   * number meets MAX_CAPACITY, immediately finish the selection.
   */
  @SuppressWarnings("checkstyle:variabledeclarationusagedistance")
  public static void iterate() {
    ArrayList<Gene> candidates = new ArrayList<Gene>();
    candidates.addAll(population);
    Collections.sort(candidates);
    // This has to be here, but evidently checkstyle is unaware.
    int previous = candidates.getFirst().penalty;
    System.out.println("Mating all Genes...");
    for (int i = 0; i < population.size(); i++) {
      for (int j = i; j < population.size(); j++) {
        candidates.add(candidates.get(i).mate(candidates.get(j)));
      }
    }
    System.out.println("Mating resulted in a total of " + candidates.size() + " candidates. ");
    System.out.println("Calculating penalty...");
    calculatePenalty(candidates);
    Collections.sort(candidates);
    population.clear();
    // Protect the elites
    System.out.println("Current Candidates lowest penalty: " + candidates.getFirst().penalty);
    System.out.println("Current Candidates highest penalty: " + candidates.getLast().penalty);
    // If there are no change, raise mutate probablity to ensure diversity
    if (candidates.getFirst().penalty == previous) {
      diversity_multiplier += 500;
      System.out.println("Diversity Multiplier: " + diversity_multiplier);
    } else {
      diversity_multiplier = 1000;
    }
    for (Gene g : candidates) {
      if (population.size() >= MAX_CAPACITY) {
        System.out.println("The first eliminated gene has penalty " + g.penalty);
        break;
      }
      if (population.size() >= MIN_CAPACITY && (g.lifetime >= GENE_LIFETIME)) {
        continue;
      }
      population.add(g);
      g.lifetime++;
      eliteCheck(g);
    }
    eliteDump();
    population.addAll(elites);
    System.out.println("Current Generation size: " + population.size());
  }

  /**
   * When a single Individual reaches the ELITE_MAX_PENALTY, add it to the elites. Elites are
   * protected so they won't be easily eliminated.
   */
  public static void eliteCheck(Gene g) {
    if (g.penalty < elites_highest_penalty) {
      /*for (Gene elite : elites) {
        if (elite.penalty == g.penalty) {
          return;
        }
      }*/
      elites.add(g);
      /*System.out.println(
      "A new Elite with penalty "
          + g.penalty
          + " is added to the queue, since the current max penalty is "
          + elites_highest_penalty);*/
    }
  }

  /**
   * Selects only the first ELITE_COUNT elite candidates in the list. Then marks the current max
   * penalty.
   */
  public static void eliteDump() {
    ArrayList<Gene> sort = new ArrayList<Gene>(elites);
    Collections.sort(sort);
    elites.clear();
    for (int i = 0; i < ELITE_COUNT; i++) {
      elites.add(sort.get(i));
    }
    elites_highest_penalty = sort.get(ELITE_COUNT - 1).penalty;
    showElites();
  }

  /** Debug use. */
  public static void showElites() {
    System.out.print("Current Elites: ");
    for (Gene elite : elites) {
      System.out.print(elite.penalty + " ");
    }
    System.out.println();
  }

  /** Finalises the evolution and generates a report using the current generation. */
  public static void generateReport() {
    System.out.println("Evolution ended. Generating report using the current generation...");
    ArrayList<Gene> result = new ArrayList<Gene>(population);
    Collections.sort(result);
    Ecosystem eco = new Ecosystem(result.getFirst());
    Graph graph = eco.generateGraph();

    ArrayList<Integer> a = eco.translate(MaxCliqueProblem.solve(graph));
    eco.assign(Group.A, a);
    graph = eco.generateGraph();

    ArrayList<Integer> b = eco.translate(MaxCliqueProblem.solve(graph));
    eco.assign(Group.B, b);
    graph = eco.generateGraph();

    ArrayList<Integer> c = eco.translate(MaxCliqueProblem.solve(graph));
    eco.assign(Group.C, c);

    NameRegistry.reset();
    for (Course co : eco.env.values()) {
      for (String s : co.getStudents()) {
        NameRegistry.entry(s, co, co.getGroup());
      }
    }

    System.out.println(
        "==========Xchelf Report==========\n"
            + "- Current Generation\n"
            + "    Min Penalty: "
            + result.getFirst().penalty
            + "\n"
            + "    Max Penalty: "
            + result.getLast().penalty
            + "\n"
            + "A total of "
            + eco.unassigned().size()
            + " courses cannot be scheduled properly\n"
            + "- Groups Arrangement\n"
            + "    Group A: "
            + NameRegistry.listAllInGroup(Group.A, "; ")
            + "\n"
            + "    Group B: "
            + NameRegistry.listAllInGroup(Group.B, "; ")
            + "\n"
            + "    Group C: "
            + NameRegistry.listAllInGroup(Group.C, "; ")
            + "\n"
            + "- Courses failed to be arranged properly");
    for (Course co : eco.unassigned()) {
      System.out.print(NameRegistry.courseName(co) + ", ");
    }
    System.out.println("\n- Student Schedules");
    for (String s : NameRegistry.students) {
      System.out.print(s + ": ");
      System.out.print(NameRegistry.courseName(NameRegistry.courseOf(s, Group.A)) + " | ");
      System.out.print(NameRegistry.courseName(NameRegistry.courseOf(s, Group.B)) + " | ");
      System.out.print(NameRegistry.courseName(NameRegistry.courseOf(s, Group.C)) + "\n");
    }
    System.out.println("========End Xchelf Report========");
  }
}
