package sim;

import java.util.Random;

public class Genes{
   // genes
   private final int NUM_HIDDEN_LAYERS = 3; // must be 1 or greater
   // stats
   private final double MUTATION_FACTOR = 0.2;

   // genes
   private double[][] weightsHidden; // [3][3]
   private double[] weightsPenultimate; // [3]
   private double[][] weightsLast; // [3][6]
   // stats
   private double health; // how much hp the critter has
   private double defense; // how much the attack recieved is lowered by
   private double attack; // how much damage the critter does
   private double regen; // how much hp the critter regenerates per turn
   private double lifespan; // how long it will live
   private double totalStats; // total points available to be distributed

   // Randomly generate genes
   public Genes(){
      Random rand = new Random();
      // randomized neural network weights
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            weightsHidden[i][j] = rand.nextDouble() - 0.5;
         }
      }
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 6; j++) {
            weightsLast[i][j] = rand.nextDouble() - 0.5;
         }
      }
      for (int i = 0; i < 3; i++) {
         weightsPenultimate[i] = rand.nextDouble() - 0.5;
      }
      // randomized starting stats
      health = rand.nextDouble();
      defense = rand.nextDouble();
      attack = rand.nextDouble();
      regen = rand.nextDouble();
      lifespan = rand.nextDouble();

      // conversion to proportion
      totalStats = health + defense + attack + regen + lifespan;
      health = health / totalStats;
      defense = defense / totalStats;
      attack = attack / totalStats;
      regen = regen / totalStats;
      lifespan = lifespan / totalStats;
	}

	//Generates mutated genes from parent genes
    public Genes(Genes parent){
       Random rand = new Random();
       // transferring traits
       weightsHidden = Arrays.copyOf(parent.weightsHidden);
       weightsPenultimate = Arrays.copyOf(parent.weightsPenultimate);
       weightsLast = Arrays.copyOf(parent.weightsLast);
       // neural network weights mutation
       for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            weightsHidden[i][j] += MUTATION_FACTOR * (rand.nextDouble() - 0.5);
         }
       }
       for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 6; j++) {
            weightsLast[i][j] += MUTATION_FACTOR * (rand.nextDouble() - 0.5);
         }
       }
       for (int i = 0; i < 3; i++) {
         weightsPenultimate[i] += MUTATION_FACTOR * (rand.nextDouble() - 0.5);
       }
       // stats mutation
       health = parent.health + MUTATION_FACTOR * (rand.nextDouble() - 0.1);
       defense = parent.defense + MUTATION_FACTOR * (rand.nextDouble() - 0.1);
       attack = parent.attack + MUTATION_FACTOR * (rand.nextDouble() - 0.1);
       regen = parent.regen + MUTATION_FACTOR * (rand.nextDouble() - 0.1);
       lifespan = parent.lifespan + MUTATION_FACTOR * (rand.nextDouble() - 0.1);
       // get rid of negative stats
       if (health < 0) {
          health = 0;
       } if (defense < 0) {
          defense = 0;
       } if (attack < 0) {
          attack = 0;
       } if (regen < 0) {
          regen = 0;
       } if (lifespan < 0) {
          lifespan = 0;
       }
       // will covert to a proportion and multiply to get it's numerical stat
       totalStats = health + defense + attack + regen + lifespan;
       health = health / totalStats;
       defense = defense / totalStats;
       attack = attack / totalStats;
       regen = regen / totalStats;
       lifespan = lifespan / totalStats;
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
      double greatestValue = output[0];
      int getMove = 0;
      for (int i = 1; i < 6; i++) {
         if (greatestValue > output[i]) {
            greatestValue = output[i];
            getMove = i;
         }
      }
      if (getMove = 0) {
         return FORWARD;
      } else if (getMove = 1) {
         return BACK;
      } else if (getMove = 2) {
         return LEFT;
      } else if (getMove = 3) {
         return RIGHT;
      } else if (getMove = 4) {
         return LEFTTURN;
      } else {
         return RIGHTTURN;
      }
   }
}