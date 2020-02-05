package sim;

import java.util.Random;
import sim.util.Move;
import sim.gameobjects.Creature;
import sim.SensoryInput;

public class Simulation{
	private final int SIZE = 128;
	private final int NUM_FOOD_INITIAL = 1000;
	private GameObject[][] map;

	public Simulation(){
		map = new GameObject[SIZE][SIZE];
	}

	//Fills board with food and creatures
	public void populate(){

	}

	//Updates all creatures on board
	public void update(){
		
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