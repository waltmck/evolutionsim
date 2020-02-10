package sim;

import java.util.Random;

public class Genes{
	private final int NUM_HIDDEN_LAYERS = 3; // must be 1 or greater
   
   private double[][] weightsHidden; // [3][3]
   private double[] weightsPenultimate; // [3]
   private double[][] weightsLast; // [3][6]
   private double[][][] matrix;


	//Randomly generate genes
	public Genes(){
	   Random rand = new Random();
	   for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            weightsHidden[i][j] = 10 * rand.nextDouble() - 5;
         }
	   }
	   for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 6; j++) {
            weightsLast[i][j] = 10 * rand.nextDouble() - 5;
         }
	   }
	   for (int i = 0; i < 3; i++) {
         weightsPenultimate[i] = 10 * rand.nextDouble() - 5;
	   }
	}

	//Generates mutated genes from parent genes
	public Genes(Genes parent){
      Random rand = new Random();
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            weightsHidden[i][j] += rand.nextDouble() - 0.5;
         }
      }
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 6; j++) {
            weightsLast[i][j] += rand.nextDouble() - 0.5;
         }
      }
      for (int i = 0; i < 3; i++) {
         weightsPenultimate[i] += rand.nextDouble() - 0.5;
      }
	}
   
   //Generates a move based off of a neural network with an input of SensoryInput and weighted by genes
   public Move getMove(SensoryInput input) {
      double inputLayer[][] = input; // input layer
      double hiddenLayer[][] = new double[3][3]; // any of the hidden layers (except penultimate)
      double hiddenWeight[][] = weightsHidden; // all of hiddenLayer weights
      for (int layer = 0; layer < NUM_HIDDEN_LAYERS; layer++) {
         if (layer == 0) { // for first hidden layer
            for (int i = 0; i < 3; i++) {
               for (int j = 0; j < 3; j++) {
                  int total = 0;
                  for (int k = 0; k < 3; k++) {
                     total += (inputLayer[i][k] * hiddenWeight[k][j]);
                  }
               hiddenLayer[i][j] = total;   
               }
            }
         } else { // for other hidden layers
            double nextLayer[][] = new double[3][3]; // storage space for the next hidden layer
            for (int i = 0; i < 3; i++) {
               for (int j = 0; j < 3; j++) {
                  int total = 0;
                  for (int k = 0; k < 3; k++) {
                     total += (hiddenLayer[i][k] * hiddenWeight[k][j]);
                  }
               nextLayer[i][j] = total;   
               }
            }
            for (int i = 0; i < 3; i++) {
               for (int j = 0; j < 3; j++) {
                  hiddenLayer[i][j] = nextLayer[i][j];
               }
            }
         }
      }
      double hiddenWeightPenultimate[] = weightsPenultimate; // input to hidden layer 1 weights
      double hiddenLayerPenultimate[] = new double[3]; // initial hidden layer 1 before moving values
      for (int i = 0; i < 3; i++) { 
         double total = 0;
         for (int j = 0; j < 3; j++) { 
            total += (hiddenLayer[i][j] * hiddenWeightPenultimate[i]);
         }
         hiddenLayerPenultimate[i] = total; 
      }
      double hiddenWeightLast[][] = weightsLast;
      double output[] = new double[6];
      for (int i = 0; i < 6; i++) {
         int total = 0;
         for (int j = 0; j < 3; j++) {
            total += (hiddenWeightLast[j][i] * hiddenLayerPenultimate[j]);
         }
      output[i] = total;  
      } 
   }
}