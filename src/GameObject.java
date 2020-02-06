package sim;

//Interface for any object on the board
public interface GameObject{
	Move getMove(SensoryInput input);

	Stats getStats(); // Gets the stats of the creature

	GameObject getOffspring(); //gets the offspring object

	int[] getDirection(); // Returns direction in <x, y> vector format

	void tick(); // Increments age, updates state of GameObject

	double getSimilarity(GameObject g); // returns similarity from 0 to 1. Food returns -2
}

enum Move{
	FORWARD, BACK, LEFT, RIGHT, INTERACT, LEFTTURN, RIGHTTURN;
}