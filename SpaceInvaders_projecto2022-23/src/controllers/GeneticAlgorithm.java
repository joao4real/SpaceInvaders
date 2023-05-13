package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import controllers.Controller.NeuralNetwork;
import space.SpaceInvaders;

public class GeneticAlgorithm {

	private List<NeuralNetwork> population = new ArrayList<>();

	private static final int POPULATION_SIZE = 100;
	private static final int MAX_GENERATIONS = 50;
	private static final double MUTATION_ODD = 0.8;
	private static final double SELECTION_RATIO = 0.05;
	private static final int NUM_OF_CHANGES = 1;


	private long seed;

	public GeneticAlgorithm() {

		//Initialize seed
		seed = new Random().nextLong();

		//Initialization
		generate();

		//Evaluate the first generation
		evaluate();

		System.out.println("Generation: 1");
		population.forEach(nn -> System.out.println(nn.getFitness()));

		int numOfGens = 1;
		while (numOfGens++ < MAX_GENERATIONS) {
			System.out.println("Generation: " + numOfGens);
			population.forEach(nn -> System.out.println(nn.getFitness()));
			//Selection
			selection();

			//Cross Over
			newGeneration();

			//Mutation
			attemptMutation();

			//Evaluate
			evaluate();

		}
		showFittestIndividual();
	}

	private void generate() {
		for (int i = 0; i < POPULATION_SIZE; i++) 
			population.add(new NeuralNetwork());
	}

	private void evaluate() {
		population.forEach(nn -> nn.evaluate(seed));
	}

	private int getRandom(int bound) {
		return new Random().nextInt(bound);
	}

	private void selection() {
		//Mete em ordem crescente
		Collections.sort(population);
		//Calcula o novo tamanho
		int newSize = (int) (SELECTION_RATIO * POPULATION_SIZE);
		//Remove os elementos excedentes da lista
		population.subList(newSize, population.size()).clear();
	}

	/*public NeuralNetwork lowerFitness(NeuralNetwork nn1, NeuralNetwork nn2) {
		return (nn1.getFitness() < nn2.getFitness()) ? nn1 : nn2;
	}*/

	public void newGeneration() {
		while (population.size() < POPULATION_SIZE) 
			population.add(crossOver(population.get(getRandom(population.size())), population.get(getRandom(population.size()))));

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
				while (x++ <= NUM_OF_CHANGES) {
					nn.getArray()[getRandom(nn.getArray().length)] = ThreadLocalRandom.current().nextDouble(-1,1);
				}
	}

	private void showFittestIndividual() {
		NeuralNetwork nn = new NeuralNetwork();
		for(NeuralNetwork n : population)
			nn = (nn.getFitness() > n.getFitness()) ? nn : n;
		SpaceInvaders.showControllerPlaying(new Controller(nn), seed);
	}
}