import java.util.*;

public class Genes{
    // genes
    private final int NUM_HIDDEN_LAYERS = 10; // must be 1 or greater
    // stats
    private final double MUTATION_FACTOR = 0.01;
    
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
    
    //Randomly generate genes
    public Genes(){
        weightsHidden = new double[3][3];
        weightsPenultimate = new double[3];
        weightsLast = new double[3][6];
        Random rand = Simulation.rand;
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
        
        health = rand.nextDouble();
        defense = rand.nextDouble();
        attack = rand.nextDouble();
        regen = rand.nextDouble();
        lifespan = rand.nextDouble();
        // will covert to a proportion and multiply to get it's numerical stat
        totalStats = health + defense + attack + regen + lifespan;
        health = health / totalStats;
        defense = defense / totalStats;
        attack = attack / totalStats;
        regen = regen / totalStats;
        lifespan = lifespan / totalStats;
    }
    
    //Generates mutated genes from parent genes
    public Genes(Genes parent){
        Random rand = Simulation.rand;
        // transferring traits
        weightsHidden = Arrays.copyOf(parent.weightsHidden, parent.weightsHidden.length);
        weightsPenultimate = Arrays.copyOf(parent.weightsPenultimate, parent.weightsPenultimate.length);
        weightsLast = Arrays.copyOf(parent.weightsLast, parent.weightsLast.length);
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
        double inputLayer[][] = input.vision; // input layer
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
        if (getMove == 0) {
            return Move.FORWARD;
        } else if (getMove == 1) {
            return Move.BACK;
        } else if (getMove == 2) {
            return Move.LEFT;
        } else if (getMove == 3) {
            return Move.RIGHT;
        } else if (getMove == 4) {
            return Move.LEFTTURN;
        } else {
            return Move.RIGHTTURN;
        }
    }
    
    public double getAttack(){
        return attack;
    }

    public double getHealth() {
        return health;
    }

    public double getRegen() {
        return regen;
    }

    public double getDefense() {
        return defense;
    }

    public int getLargest() { // 0 = health, 1 = defense, 2 = attack, 3 = regen, 4 = lifespan
        if ((health >= defense) && (health >= attack) && (health >= regen) && (health >= lifespan)) {
            return 0;
        } else if ((defense >= health) && (defense >= attack) && (defense >= regen) && (defense >= lifespan)) {
            return 1;
        } else if ((attack >= health) && (attack >= defense) && (attack >= regen) && (attack >= lifespan)) {
            return 2;
        } else if ((regen >= health) && (regen >= defense) && (regen >= attack) && (regen >= lifespan)) {
            return 3;
        } else {
            return 4;
        }
    }

    public int getLifespan(){
        return (int)(lifespan*300);
    }

    //Gets the genetic similarity between 0 and 1
    public double getSimilarity(Genes g2){
        return Math.sqrt((Math.pow((attack - g2.attack),2)+ Math.pow((defense - g2.defense),2) + Math.pow((health - g2.health),2)
                + Math.pow((regen - g2.regen),2) + Math.pow((lifespan - g2.lifespan),2)));
    }
}