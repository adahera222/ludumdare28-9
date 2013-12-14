package org.ludumdare28.things;

import org.ludumdare28.inventory.Inventory;
import org.ludumdare28.inventory.InventoryImpl;

/**
 * Author: Shiera
 */
public class Player  extends BaseThing{
    private String name;
    private double baseSpeed;
    private double speed;
    private Inventory inventory;
    private double hunger;
    private double thirst;
    private double tiredness;
    private double damage;
    private final int maxStat = 100;
    private double hungerSpeedModifier;
    private double thirstSpeedModifier;
    private double tirednessSpeedModifier;
    private double damageSpeedModifier;
    private boolean alive;
    private boolean awake;
    /*
    -the player have a name that can be given in the beginning of the game.
    -the player have a speed that tells how long time it takes to do different tasks, the speed
     consist of the players basespeed that can be lowered because of hunger, thirst and need to sleep
     -The player have an inventory wher all his things are
     - maxstat tells how big hunger, thirst and need to sleep can be (ex if hunger goes ower maxStat you die in hunger)
     */

    public Player(String name, int maxInventorySlots) {
        this.name = name;
        this.baseSpeed = maxStat;
        speed = baseSpeed;
        hunger = 0;
        thirst = 0;
        tiredness = 0;
        damage = 0;
        alive = true;
        awake = true;
        hungerSpeedModifier = 0;
        thirstSpeedModifier = 0;
        damageSpeedModifier = 0;
        tirednessSpeedModifier = 0;
        inventory = new InventoryImpl(maxInventorySlots);
    }

    public void changeHunger(int hungerAmount){
        hunger += hungerAmount;
        if (hunger < 0) hunger = 0;
        if (hunger >= maxStat) alive = false;
        if (hunger >= 50) hungerSpeedModifier = -(hunger-50)/2;
        if (hunger < 50) hungerSpeedModifier = 0;

    }

    public void changeTiredness(int tirednessAmount){
        tiredness += tirednessAmount;
        if (tiredness < 0) tiredness = 0;
        if (tiredness >= maxStat) awake = false;
        if (tiredness >= 50) tirednessSpeedModifier = -(tiredness-50)/2;
        if (tiredness < 50) tirednessSpeedModifier = 0;

    }

    public void changeThirst(int thirstAmount){
        thirst += thirstAmount;
        if (thirst < 0) thirst = 0;
        if (thirst >= maxStat) alive = false;
        if (thirst >= 50) thirstSpeedModifier = -(thirst-50)/2;
        if (thirst < 50) thirstSpeedModifier = 0;

    }

    public void changeDamage(int damageAmount){
        damage += damageAmount;
        if (damage < 0) damage = 0;
        if (damage >= maxStat) alive = false;
        if (damage >= 50) damageSpeedModifier = -(damage-50)/2;
        if (damage < 50) damageSpeedModifier = 0;

    }

    public double getDamage() {
        return damage;
    }

    public double getTiredness() {
        return tiredness;
    }

    public double getHunger() {
        return hunger;
    }

    public double getThirst() {
        return thirst;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isAwake() {
        return awake;
    }

    public double getSpeed(){
        return baseSpeed + damageSpeedModifier + hungerSpeedModifier + thirstSpeedModifier + tirednessSpeedModifier;
    }

    public int getMaxStat() {
        return maxStat;
    }
}
