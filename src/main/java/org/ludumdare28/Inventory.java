package org.ludumdare28;

import java.util.List;

/**
 * Inventory with the players items, as well as the currently selected item.
 */
public interface Inventory {

    /**
     * @param thingToAdd Something to add to the inventory.
     * @throws IllegalStateException if there was not enough space.
     */
    void addToInventory(Thing thingToAdd);

    /**
     * @param thingToRemove Something to remove from the inventory.
     * @throws IllegalArgumentException if the thing was not in the inventory.
     */
    void removeFromInventory(Thing thingToRemove);

    /**
     * @return all the things currently in the inventory, including the selected thing.
     */
    List<Thing> getThings();

    /**
     * @return number of open slots left in the inventory.
     */
    int getOpenSlots();

    /**
     * @param thingToSelect thing to select, or null if should select none.
     * @throws IllegalStateException if there was not enough space.
     */
    void setSelectedThing(Thing thingToSelect);

    /**
     * @return get selected thing, or null if none selected.
     */
    Thing getSelectedThing();


}
