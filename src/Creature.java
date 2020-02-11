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

	// TODO tick updates creature, returns true if creature dies (such as of old age)
	public boolean tick(){
		return health<=0 || age >= genes.getLifespan();
	}

	// TODO gets the genetic similarity between two objects, between 0 and 1. Returns -2 if input is a food object.
	public double getSimilarity(GameObject g){
		return 0;
	}

	// TODO gets health of creature
	public double getHealth(){
		return health;
	}

	// damages creature, returning true of the damage killed it
	// TODO: make this work with the health gene
	public boolean damage(double dmg){
		health-=dmg;
		return health<=0;
	}

	public int[] getDirection(){
		return direction;
	}

	public double getAttack(){
		return genes.getAttack();
	}
}