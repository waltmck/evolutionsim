package sim;

//Interface for any object on the board
public interface GameObject{
	Move getMove(SensoryInput input);

	Stats getStats(); // Gets the stats of the creature

	GameObject getOffspring(); //gets the offspring object

	int[] getDirection(); // Returns direction in <x, y> vector format

	void tick(); // Increments age, updates state of GameObject
}

enum Move{
	FORWARD, BACK, LEFT, RIGHT, INTERACT, LEFTTURN, RIGHTTURN;
}