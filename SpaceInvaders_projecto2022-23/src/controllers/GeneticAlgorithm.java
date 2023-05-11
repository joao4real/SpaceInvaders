package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import controllers.Controller.NeuralNetwork;
import space.SpaceInvaders;

public class GeneticAlgorithm {

	private List<NeuralNetwork> population = new ArrayList<>();

	private static final int POPULATION_SIZE = 100;
	private static final int MAX_GENERATIONS = 200;
	private static final double MUTATION_ODD = 0.2;
	private static final double SELECTION_RATIO = 0.2;
	private static final int NUM_OF_CHANGES = 4;

	private long seed;
	
	public GeneticAlgorithm() {
	
		
		// Initialization
		population = generate();

		int numOfGens = 1;
		while (numOfGens < MAX_GENERATIONS) {

			// Selection
			selection();
			System.out.println("selection");
			// Cross Over
			newGeneration();
			System.out.println("crossover");
			// Mutation
			attemptMutation();
			System.out.println("mutation");
			// Generation improvement
			numOfGens++;
		}
		SpaceInvaders.showControllerPlaying(new Controller(population.get(0)),seed);
	}

	public List<NeuralNetwork> generate() {
		List<NeuralNetwork> population = new ArrayList<>();
		seed = new Random().nextLong();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			population.add(new Controller(new NeuralNetwork()).getNeuralNetwork());
		}
		return population;
	}

	public int getRandom(int bound) {
		return new Random().nextInt(bound);
	}

	public void selection() {
		sort();
		List<NeuralNetwork> nn1= new ArrayList<>();
		for(int i = 0; i < POPULATION_SIZE * SELECTION_RATIO; i++)
			nn1.add(population.get(i));
		population = nn1;
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
				while (x++ < NUM_OF_CHANGES)
					nn.getArray()[getRandom(nn.getArray().length)] = ThreadLocalRandom.current().nextDouble(-1,1);
	}

	public void sort() {
		population.sort((nn1,nn2) -> (int)(nn2.getFitness(seed) - nn1.getFitness(seed)));
	}

	public List<NeuralNetwork> getPopulation() {
		return population;
	}

	public long getSeed() {
		return seed;
	}
}
