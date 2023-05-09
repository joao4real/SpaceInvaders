package controllers;

import java.util.Arrays;

public class Individual {

	public static final int SIZE = 0;
	public static final int MAX_FITNESS = 100000;

	private int[] elements;
	private int fitness;
	
	public Individual() {
		this.elements = new int[SIZE];
        this.fitness = 0;
	}
	
	public Individual(int[] elements) {
        this.elements = elements;
        this.fitness = 0;
    }

    public int[] getElements() {
        return elements;
    }
	
	public static Object lowerFitness(Individual individual, Individual individual2) {
		return (individual.calculateFitness() <= individual2.calculateFitness()) ? individual : individual2;
	}

	public static Individual childrenOf(Individual individual, Individual individual2) {
		return individual;
	}

	public int[] getArrayOfElements() {
		return elements;
	}

	public void setArrayOfElements(int[] elements) {
		this.elements = elements;
	}

	public int calculateFitness() {
		return fitness;
    }
	

	public static int getMax_fitness() {
		return MAX_FITNESS;
	}
	
	@Override
    public String toString() {
        return Arrays.toString(elements);
    }

}
