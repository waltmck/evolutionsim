import java.util.*;
import java.io.*;

public class Analysis{
    private static final int NUM_POPULATION_STORED = 100;
    private static final int MIN_POPULATION_STABLE = 100;
    private static final double MAX_STDEV_STABLE = 5;
    public static void main(String[] args) throws Exception {
        Simulation s = new Simulation();
        s.update();

        Queue<Integer> populationHistory = new LinkedList<>();
        while(true){
            s.update();
            populationHistory.add(s.numCreatures);
            int popHistorySize = populationHistory.size();
            if(popHistorySize > NUM_POPULATION_STORED){
                populationHistory.remove();
                if(stdev(populationHistory) <= MAX_STDEV_STABLE && s.numCreatures > MIN_POPULATION_STABLE){
                    writeSnapshot(s, "stable_data");
                    break;
                }
            }
        }

        Snapshot snap = readSnapshot("stable_data");
        System.out.println("SIZE: " + snap.genes.size());
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
    public static void writeSnapshot(Simulation s, String filePath) throws Exception{
        FileOutputStream fos = new FileOutputStream("../data/" + filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(new Snapshot(s));

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
    private static final int NUM_TRIALS = 1000;

    private double aggression;
    private double foodPreference;
    private double kinship;

    public BehaviorPhenotype(Genes g) {

    }
}



class Util{
    public static double dotProduct(double[][] mtx1, double[][] mtx2){
        double total = 0;

        for(int i=0; i<mtx1.length; i++){
            for(int j=0; j<mtx1[i].length; j++){
                total += mtx1[i][j]*mtx2[i][j];
            }
        }

        return total;
    }
    // only food left
    public double[][] foodTransform(double[][] in) {
        newFood = new double[3][3];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!(in[i][j] == -2)) {
                    newFood[i][j] = 0;
                } else if (in[i][j] == -2) {
                    newFood[i][j] = -2;
                }
            }
        }
        return newFood;
    }
    // only creatures left
    public double[][] creatureTransform(double[][] in) {
        newCreature = new double[3][3];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (in[i][j] == -2) {
                    newCreature[i][j] = 0;
                } else {
                    newCreature[i][j] = in[i][j];
                }
            }
        }
        return newCreature;
    }
}


