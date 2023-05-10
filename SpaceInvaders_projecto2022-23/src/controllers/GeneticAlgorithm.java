package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controllers.Controller.NeuralNetwork;
import space.Board;

public class GeneticAlgorithm {

	private List<NeuralNetwork> population = new ArrayList<>();

	private static final int POPULATION_SIZE = 100;
	private static final int MAX_GENERATIONS = 1000;
	private static final double MUTATION_ODD = 0.2;
	private static final double SELECTION_RATIO = 0.05;

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
			// attemptMutation();

			// Repeat through generations
			numOfGens++;
		}
	}

	public List<NeuralNetwork> generate() {
		List<NeuralNetwork> population = new ArrayList<>();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			population.add(new Controller(new NeuralNetwork()).getNeuralNetwork());
		}
		return population;
	}

	public int getRandom(int bound) {
		return new Random().nextInt(bound);
	}

	public void selection() {
		while (population.size() > SELECTION_RATIO * POPULATION_SIZE) {
			int a = getRandom(population.size());
			int b = getRandom(population.size());
			population.remove(lowerFitness(population.get(a), population.get(b)));
		}
	}

	public NeuralNetwork lowerFitness(NeuralNetwork nn1, NeuralNetwork nn2) {
		return (nn1.getFitness() < nn2.getFitness()) ? nn1 : nn2;
	}

	public void newGeneration() {
		List<NeuralNetwork> list = new ArrayList<>();
		while (list.size() < POPULATION_SIZE) {
			int a = getRandom(population.size());
			int b = getRandom(population.size());
			list.add(crossover(population.get(a), population.get(b)));
		}
		population = list;
	}

	private NeuralNetwork crossover(NeuralNetwork nn1, NeuralNetwork nn2) {
		NeuralNetwork nn = new NeuralNetwork();
		for (int i = 0; i < nn1.getArray().length; i++)
			nn.getArray()[i] = (i < getRandom(nn1.getArray().length)) ? nn1.getArray()[i] : nn2.getArray()[i];
		return nn;
	}

	public void attemptMutation() {
		for (NeuralNetwork nn : population) {
			if (Math.random() <= MUTATION_ODD) {}
//				nn.getArray()[getRandom(nn.getArray().length)] = new Random();
		}
	}

	/*public Individual checkSolution() {

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
		NaturalSelection queens = new NaturalSelection(); // Individual i = new Individual(a); //
		System.out.println(i.calculateFitness());
	}
*/
}
