package sim;

import java.util.Random;

public class Stats {
   private final double MAX_HEALTH = 10;
   private final double MAX_DEFENSE = 10;
   private final double MAX_ATTACK = 10;
   private final double MAX_REGEN = 10;
   private final double MAX_LIFESPAN = 10;

   private double health; // how much hp the critter has
   private double defense; // how much the attack recieved is lowered by
   private double attack; // how much damage the critter does
   private double regen; // how much hp the critter regenerates per turn
   private double lifespan; // how long it will live
   private double totalStats; // total points available to be distributed
   
   // creates an instance of stats. Takes in an array of "genetic information"
   public Stats(Stats parent) {
      Random rand = new Random();

      // convert stats back to proportions & randomly alter stats
      health = health / MAX_HEALTH + rand.nextDouble() - 0.1;
      defense = defense / MAX_DEFENSE + rand.nextDouble() - 0.1;
      attack = attack / MAX_ATTACK + rand.nextDouble() - 0.1;
      regen = regen / MAX_REGEN + rand.nextDouble() - 0.1;
      lifespan = lifespan / MAX_LIFESPAN + rand.nextDouble() - 0.1;

      // get rid of negative stats
      if (health < 0) {
         health = 0;
      } if (defense < 0) {
         defense = 0;
      } if (attack < 0) {
         attack = 0;
      } if (regen < 0) {
         regen = 0;
      } if (lifespan < 0) {
         lifespan = 0;
      }

      // will covert to a proportion and multiply to get it's numerical stat
      totalStats = health + defense + attack + regen + lifespan;
      health = health / totalStats * MAX_HEALTH;
      defense = defense / totalStats * MAX_DEFENSE;
      attack = attack / totalStats * MAX_ATTACK;
      regen = regen / totalStats * MAX_REGEN;
      lifespan = lifespan / totalStats * MAX_LIFESPAN;
      
   }
      
}