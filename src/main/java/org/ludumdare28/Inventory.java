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

    public void useThing(){
        Thing usingThisThing = chooseThing();

    }

    // removes this thing from the inventory
    public void removeFromInventory(){
        Thing removingThisThing = chooseThing();
        inventory.remove(removingThisThing);
    }



    private Thing chooseThing(){
        // make something to choose a thing whit return choosed thing
        return null;
    }


}
