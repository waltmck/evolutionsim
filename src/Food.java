//Creature for which evolution is being simulated
public class Food implements GameObject{
    private static final int FOOD_LIFESPAN = 5;
    private int age;
    private double health;

    // Creates creature with 
    public Food(){
        age = 0;
        health = 0.2;

    }

    // Gets the offspring creature
    public GameObject getOffspring(){
        return new Food();
    }

    public GameObject getOffspring(int[] d){
        return getOffspring();
    }

    //gets the move from SensoryInput
    public Move getMove(SensoryInput input){
        return null;
    }

    //Tick updates creature, returns true if creature dies
    public boolean tick(){
        age++;
        return age>=FOOD_LIFESPAN || health<=0;
    }

    public double getSimilarity(GameObject g){
        return -2;
    }

    public double getHealth(){
        return health;
    }

    // damages creature, returning true of the damage killed it
    public boolean damage(double dmg){
        health-=dmg;
        return health<=0;
    }

    public int[] getDirection(){
        return null;
    }
    
    public double getAttack(){
        return 0;
    }

    public Appearance getAppearance(){
        return new Appearance("\u001b[32mF\u001b[37m");
    }
}