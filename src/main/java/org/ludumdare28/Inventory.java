package org.ludumdare28;

import java.util.ArrayList;

/**
 * Author: Shiera
 */
public class Inventory {

    private final ArrayList<Thing> inventory= new ArrayList<Thing>();
    private int maxSlots;

    public Inventory(int maxInventorySlots){
        maxSlots = maxInventorySlots;
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
