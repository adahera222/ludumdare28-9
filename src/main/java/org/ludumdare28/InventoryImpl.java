package org.ludumdare28;

import java.util.ArrayList;

/**
 * Author: Shiera
 */
public class InventoryImpl implements Inventory {

    private final ArrayList<Thing> inventory= new ArrayList<Thing>();
    private int maxSlots;

    public InventoryImpl(int maxInventorySlots){
        maxSlots = maxInventorySlots;
    }

    @Override public boolean addToInventory(Thing thingToAdd){
        if (inventory.size() < maxSlots){
            inventory.add(thingToAdd);
            return true;
        }
        return false;
    }

    public void useThing(){
        Thing usingThisThing = chooseThing();

    }

    // removes this thing from the inventory
    @Override public void removeFromInventory(){
        Thing removingThisThing = chooseThing();
        inventory.remove(removingThisThing);
    }



    private Thing chooseThing(){
        // make something to choose a thing whit return choosed thing
        return null;
    }


}
