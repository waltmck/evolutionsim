import java.util.*;
import java.io.*;

public class Analysis{
    public static void main(String[] args) throws FileNotFoundException {
        Simulation s = new Simulation();
        int num = 0;
        while(true){
            s.update();
            num = s.numCreatures;
        }
    }

    //TODO @Peyton returns a behavior phenotype object from genes
    public BehaviorPhenotype getBehaviorPhenotype(Genes g){
        return new BehaviorPhenotype();
    }
}

//TODO @Peyton describes the behavior phenotype of a creature in human readable form
class BehaviorPhenotype{
    public BehaviorPhenotype(){

    }
}