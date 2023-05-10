package controllers;
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

	@Override
	public void giveFitnessValue(double fitness) {
		nn.fitness = fitness;
	}
	
	public NeuralNetwork getNeuralNetwork() {
		return nn;
	}

	protected static class NeuralNetwork {

		private static final int HIDDEN_LAYER_SIZE = 100;
		private double[] array;
		private double fitness;
		
		protected NeuralNetwork() {
			createNewNeuralNetwork();
		}

		private void createNewNeuralNetwork() {
			array = new double[ Commons.STATE_SIZE*HIDDEN_LAYER_SIZE + HIDDEN_LAYER_SIZE + HIDDEN_LAYER_SIZE*Commons.NUM_ACTIONS + Commons.NUM_ACTIONS];
			generateArray(array);
		}

		private void generateArray(double[] array) {
			for (int i = 0; i < array.length; i++)
				array[i] = Math.random() * 100000; // valores entre 0 e 100000
		}

//		private double[] calculate(double[] array) {

//			double[] result = new double[biases.length];

			/*for (int j = 0; j < weights[0].length; j++) {
				double total = 0;
				for (int i = 0; i < weights.length; i++)
					total += weights[i][j] * firstValues[i];
				result[j] =  total + biases[j];
			}
			return result;
		}
			*/
		
//		private double[] forward(double[] currentState) {
		
		public double[] getArray() {
			return array;
		}

		public double getFitness() {
			return fitness;
		}
	}

	
}
