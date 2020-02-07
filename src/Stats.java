package sim;

public class Stats {
   
   private int health; // how much hp the critter has
   private int defense; // how much the attack recieved is lowered by
   private int attack; // how much damage the critter does
   private int regen; // how much hp the critter regenerates per turn
   private int lifespan; // how long it will live
   private int totalStats; // total points available to be distributed
   
   // creates an instance of stats. Takes in an array of "genetic information"
   public Stats() {
      if (health < 0) { // get rid of negative stats
         health = 0;
      } 
      if (defense < 0) {
         defense = 0;
      }
      if (attack < 0) {
         attack = 0;
      }
      if (regen < 0) {
         regen = 0;
      }
      if (lifespan < 0) {
         lifespan = 0;
      }
      totalStats = health + defense + attack + regen + lifespan; // will covert to a proportion 
      health = health / totalStats;
      defense = defense / totalStats;
      attack = attack / totalStats;
      regen = regen / totalStats; 
      lifespan = lifespan / totalStats;  
      
   }
      
}