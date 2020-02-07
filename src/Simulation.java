package sim;

import java.util.Random;
import sim.util.Move;
import sim.gameobjects.Creature;
import sim.SensoryInput;

public class Simulation{
	private final int SIZE = 128;
	private final int NUM_FOOD_INITIAL = 1000;
   private final int FOOD_PER_TURN = 50;
   private final int NUM_CREATURE_INITIAL = 2000;
	private GameObject[][] map;
   

	public Simulation(){
		map = new GameObject[SIZE][SIZE];
	}

	//Fills board with food and creatures
	public void populate(GameObject[][] map){
      Random rand = new Random();
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

	//Updates all objects on board
	public void update(){
	   
      Random rand = new Random();
      int foodCount = 0; // set intial food created this turn to 0
      while(foodCount < FOOD_PER_TURN) { // create the food in this turn
         int randX = rand.nextInt(128); int randY = rand.nextInt(128); // gets a random (x,y)
         if (map[randY][randX] == null) { // checks if (x,y) is empty
            map[randY][randX] = new Food(); // fills space with a new food
            foodCount++;
         } 
      }
            	
	}

	//gets all moves, 
	public Move[][] getMovesTick(){
		Move[][] m = new Move[SIZE][SIZE];
		for (int i=0; i<SIZE; i++){
			for (int j=0; j<SIZE; j++){
				GameObject obj = map[i][j];
				if (obj != null){
					if(obj instanceof Creature){
						m[i][j] = (Creature) obj.getMove(new SensoryInput(i, j, map));
					}
					obj.tick();
				}
			}
		}
		return m;
	}
}