import java.util.Random;

//Creature for which evolution is being simulated

// TODO implement health using genes
public class Creature implements GameObject{
    private Genes genes;
    private int age;
    private double health; //between 0 and 1

    public int[] direction;

    // Set genes, set direction
    public Creature(Genes g, int[] d){
        genes = g;
        age = 0;
        health = 1;
        direction = d;
    }

    // Random genes, random direction
    public Creature(){
        genes = new Genes();
        age = 0;
        health = 1;
        
        //randomly sets direction
        int r = Simulation.rand.nextInt(4);
        direction = new int[]{0, 1};

        for (int i=0; i<r; i++){
            direction = new int[]{-direction[1], direction[0]};
        }
    }

    // Gets the offspring creature based on desired direction
    public Creature getOffspring(int[] d){
        return new Creature(new Genes(genes), d);
    }

    public Creature getOffspring(){
        //randomly sets direction
        int r = Simulation.rand.nextInt(4);
        direction = new int[]{0, 1};

        for (int i=0; i<r; i++){
            direction = new int[]{-direction[1], direction[0]};
        }

        return new Creature(new Genes(genes), direction);

    }

    // TODO get move based on genes
    public Move getMove(SensoryInput input){
        Move out = genes.getMove(input);
        return out;
    }


    public boolean tick(){
        if (health <= 0){
            return true;
        }
        age++;
        health = Math.min(health+genes.getRegen(), 1);
        return age >= genes.getLifespan();
    }

    // TODO gets the genetic similarity between two objects, between 0 and 1. Returns -2 if input is a food object.
    public double getSimilarity(GameObject g){
        if(g instanceof Food){
            return -2;
        } else if(g instanceof Creature) {
            return Math.min(1, genes.getSimilarity(((Creature) g).getGenes()));
        } else{
            throw new RuntimeException("Unknown GameObject type");
        }
    }


    public double getHealth(){
        return health;
    }

    // damages creature, returning true of the damage killed it

    public boolean damage(double dmg){
        dmg -= genes.getDefense();
        dmg = Math.min(0, dmg);
        health-=dmg;
        return health<=0;
    }

    public int[] getDirection(){
        return direction;
    }

    public double getAttack(){
        return genes.getAttack();
    }

    public Appearance getAppearance(){
        int color = genes.getLargest();
        if (color == 0) {
            return new Appearance("\u001b[36mC\u001b[37m");
        } else if (color == 1) {
            return new Appearance("\u001b[34mC\u001b[37m");
        } else if (color == 2) {
            return new Appearance("\u001b[31mC\u001b[37m");
        } else if (color == 3) {
            return new Appearance("\u001b[35mC\u001b[37m");
        } else {
            return new Appearance("\u001b[33mC\u001b[37m");
        }
    }

    public Genes getGenes(){
        return genes;
    }
}