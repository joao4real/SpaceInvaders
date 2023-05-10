package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controllers.Controller.NeuralNetwork;
import space.Board;

public class GeneticAlgorithm {

	private List<Board> population = new ArrayList<>();
	
	private static final int POPULATION_SIZE = 100;
	private static final int MAX_GENERATIONS = 1000;
	private static final double MUTATION_ODD = 0.2;
	private static final double SELECTION_RATIO = 0.2;
	
	public GeneticAlgorithm() {

			// Initialization
			population = generate();

			int numOfGens = 1;
			while (numOfGens < MAX_GENERATIONS) {

				// Selection
				selection();

				// Cross Over
				newGeneration();

				// Mutation
				//attemptMutation();

				// Repeat through generations
				numOfGens++;
			}
		}

		public List<Board> generate() { // WORKING
			List<Board> population = new ArrayList<>();
			for (int i = 0; i < POPULATION_SIZE; i++)
				population.add(new Board(new Controller(new NeuralNetwork())));
			return population;
		}

		public int getRandom(int bound) { // WORKING
			return new Random().nextInt(bound);
		}

		public void selection() { // WORKING
			while (population.size() > SELECTION_RATIO * POPULATION_SIZE) {
				int a = getRandom(population.size());
				int b = getRandom(population.size());
				population.remove(lowerFitness(population.get(a), population.get(b)));
			}
		}

		public Board lowerFitness(Board b1, Board b2) {
			return (b1.getFitness() < b2.getFitness()) ?  b1 : b2;
		}

		public void newGeneration() {

			List<Board> list = new ArrayList<>();
			while (list.size() < POPULATION_SIZE) {
				int a = getRandom(population.size());
				int b = getRandom(population.size());
				list.add(childrenOf(population.get(a), population.get(b)));
			}
			population = list;
		}

		private Board childrenOf(Board b1, Board b2) {
			return null; //TODO 
		}

		/*public void attemptMutation() {
			for (Board b : population) 
				if (Math.random() <= MUTATION_ODD) 
					i.getArrayOfElements()[getRandom(Individual.SIZE)] = getRandom(Individual.SIZE);
		}

		public Individual checkSolution() {

			for (Individual i : population)
				if (i.calculateFitness() == Individual.getMax_fitness())
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
		}*/
	}

