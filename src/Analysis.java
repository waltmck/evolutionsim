import java.util.*;
import java.io.*;

public class Analysis{
    private static final int NUM_POPULATION_STORED = 100;
    private static final double MAX_STDEV_STABLE = 0.1;
    public static void main(String[] args) throws FileNotFoundException {
        Simulation s = new Simulation();
        Queue<Integer> populationHistory = new LinkedList<>();
        while(true){
            s.update();
            populationHistory.add(s.numCreatures);
            int popHistorySize = populationHistory.size();
            if(popHistorySize > NUM_POPULATION_STORED){
                populationHistory.remove();
                if(stdev(populationHistory) <= MAX_STDEV_STABLE){
                    writeSimulation(s, "stable_data");
                    break;
                }
            }
        }
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

    //TODO @Peyton returns a behavior phenotype object from genes
    public static BehaviorPhenotype getBehaviorPhenotype(Genes g){
        return new BehaviorPhenotype();
    }

    //TODO @Walt Stores simulation object in filepath inside data directory
    public static void writeSimulation(Simulation s, String filePath){

    }

    //TODO @Walt Reads simulation object from filepath in data directory
    public static Simulation readSimulation(String filePath){
        return new Simulation();
    }
}

//TODO @Peyton describes the behavior phenotype of a creature in human readable form
class BehaviorPhenotype{
    public BehaviorPhenotype(){

    }
}