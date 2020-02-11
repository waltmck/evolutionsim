//Creature for which evolution is being simulated
public class Food implements GameObject{
	private int age;
	private double health;

	// Creates creature with 
	public Food(){
		age = 0;
		health = 1;
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
		return health>0 && age<30;
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
}