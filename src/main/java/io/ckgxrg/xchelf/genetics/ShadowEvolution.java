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
  public static final int ELITE_MAX_PENALTY = 30;

  public static HashSet<Gene> population;
  public static HashSet<Gene> elites;

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
    calculatePenalty(new ArrayList<Gene>(population));
  }

  /** Calculates penalty for each Gene in the set. Very expensive. */
  public static void calculatePenalty(ArrayList<Gene> candidates) {
    for (Gene g : candidates) {
      Ecosystem eco = new Ecosystem(g);
      Graph graph = eco.generateGraph();
      ArrayList<Integer> a = eco.translate(MaxCliqueProblem.solve(graph));
      // System.out.println("Group A: " + Courses.prettyPrint(A));
      eco.assign(Group.A, a);
      graph = eco.generateGraph();

      ArrayList<Integer> b = eco.translate(MaxCliqueProblem.solve(graph));
      // System.out.println("Group B: " + Courses.prettyPrint(B));
      eco.assign(Group.B, b);
      graph = eco.generateGraph();

      ArrayList<Integer> c = eco.translate(MaxCliqueProblem.solve(graph));
      // System.out.println("Group C: " + Courses.prettyPrint(C));
      eco.assign(Group.C, c);

      g.penalty = 0;
      for (Course co : eco.unassigned()) {
        g.penalty += 10;
        System.out.println(NameRegistry.courseName(co));
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
  public static void stableStage() {
    ArrayList<Gene> candidates = new ArrayList<Gene>();
    candidates.addAll(population);
    Collections.sort(candidates);
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
    int current = candidates.getFirst().penalty;
    System.out.println("Current Generation lowest penalty: " + current);
    for (Gene g : candidates) {
      if (population.size() >= MAX_CAPACITY) {
        break;
      }
      if (g.penalty > current && population.size() >= MIN_CAPACITY) {
        break;
      } else {
        current = g.penalty;
      }
      System.out.println("Selected Gene with penalty " + g.penalty);
      population.add(g);
      eliteCheck(g);
    }
  }

  /**
   * When a single Individual reaches the ELITE_MAX_PENALTY, add it to the elites. Elites are
   * protected so they won't be easily eliminated.
   */
  public static void eliteCheck(Gene g) {
    if (g.penalty <= ELITE_MAX_PENALTY) {
      if (elites.size() < ELITE_COUNT) {
        elites.add(g);
        System.out.println("Found a new Elite with penalty " + g.penalty);
      } else {
        Gene highest = g;
        for (Gene elite : elites) {
          if (elite.penalty > highest.penalty) {
            highest = elite;
          }
        }
        if (highest != g) {
          elites.remove(highest);
          elites.add(g);
          System.out.println("Found another new Elite with penalty " + g.penalty);
        }
      }
    }
  }
}
