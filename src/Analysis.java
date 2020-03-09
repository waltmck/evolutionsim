import java.util.*;
import java.io.*;

public class Analysis{
    private static final int NUM_POPULATION_STORED = 1000;
    private static final int MIN_POPULATION_STABLE = 500;
    private static final double MAX_STDEV_PERC_STABLE = 0.04;
    public static void main(String[] args) throws Exception {
        for(int trial=0; trial<100; trial++){
            Simulation s = new Simulation();
            s.update();
            Scanner console = new Scanner(System.in);
            /* System.out.print("aggression output file name? ");
            PrintStream aggressionOutput = new PrintStream(new File(console.nextLine()));
            System.out.print("food preference output file name? ");
            PrintStream foodPreferenceOutput = new PrintStream(new File(console.nextLine()));
            System.out.print("kinship output file name? ");
            PrintStream kinshipOutput = new PrintStream(new File(console.nextLine())); */
    
            PrintStream aggressionOutput = new PrintStream(new File("../data/trial"+trial+"_aggression"));
            PrintStream foodPreferenceOutput = new PrintStream(new File("../data/trial"+trial+"_food"));
            PrintStream kinshipOutput = new PrintStream(new File("../data/trial"+trial+"_kin"));

            PrintStream attackOutput = new PrintStream(new File("../data/trial"+trial+"_attack"));;
            PrintStream defenseOutput = new PrintStream(new File("../data/trial"+trial+"_defense"));;
            PrintStream healthOutput = new PrintStream(new File("../data/trial"+trial+"_health"));;
            PrintStream regenOutput = new PrintStream(new File("../data/trial"+trial+"_regen"));;
    
            Queue<Integer> populationHistory = new LinkedList<>();
            while (true) {
                s.update();
                populationHistory.add(s.numCreatures);
                int popHistorySize = populationHistory.size();
                if (popHistorySize > NUM_POPULATION_STORED) {
                    populationHistory.remove();
                    if (stdev(populationHistory) <= MAX_STDEV_PERC_STABLE*average(populationHistory) && s.numCreatures > MIN_POPULATION_STABLE) {
                        writeSnapshot(new Snapshot(s), "trial"+trial+"_snapshot");
                        break;
                    }
                }
            }
    
            Snapshot snap = readSnapshot("trial"+trial+"_snapshot");
            for (Genes g : snap.genes) {
                BehaviorPhenotype creature = new BehaviorPhenotype(g);
                aggressionOutput.println(creature.getAggression());
                foodPreferenceOutput.println(creature.getFoodPreference());
                kinshipOutput.println(creature.getKinship());
                regenOutput.println(g.getRegen());
                attackOutput.println(g.getAttack());
                defenseOutput.println(g.getDefense());
                healthOutput.println(g.getHealth());

            }
            System.out.println("Trial "+trial+" Snapshot with SIZE: " + snap.genes.size());
        }
    }

    public static double average(Queue<Integer> q){
        int length = q.size();
        double sum = 0;
        for(int i=0; i<length; i++){
            int number = q.remove();
            sum += number;
            q.add(number);
        }

        return sum/length;
    }

    //Takes standard deviation of queue, leaves it as it was
    public static double stdev(Queue<Integer> q){
        int length = q.size();

        double sum = 0.0;
        for(int i=0; i<length; i++){
            int number = q.remove();
            sum+=number;
            q.add(number);
        }

        double mean = sum/length;

        double sumDiffSquared = 0.0;
        for(int i=0; i<length; i++){
            int number = q.remove();
            sumDiffSquared += Math.pow((double) number - mean, 2);
            q.add(number);
        }

        return Math.sqrt(sumDiffSquared/length);
    }



    // Stores simulation object in filepath inside data directory
    public static void writeSnapshot(Snapshot s, String filePath) throws Exception{
        FileOutputStream fos = new FileOutputStream("../data/" + filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(s);

        fos.close();
        oos.close();
    }

    // Reads snapshot object from filepath in data directory
    public static Snapshot readSnapshot(String filePath) throws Exception{
        FileInputStream fis = new FileInputStream("../data/" + filePath);
        ObjectInputStream ois = new ObjectInputStream(fis);

        // Method for de-serialization of B's class object
        return (Snapshot) ois.readObject();
    }


}

class BehaviorPhenotype {
    private static final int NUM_TRIALS = 10000;

    private double aggression;
    private double foodPreference;
    private double kinship;

    private static transient final double AGG_NORMALIZATION = 0.4449987633;
    private static transient final double FOOD_NORMALIZATION = 0.003179774609;
    private static transient final double KIN_NORMALIZATION = -0.00003974444966;

