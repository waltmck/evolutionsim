package sim.gameobjects;

import sim.GameObject;
import sim.Genes;

public class Creature implements GameObject{
	private Genes genes;
	private int age;

	public Creature(Genes g){
		genes = g;
		age = 0;
	}

	public Creature getOffspring(){
		return new Creature(new Genes(genes));
	}
}