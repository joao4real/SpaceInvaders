package controllers;

import space.Board;

public class Main {

	public static void main(String[] args) {
		
	GeneticAlgorithm ga = new GeneticAlgorithm();
	ga.getPopulation().forEach(g -> new Board(new Controller(g)).run());
	}
	
}
