package controllers;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {

	private List<Controller> population = new ArrayList<>();
	
	/*public class NaturalSelection {

		public static final int POPULATION_SIZE = 100;
		public static final int MAX_GENERATIONS = 1000;
		public static final double MUTATION_ODD = 0.2;
		public static final double SELECTION_RATIO = 0.2;

		private List<Individual> population = new ArrayList<>();

		public NaturalSelection() {

			// Initialization
			population = generate();

			int numOfGens = 0;
			while (numOfGens < MAX_GENERATIONS) {

				// Selection
				selection();

				// Cross Over
				newGeneration();

				// Mutation
				attemptMutation();

				// Termination
				Individual i = checkSolution();
				if (i != null) {
					System.out.println("BEST SOLUTION FOUNDED:\n\n" + i + "\n\nTOTAL OF GENERATIONS:\n\n" + numOfGens);
					return;
				}
				numOfGens++;
			}
			System.out.println("BEST SOLUTION NOT FOUNDED...\n" + "TOTAL OF GENERATIONS: " + numOfGens);

		}

		public List<Individual> generate() { // WORKING
			List<Individual> population = new ArrayList<>();
			for (int i = 0; i < POPULATION_SIZE; i++)
				population.add(new Individual());
			return population;
		}

		public static int getRandom(int bound) { // WORKING
			return new Random().nextInt(bound);
		}

		public void selection() { // WORKING
			while (population.size() > SELECTION_RATIO * POPULATION_SIZE) {
				int a = getRandom(population.size());
				int b = getRandom(population.size());
				population.remove(Individual.lowerFitness(population.get(a), population.get(b)));
			}
		}

		public void newGeneration() {

			List<Individual> list = new ArrayList<>();
			while (list.size() < POPULATION_SIZE) {
				int a = getRandom(population.size());
				int b = getRandom(population.size());
				list.add(Individual.childrenOf(population.get(a), population.get(b)));
			}
			population = list;
		}

		public void attemptMutation() {
			for (Individual i : population) 
				if (Math.random() <= MUTATION_ODD) 
					i.getArrayOfElements()[getRandom(Individual.SIZE)] = getRandom(Individual.SIZE);
		}

		public Individual checkSolution() {

			for (Individual i : population)
				if (i.calculateFitness() == Individual.MAX_FITNESS)
					return i;
			return null;
		}

		@Override
		public String toString() {
			String x = "";
			for (Individual i : population)
				x += i + "," + i.calculateFitness() + "\n";
			return x;
		}

		public static void main(String[] args) {
			NaturalSelection queens = new NaturalSelection();
//			Individual i = new Individual(a);
//			System.out.println(i.calculateFitness());
		}
	}*/
}
