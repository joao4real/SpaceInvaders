package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import controllers.Controller.NeuralNetwork;
import space.SpaceInvaders;

public class GeneticAlgorithm {

	private List<NeuralNetwork> population = new ArrayList<>();

	private static final int POPULATION_SIZE = 200;
	private static final int MAX_GENERATIONS = 100;
	private static final double SELECTION_RATIO = 0.02;
	protected static final int NUM_OF_CHANGES = 5;
	protected static final double MUTATION_ODD = 0.8;

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
			
			//Selection
			selection();

			//Cross Over
			newGeneration();

			//Mutation
			attemptMutation();

			//Evaluate
			evaluate();
			
			//Show new generation
			System.out.println("Generation: " + numOfGens);
			population.forEach(nn -> System.out.println(nn.getFitness()));

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

	protected static int getRandom(int bound) {
		return new Random().nextInt(bound);
	}

	private void selection() {
		
		Collections.sort(population);
		int newSize = (int) (SELECTION_RATIO * POPULATION_SIZE); 	//Calculate the selected parents size
		population.subList(newSize, population.size()).clear(); 	//Remove
	}

	public void newGeneration() {
		while (population.size() < POPULATION_SIZE) 
			population.add(NeuralNetwork.crossOver(population.get(getRandom(population.size())), population.get(getRandom(population.size()))));
	}
	
	public void attemptMutation() {
		population.forEach(nn -> nn.mutation());	
	}

	private void showFittestIndividual() {
		NeuralNetwork nn = new NeuralNetwork();
		for(NeuralNetwork n : population)
			nn = (nn.getFitness() > n.getFitness()) ? nn : n;
		SpaceInvaders.showControllerPlaying(new Controller(nn), seed);
	}
}