    public BehaviorPhenotype(Genes g) {
        aggression = 0; // postive is more aggressive, negative is more passive
        foodPreference = 0; // Positive is herbavore and Negative is Carnivore
        kinship = 0; // positive means will go away from similar, negative means will go towards
        for (int i = 0; i < NUM_TRIALS; i++) {
            double[][] boardState = getInput();
            double[][] foodState = foodTransform(boardState);
            double[][] kinState = kinTransform(boardState);
            double[][] foeState = foeTransform(boardState);
            double moveMade = g.getMoveAnalysis(boardState);
            if (moveMade == 0) {
                foodPreference += Util.dotProduct(Util.forwardStrafeMatrix, foodState);
                if (boardState[1][1] == 0) {
                    aggression += Util.dotProduct(Util.forwardStrafeMatrix, foeState);
                    kinship -= Util.dotProduct(Util.forwardStrafeMatrix, kinState);
                } else {
                    aggression += Util.dotProduct(Util.forwardAttackMatrix, foeState);
                    kinship += Util.dotProduct(Util.forwardAttackMatrix, kinState);
                }
            } else if (moveMade == 1) {
                foodPreference += Util.dotProduct(Util.backStrafeMatrix, foodState);
                aggression += Util.dotProduct(Util.backStrafeMatrix, foeState);
                kinship += Util.dotProduct(Util.backStrafeMatrix, kinState);
            } else if (moveMade == 2) {
                foodPreference += Util.dotProduct(Util.leftStrafeMatrix, foodState);
                aggression += Util.dotProduct(Util.leftStrafeMatrix, foeState);
                kinship += Util.dotProduct(Util.leftStrafeMatrix, kinState);
            } else if (moveMade == 3) {
                foodPreference += Util.dotProduct(Util.rightStrafeMatrix, foodState);
                aggression += Util.dotProduct(Util.rightStrafeMatrix, foeState);
                kinship += Util.dotProduct(Util.rightStrafeMatrix, kinState);
            } else if (moveMade == 4) {
                foodPreference += Util.dotProduct(Util.leftTurnMatrix, foodState);
                aggression += Util.dotProduct(Util.leftTurnMatrix, foeState);
                kinship += Util.dotProduct(Util.leftTurnMatrix, kinState);
            } else {
                foodPreference += Util.dotProduct(Util.rightTurnMatrix, foodState);
                aggression += Util.dotProduct(Util.rightTurnMatrix, foeState);
                kinship += Util.dotProduct(Util.rightTurnMatrix, kinState);
            }
        }
        aggression = aggression / NUM_TRIALS - AGG_NORMALIZATION;
        foodPreference = foodPreference / NUM_TRIALS - FOOD_NORMALIZATION;
        kinship = kinship / NUM_TRIALS - KIN_NORMALIZATION;
    }

    public double[][] getInput() {
        double inputMatrix[][] = new double[3][3];
        Random rand = new Random();
        for (int i = 0; i < 3; i++ ) {
            for (int j = 0; j < 3; j++) {
                if ((i == 2) && (j == 1)) {
                    inputMatrix[i][j] = rand.nextDouble();
                } else {
                    if (rand.nextDouble() - 0.5 > 0) {
                        if (rand.nextDouble() - 0.5 > 0) {
                            inputMatrix[i][j] = rand.nextDouble() + 1;
                        } else {
                            inputMatrix[i][j] = -2;
                        }
                    } else {
                        inputMatrix[i][j] = 0;
                    }
                }
            }
        }
        return inputMatrix;
    }
    public double getAggression() {
        return aggression;
    }
    public double getFoodPreference() {
        return foodPreference;
    }
    public double getKinship() {
        return kinship;
    }
    // food becomes - 1 and creatures 1
    public double[][] foodTransform(double[][] in) {
        double[][] newFood = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!(in[i][j] == -2) && !(in[i][j] == 0)) {
                    newFood[i][j] = 1;
                } else if (in[i][j] == -2) {
                    newFood[i][j] = -1;
                }
            }
        }
        return newFood;
    }
    // only creatures left
    public double[][] kinTransform(double[][] in) {
        double[][] newCreature = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((in[i][j] == -2) || (in[i][j] == 0)) {
                    newCreature[i][j] = 0;
                } else {
                    newCreature[i][j] = in[i][j] - 1.5;
                }
            }
        }
        return newCreature;
    }
    public double[][] foeTransform(double[][] in) {
        double[][] newCreature = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((in[i][j] == -2) || (in[i][j] == 0)) {
                    newCreature[i][j] = 0;
                } else {
                    newCreature[i][j] = -1;
                }
            }
        }
        return newCreature;
    }
}



class Util{
    public static final double[][] backStrafeMatrix = {
        {1, 1, 1},
        {1, 1, 1},
        {0, 0, 0}
    };

    public static final double[][] leftTurnMatrix = {
        { 0, 1, 1},
        { 0, 1, 1},
        {-1, 0, 1}
    };

    public static final double[][] rightTurnMatrix = {
        {1, 1,  0},
        {1, 1,  0},
        {1, 0, -1}
    };

    public static final double[][] forwardStrafeMatrix = {
        {-1, -1, -1},
        { 0,  0,  0},
        { 1,  0,  1}
    };

    public static final double[][] forwardAttackMatrix = {
        {0,  0, 0},
        {0, -5, 0},
        {0,  0, 0}
    };

    public static final double[][] leftStrafeMatrix = {
        {-1, 1, 1},
        {-1, 1, 1},
        { 0, 0, 1}
    };

    public static final double[][] rightStrafeMatrix = {
        {1, 1, -1},
        {1, 1, -1},
        {1, 0,  0}
    };

    public static double dotProduct(double[][] mtx1, double[][] mtx2){
        double total = 0;

        for(int i=0; i<mtx1.length; i++){
            for(int j=0; j<mtx1[i].length; j++){
                total += mtx1[i][j]*mtx2[i][j];
            }
        }

        return total;
    }
}