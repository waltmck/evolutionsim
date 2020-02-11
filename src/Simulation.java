import java.util.*;


public class Simulation{
	private int SIZE = 64;
	private int NUM_FOOD_INITIAL = 600;
	private int FOOD_PER_TURN = 1;
	private int NUM_CREATURE_INITIAL = 100;
	private GameObject[][] map;
	public static Random rand;
   

	public Simulation(){
		map = new GameObject[SIZE][SIZE];
		rand = new Random();
		populate();
	}

	//Fills board with food and creatures
	private void populate(){
      int creatureCount = 0;
      int foodCount = 0;
      while(creatureCount < NUM_CREATURE_INITIAL) {
         int randX = rand.nextInt(SIZE); int randY = rand.nextInt(SIZE); // gets a random (x,y)
         if (map[randY][randX] == null) { // checks if (x,y) is empty
            map[randY][randX] = new Creature(); // fills space with a new creature
            creatureCount++;
         } 
      }
      while(foodCount < NUM_FOOD_INITIAL) {
         int randX = rand.nextInt(SIZE); int randY = rand.nextInt(SIZE); // gets a random (x,y)
         if (map[randY][randX] == null) { // checks if (x,y) is empty
            map[randY][randX] = new Food(); // fills space with a new food
            foodCount++;
		 }
	  } 
	  Debug.out.println("Finished population");
	}

	public GameObject[][] getMap(){
		return map;
	}

	//Updates all objects on board in random order
	public void update(){
		Debug.out.println("updating");
		Map<int[], Move> moves = getMovesTick();

		//list of creature coords in randomized order
		List<int[]> coords = new ArrayList<int[]>(moves.keySet());

		Collections.shuffle(coords, rand);

		for(Iterator<int[]> i = coords.iterator(); i.hasNext();){
			int[] coord = i.next();
			updateCreature(coord, moves.get(coord));
		}
		Debug.out.println("done updating");
	}

	// Updates a Creature at coordinate, giving preference to that creature (it moves first in conflict)
	private void updateCreature(int[] coord, Move move){
		Creature c = (Creature) map[coord[0]][coord[1]];
		int[] direction = c.direction;
		int[] t;
		Debug.out.println("updating creature");
		switch(move){
			case FORWARD:
				t = addVectors(coord, direction);
				if(insideBounds(t)){
					GameObject targetObj = map[t[0]][t[1]];
					if(targetObj==null){
						map[t[0]][t[1]] = c;
						map[coord[0]][coord[1]] = null;
					}
					else{
						if(targetObj.damage(c.getAttack())){
							map[t[0]][t[1]] = c.getOffspring();
						}
					}
				}
			case BACK:
				t = addVectors(coord, new int[]{-direction[0], -direction[1]});
				if(insideBounds(t) && map[t[0]][t[1]]==null){
					map[t[0]][t[1]] = c;
					map[coord[0]][coord[1]] = null;
				}
			case LEFT:
				t = addVectors(coord, new int[]{-direction[1], direction[0]});
				if(insideBounds(t) && map[t[0]][t[1]]==null){
					map[t[0]][t[1]] = c;
					map[coord[0]][coord[1]] = null;
				}
			case RIGHT:
				t = addVectors(coord, new int[]{direction[1], -direction[0]});
				if(insideBounds(t) && map[t[0]][t[1]]==null){
					map[t[0]][t[1]] = c;
					map[coord[0]][coord[1]] = null;
				}
			case LEFTTURN:
				c.direction = new int[]{-direction[1], direction[0]};
			case RIGHTTURN:
				c.direction = new int[]{direction[1], -direction[0]};
		}
	}

	private int[] addVectors(int[] a, int[] b){
		assert a.length == b.length;

		int[] out = new int[a.length];
		for(int i=0; i<a.length; i++){
			out[i]=a[i]+b[i];
		}
		return out;
	}

	private boolean insideBounds(int[] coord){
		return (coord[0]>=0 && coord[0]<SIZE && coord[1]>=0 && coord[1]<SIZE);
	}

	//gets all moves, 
	private Map<int[], Move> getMovesTick(){
		Debug.out.println("getting moves tick");
		Map<int[], Move> m = new HashMap<int[], Move>();
		for (int i=0; i<SIZE; i++){
			for (int j=0; j<SIZE; j++){
				Debug.out.println("("+i+", "+j+")");
				GameObject obj = map[i][j];
				if (obj != null){
					Debug.out.println("Object not null");
					if(obj.tick()){
						//if creature dies, remove it.
						map[i][j] = null;
					}
					else if(obj instanceof Creature){
						Debug.out.println("Object is creature");
						m.put(new int[]{i, j}, obj.getMove(new SensoryInput(i, j, map))); //this is stalling
					}
				}
			}
		}
		Debug.out.println("Finished getting moves tick");
		return m;
	}
}