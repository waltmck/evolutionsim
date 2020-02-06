package sim.gameobjects;

import sim.Genes;

//Creature for which evolution is being simulated
public class Food implements GameObject{
	private int age;
	private double health;

	// Creates creature with 
	public Food(){
		age = 0;
	}

	//Randomly generates new Creature
	public Creature(){
		genes = new Genes();
		age = 0;
		health = 1;
		
		//randomly sets direction
		int r = new Random().nextInt(4);
		direction = new int[]{0, 1};

		for (int i=0; i<r; i++){
			direction = new int[]{-direction[1], direction[0]};
		}
	}

	// Gets the offspring creature
	public Creature getOffspring(int[] d){
		Creature c = new Creature(new Genes(genes));
		c.direction = d;
		return c;
	}

	//gets the move from SensoryInput
	public Move getMove(SensoryInput input){

	}

	//Gets creature stats
	public Stats getStats(){

	}

	//Tick updates creature
	public void tick(){

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
}