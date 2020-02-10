package sim;

import java.util.Random;
import sim.util.Move;
import sim.gameobjects.Creature;
import sim.SensoryInput;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Iterator;

public class Simulation{
	private final int SIZE = 128;
	private final int NUM_FOOD_INITIAL = 1000;
	private final int FOOD_PER_TURN = 50;
	private final int NUM_CREATURE_INITIAL = 2000;
	private GameObject[][] map;
	private Random rand;
   

	public Simulation(){
		map = new GameObject[SIZE][SIZE];
		rand = new Random();
	}

	//Fills board with food and creatures
	public void populate(GameObject[][] map){
      int creatureCount = 0;
      int foodCount = 0;
      while(creatureCount < NUM_CREATURE_INITIAL) {
         int randX = rand.nextInt(128); int randY = rand.nextInt(128); // gets a random (x,y)
         if (map[randY][randX] == null) { // checks if (x,y) is empty
            map[randY][randX] = new Creature(new Genes()); // fills space with a new creature
            creatureCount++;
         } 
      }
      while(foodCount < NUM_FOOD_INITIAL) {
         int randX = rand.nextInt(128); int randY = rand.nextInt(128); // gets a random (x,y)
         if (map[randY][randX] == null) { // checks if (x,y) is empty
            map[randY][randX] = new Food(); // fills space with a new food
            foodCount++;
         } 
      } 
	}

	//Updates all objects on board in random order
	public void update(){
		Map<int[], Move> moves = getMovesTick();

		//list of creature coords in randomized order
		List<int[]> coords = Collections.shuffle(new ArrayList<int[]>(moves.keySet()), rand);

		for(Iterator<int[]> i = coords.iterator(); i.hasNext();){
			int[] coord = i.next();
			update(coord, moves.get(coord));
		}
            	
	}

	// updates a coordinate, giving preference to that coordinate
	public void update(int[] coord, Move m){

	}

	//gets all moves, 
	public Map<int[], Move> getMovesTick(){
		Map<int[], Move> m = new HashMap<int[], Move>();
		for (int i=0; i<SIZE; i++){
			for (int j=0; j<SIZE; j++){
				GameObject obj = map[i][j];
				if (obj != null){
					if(obj instanceof Creature){
						moves.put(new int[]{i, j}, obj.getMove(new SensoryInput(i, j, map)));
					}
					obj.tick();
				}
			}
		}
		return m;
	}
}