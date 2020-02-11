//Interface for any object on the board
public interface GameObject{
	Move getMove(SensoryInput input);

	double getHealth(); // Gets the health of the creature

	GameObject getOffspring(int[] d); //gets the offspring object, facing direction d

	GameObject getOffspring(); //gets the offspring object, facing random direction

	int[] getDirection(); // Returns direction in <x, y> vector format

	boolean tick(); // Increments age, updates state of GameObject, returns true of creature dies

	double getSimilarity(GameObject g); // returns similarity from 0 to 1. Food returns -2

	boolean damage(double health); // Damage the object by a given amount, returning true if the damage killed it

	double getAttack(); //gets the amount of damage done by this creature
}

enum Move{
	FORWARD, BACK, LEFT, RIGHT, INTERACT, LEFTTURN, RIGHTTURN;
}