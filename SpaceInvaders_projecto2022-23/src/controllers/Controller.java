package controllers;

import java.util.Random;

import space.Board;
import space.Commons;

public class Controller implements GameController {

	private NeuralNetwork nn;

	public Controller(NeuralNetwork nn) {
		this.nn = nn;
	}

	@Override
	public double[] nextMove(double[] currentState) {
		return nn.forward(currentState);
	}

	public NeuralNetwork getNeuralNetwork() {
		return nn;
	}

	protected static class NeuralNetwork implements Comparable<NeuralNetwork> {

		private static final int HIDDEN_LAYER_SIZE = 50;

		private double[] array = new double[HIDDEN_LAYER_SIZE * (Commons.STATE_SIZE + Commons.NUM_ACTIONS + 1) + Commons.NUM_ACTIONS];
		private double fitness;

		protected NeuralNetwork() {
			generateWeights(array);
		}

		private void generateWeights(double[] array) {
			for (int i = 0; i < array.length; i++)
				array[i] = (Math.random() * 6) - 3; // valores entre -3 e 3
		}

		public double[] forward(double[] input) {

			double[] hidden = new double[HIDDEN_LAYER_SIZE];
			double[] output = new double[Commons.NUM_ACTIONS];

			int inputOffset = 0;
			int hiddenOffset = Commons.STATE_SIZE * HIDDEN_LAYER_SIZE;
			int outputOffset = hiddenOffset + HIDDEN_LAYER_SIZE;

			// Input Weights
			for (int i = 0; i < Commons.STATE_SIZE; i++)
				for (int j = 0; j < HIDDEN_LAYER_SIZE; j++)
					hidden[j] += input[i] * array[inputOffset++];

			// Hidden Layer Activation
			for (int i = 0; i < HIDDEN_LAYER_SIZE; i++)
				hidden[i] = 1 / (1 + Math.exp(-hidden[i] - array[hiddenOffset + i]));

			// Output Weights
			for (int i = 0; i < HIDDEN_LAYER_SIZE; i++)
				for (int j = 0; j < Commons.NUM_ACTIONS; j++)
					output[j] += hidden[i] * array[outputOffset++];

			// Output Layer Activation
			for (int i = 0; i < Commons.NUM_ACTIONS; i++)
				output[i] = 1 / (1 + Math.exp(-output[i] - array[outputOffset + i]));

			return output;
		}

		public static NeuralNetwork crossOver(NeuralNetwork nn1, NeuralNetwork nn2) {
			NeuralNetwork nn = new NeuralNetwork();
			int pointer = GeneticAlgorithm.getRandom(nn1.getArray().length);
			
			for (int i = 0; i < nn1.array.length; i++)
				nn.array[i] = (i < pointer) ? nn1.array[i] : nn2.array[i];
			
			return nn;
		}

		public void mutation() {
			int x = 0;
			if (Math.random() <= GeneticAlgorithm.MUTATION_ODD) {
				while (x < GeneticAlgorithm.NUM_OF_CHANGES) {
					array[GeneticAlgorithm.getRandom(array.length)] = (new Random().nextDouble() * 6) - 3;
					x++;
				}
			}
		}

		public void evaluate(long seed) {
			Board b = new Board(new Controller(this));
			b.setSeed(seed);
			b.run();
			fitness = b.getFitness();
		}

		public double[] getArray() {
			return array;
		}

		public double getFitness() {
			return fitness;
		}

		@Override
		public int compareTo(NeuralNetwork nn) {
			return (int) (nn.fitness - this.fitness);
		}
	}
}
