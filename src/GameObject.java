package sim;

public interface GameObject{
	Move getMove(SensoryInput input);

	Stats getStats();

	boolean isFood();

	GameObject getOffspring();
}

enum Move{
	FORWARD, BACK, LEFT, RIGHT, INTERACT, LEFTTURN, RIGHTTURN;
}