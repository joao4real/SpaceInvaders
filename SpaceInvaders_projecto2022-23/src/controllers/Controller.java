package controllers;

import space.Board;
import space.Commons;
import space.SpaceInvaders;

public class Controller implements GameController {

	private NeuralNetwork nn;

	public Controller(NeuralNetwork nn) {
		this.nn = nn;
	}

	public static void main(String[] args) {
		GameController g = new Controller(new NeuralNetwork());
		SpaceInvaders.showControllerPlaying(g, 5);
	}

	@Override
	public double[] nextMove(double[] currentState) {
		//return nn.forward(currentState);	
		double[] x = {1,2,3,4,5.4,3.2};
		return x;
	}

	public NeuralNetwork getNeuralNetwork() {
		return nn;
	}

	protected static class NeuralNetwork {

		private static final int HIDDEN_LAYER_SIZE = 100;
		private double[] array;
		
		protected NeuralNetwork() {
			createNewNeuralNetwork();
		}

		private void createNewNeuralNetwork() {
			
			array = new double[HIDDEN_LAYER_SIZE + Commons.STATE_SIZE*HIDDEN_LAYER_SIZE + Commons.NUM_ACTIONS + HIDDEN_LAYER_SIZE*Commons.NUM_ACTIONS];
			
			generateArray(array);
		}

		private void generateMatrix(double[][] matrix) {
			for (int j = 0; j < matrix.length; j++)
				for (int i = 0; i < matrix[0].length; i++)
					matrix[j][i] = Math.random() * 20 - 10; // valores entre -10 e 10
		}

		private void generateArray(double[] array) {
			for (int i = 0; i < array.length; i++)
				array[i] = Math.random() * 20 - 10; // valores entre -10 e 10
		}

		/*private double[] calculate(double[] firstValues, double[][] weights, double[] biases, boolean sigmoid) {

			double[] result = new double[biases.length];

			for (int j = 0; j < weights[0].length; j++) {
				double total = 0;
				for (int i = 0; i < weights.length; i++)
					total += weights[i][j] * firstValues[i];
				result[j] = (sigmoid) ? 1 / (1 + Math.exp(-(total + biases[j]))) : total + biases[j];
			}
			return result;
		}

		private double[] forward(double[] firstValues) {
			return calculate(calculate(firstValues, inputWeights, hiddenBiases, false), outputWeights, outputBiases, false);
		}
	}
		
		private double[] forward() {
		
			*/
		
		public Double getFitness() {
			Board b = new Board(new Controller(this));
			return b.getFitness();
		}

		public double[] getArray() {
			return array;
		}
	}
}
