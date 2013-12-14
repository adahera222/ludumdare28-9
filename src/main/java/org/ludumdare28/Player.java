package org.ludumdare28;

/**
 * Author: Shiera
 */
public class Player {
    private String name;
    private double baseSpeed;
    private double speed;
    private Inventory inventory;
    private int hunger;
    private int thirst;
    private int needToSleep;
    private int maxStat;
    /*
    -the player have a name that can be given in the beginning of the game.
    -the player have a speed that tells how long time it takes to do different tasks, the speed
     consist of the players basespeed that can be lowered because of hunger, thirst and need to sleep
     -The player have an inventory wher all his things are
     - maxstat tells how big hunger, thirst and need to sleep can be (ex if hunger goes ower maxStat you die in hunger)
     */

    public Player(String name, double baseSpeed, int maxInventorySlots, int maxStat) {
        this.name = name;
        this.baseSpeed = baseSpeed;
        speed = baseSpeed;
        hunger = 0;
        thirst = 0;
        needToSleep = 0;
        this.maxStat = maxStat;
        inventory = new Inventory(maxInventorySlots);
    }
}
