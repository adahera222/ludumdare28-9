package org.ludumdare28.inventory;

import org.ludumdare28.things.Thing;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Shiera
 */
public class InventoryImpl implements Inventory {

    private final ArrayList<Thing> inventory= new ArrayList<Thing>();
    private int maxSlots;
    private Thing selectedThing;

    public InventoryImpl(int maxInventorySlots){
        maxSlots = maxInventorySlots;
    }

    @Override
    public void addToInventory(Thing thingToAdd) {
        if (!inventory.contains(thingToAdd)) {
            if (inventory.size() >= maxSlots){
                throw new IllegalArgumentException("no space in inventory");
            }
            inventory.add(thingToAdd);
            thingToAdd.moveToInventory(this);
        }
    }

    @Override
    public void removeFromInventory(Thing thingToRemove) {
        if (!inventory.contains(thingToRemove)){
            throw new IllegalArgumentException("inventory does not contain " + thingToRemove);
        }
        inventory.remove(thingToRemove);
        if (selectedThing == thingToRemove) selectedThing = null;
    }

    @Override
    public List<Thing> getThings() {
        return inventory;
    }

    @Override
    public int getOpenSlots() {
        return maxSlots - inventory.size();
    }

    @Override
    public void setSelectedThing(Thing thingToSelect) {
        selectedThing = thingToSelect;
    }

    @Override
    public Thing getSelectedThing() {
        return selectedThing;
    }


}
