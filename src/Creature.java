import java.util.Random;

//Creature for which evolution is being simulated
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

	//gets the move from SensoryInput
	public Move getMove(SensoryInput input){
		return null;
	}

	//Gets creature stats
	public Stats getStats(){
		return null;
	}

	//Tick updates creature
	public void tick(){
		return;
	}

	//gets the genetic similarity between two objects, between 0 and 1. Returns -2 if input is a food object.
	public double getSimilarity(GameObject g){
		return 0;
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
		return direction;
	}
}