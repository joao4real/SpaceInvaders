package controllers;

import space.Commons;
import space.SpaceInvaders;

public class Controller implements GameController{

	private NeuralNetwork nn;
	
	public Controller(NeuralNetwork nn) {
		this.nn = nn;
	}

	public static void main(String[] args) {
		GameController c = new Controller(new NeuralNetwork());
		SpaceInvaders.showControllerPlaying(c,5);
	}
	
	@Override
	public double[] nextMove(double[] currentState) {
		return nn.calculate(nn.calculate(currentState, nn.inputWeights, nn.hiddenBiases, false), nn.outputWeights, nn.outputBiases, false);
	}

	
	public NeuralNetwork getNeuralNetwork() {
		return nn;
	}

	private static class NeuralNetwork {
		
		private static final int HIDDEN_LAYER_SIZE = 100;
		private double[][] inputWeights;
		private double[] hiddenBiases;
		private double[][] outputWeights;
		private double[] outputBiases;

		private NeuralNetwork() {
			createNewNeuralNetwork();
		}

		private void createNewNeuralNetwork() {

			hiddenBiases = new double[HIDDEN_LAYER_SIZE];
			inputWeights = new double[Commons.STATE_SIZE][HIDDEN_LAYER_SIZE];
			outputWeights = new double[HIDDEN_LAYER_SIZE][Commons.NUM_ACTIONS];
			outputBiases = new double[Commons.NUM_ACTIONS];

			generateMatrix(inputWeights);
			generateMatrix(outputWeights);
			generateArray(hiddenBiases);
			generateArray(outputBiases);
		}
		
		private void generateMatrix(double[][] matrix) {
			for (int j = 0; j < matrix.length; j++)
				for (int i = 0; i < matrix[0].length; i++)
					matrix[j][i] = Math.random()*20 - 10; // valores entre -10 e 10
		}

		private void generateArray(double[] array) {
			for (int i = 0; i < array.length; i++) 
				array[i] = Math.random()*20 - 10; // valores entre -10 e 10
		}

		private double[] calculate(double[] firstValues, double[][] weights, double[] biases, boolean sigmoid) {

			double[] result = new double[biases.length];

			for (int j = 0; j < weights[0].length; j++) {
				double total = 0;
				for (int i = 0; i < weights.length; i++)
					total += weights[i][j] * firstValues[i];
				result[j] = (sigmoid) ? 1 / (1 + Math.exp(-(total + biases[j]))) : total + biases[j];
			}
			return result;
		}
	}

}

