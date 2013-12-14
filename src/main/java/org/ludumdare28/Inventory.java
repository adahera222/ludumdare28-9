package org.ludumdare28;

import java.util.ArrayList;

/**
 * Author: Shiera
 */
public class Inventory {
    private ArrayList<Thing> inventory;
    private int maxSlots;

    public Inventory(int maxsInventorySlots){
        inventory = new ArrayList<Thing>();
        maxSlots = maxsInventorySlots;
    }

    public boolean addToInventory(Thing newThing){
        if (inventory.size() < maxSlots){
            inventory.add(newThing);
            return true;
        }
        return false;
    }

    //TODO show inventory


    public void removeFromInventory(){
        removable = chooseThing();
    }

    private Thing chooseThing(){
        // make something to choose a thing whit return choosed thing
    }
}
