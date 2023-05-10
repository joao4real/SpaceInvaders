package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import controllers.Controller.NeuralNetwork;

public class GeneticAlgorithm {

	private List<NeuralNetwork> population = new ArrayList<>();

	private static final int POPULATION_SIZE = 100;
	private static final int MAX_GENERATIONS = 1000;
	private static final double MUTATION_ODD = 0.2;
	private static final double SELECTION_RATIO = 0.05;
	private static final int NUM_OF_CHANGES = 4;

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
			attemptMutation();

			// Generation improvement
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
			list.add(crossOver(population.get(a), population.get(b)));
		}
		population = list;
	}

	private NeuralNetwork crossOver(NeuralNetwork nn1, NeuralNetwork nn2) {
		NeuralNetwork nn = new NeuralNetwork();
		int pointer = getRandom(nn1.getArray().length);
		for (int i = 0; i < nn1.getArray().length; i++)
			nn.getArray()[i] = (i < pointer) ? nn1.getArray()[i] : nn2.getArray()[i];
		return nn;
	}

	public void attemptMutation() {
		int x = 0;
		for (NeuralNetwork nn : population) 
			if (Math.random() <= MUTATION_ODD) 
				while (x < NUM_OF_CHANGES)
					nn.getArray()[getRandom(nn.getArray().length)] = ThreadLocalRandom.current().nextDouble(0, 100000);
	}

	public double fittestChromossome() {
		NeuralNetwork temp = null;
		for (int i = 0; i < population.size(); i++)
			temp = (population.get(i).getFitness() > temp.getFitness()) ? population.get(i) : temp;
		return temp.getFitness();
	}
}
