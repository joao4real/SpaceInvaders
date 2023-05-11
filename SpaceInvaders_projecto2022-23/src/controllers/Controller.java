package controllers;

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

	protected static class NeuralNetwork{

		private static final int HIDDEN_LAYER_SIZE = 100;
		private double[] array;

		protected NeuralNetwork() {
			createNewNeuralNetwork();
		}

		private void createNewNeuralNetwork() {
			array = new double[Commons.STATE_SIZE * HIDDEN_LAYER_SIZE + HIDDEN_LAYER_SIZE
					+ HIDDEN_LAYER_SIZE * Commons.NUM_ACTIONS + Commons.NUM_ACTIONS];
			generateArray(array);
		}

		private void generateArray(double[] array) {
			for (int i = 0; i < array.length; i++)
				array[i] = Math.random() * 2 - 1; // valores entre -1 e 1
		}

		private double[] forward(double[] currentState) {

		double[][] inputWeights = new double[Commons.STATE_SIZE][HIDDEN_LAYER_SIZE];
		double[] hiddenBiases = new double[HIDDEN_LAYER_SIZE];
		double[][] outputWeights = new double[HIDDEN_LAYER_SIZE][Commons.NUM_ACTIONS];
		double[] outputBiases = new double[Commons.NUM_ACTIONS];
		int x = 0;	

		for(int i = 0;i<array.length;i++) {
			
			if( i < Commons.STATE_SIZE * HIDDEN_LAYER_SIZE) {
				x = i/HIDDEN_LAYER_SIZE;
				if(i % HIDDEN_LAYER_SIZE == 0)
					inputWeights[x][0] = array[i]; //change to a new line
				else
					inputWeights[x][i - (x * HIDDEN_LAYER_SIZE)] = array[i];
			}
			if(i >= Commons.STATE_SIZE * HIDDEN_LAYER_SIZE && i < Commons.STATE_SIZE * HIDDEN_LAYER_SIZE + HIDDEN_LAYER_SIZE) 
				hiddenBiases[i - Commons.STATE_SIZE * HIDDEN_LAYER_SIZE] = array[i]; 
			if( i >= Commons.STATE_SIZE * HIDDEN_LAYER_SIZE + HIDDEN_LAYER_SIZE && i < Commons.STATE_SIZE * HIDDEN_LAYER_SIZE + HIDDEN_LAYER_SIZE
					+ HIDDEN_LAYER_SIZE * Commons.NUM_ACTIONS) {
				int offset = i - (Commons.STATE_SIZE * HIDDEN_LAYER_SIZE + HIDDEN_LAYER_SIZE);
				x = (offset) / Commons.NUM_ACTIONS;
				if(offset % Commons.NUM_ACTIONS == 0)
					outputWeights[x][0] = array[i];
				else
					outputWeights[x][offset - (x * Commons.NUM_ACTIONS)] = array[i];
			}
			if( i >= Commons.STATE_SIZE * HIDDEN_LAYER_SIZE + HIDDEN_LAYER_SIZE
					+ HIDDEN_LAYER_SIZE * Commons.NUM_ACTIONS && i < array.length)
				outputBiases[i - (array.length - Commons.NUM_ACTIONS)] = array[i];
		}
		return calculate(calculate(currentState, inputWeights, hiddenBiases), outputWeights, outputBiases);	
	}

	public double[] calculate(double[] input ,double[][] weights, double[] biases) {
	  double[] result = new double[biases.length];
	  
	  for (int j = 0; j < weights[0].length; j++) { 
		  double total = 0; 
		  for (int i = 0; i < weights.length; i++) 
			  total += weights[i][j] * input[i];
		  result[j] = 1/(1 + Math.exp(-(total + biases[j])));
		  } 
	  return result; 	  
	}

	public double[] getArray() {
		return array;
	}

	public double getFitness(long seed) {
		Board b = new Board(new Controller(this));
		b.setSeed(seed);
		b.run();
		return b.getFitness();
	}
}}
