package org.ludumdare28.things;

import org.ludumdare28.ground.Ground;
import org.ludumdare28.inventory.Inventory;
import org.ludumdare28.things.aspects.DrinkableAspect;
import org.ludumdare28.things.aspects.EdibleAspect;
import org.ludumdare28.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public abstract class ThingBase implements Thing {
    private double posX;
    private double posY;
    private Ground ground;
    private Inventory inventoryThingIsIn;
    private EdibleAspect edibleAspect;
    private Appearance appearance;
    private DrinkableAspect drinkableAspect;
    private Set<ThingListener> listeners = new HashSet<ThingListener>(4);
    private World world;

    protected ThingBase(double posX ,double posY , EdibleAspect edibleAspect, DrinkableAspect drinkableAspect) {
        this.posX = posX;
        this.edibleAspect = edibleAspect;
        this.drinkableAspect = drinkableAspect;
        this.posY = posY;
        this.inventoryThingIsIn = null;

    }

    protected ThingBase(double posX, double posY) {
       this(posX, posY, null, null);
    }

    protected ThingBase() {
        this(0,0,null, null);
    }

    protected ThingBase(EdibleAspect edibleAspect) {
        this(0,0, edibleAspect, null);
    }

    protected ThingBase (DrinkableAspect drinkableAspect){
        this(0, 0, null, drinkableAspect );
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override public double getX() {
        return posX;
    }

    @Override public double getY() {
        return posY;
    }

    public Ground getGround() {
        return ground;
    }

    @Override public Inventory getInventoryThingIsIn() {
        return inventoryThingIsIn;
    }

    @Override public void setPos(double x, double y) {
        double oldX = posX;
        double oldY = posY;
        posX = x;
        posY = y;

        // Notify listeners about the move
        for (ThingListener listener : listeners) {
            listener.onMoved(this, oldX, oldY, posX, posY);
        }
    }

    @Override public void moveToInventory(Inventory inventoryThingIsIn) {
        if (this.inventoryThingIsIn != inventoryThingIsIn) {
            this.inventoryThingIsIn = inventoryThingIsIn;

            if (inventoryThingIsIn != null) {
                if (ground != null) ground.removeThing(this);
                ground = null;
                inventoryThingIsIn.addToInventory(this);

                // Notify listeners about the move
                for (ThingListener listener : listeners) {
                    listener.onPlacedInInventory(this, inventoryThingIsIn);
                }
            }
        }
    }

    @Override public void moveToGround(Ground ground) {
        if (this.ground != ground) {
            this.ground = ground;

            if (ground != null) {
                if (inventoryThingIsIn != null) inventoryThingIsIn.removeFromInventory(this);
                inventoryThingIsIn = null;
                ground.addThing(this);

                // Notify listeners about the move
                for (ThingListener listener : listeners) {
                    listener.onPlacedOnGround(this, ground, posX, posY);
                }
            }
        }
    }


    @Override public void addThingListener(ThingListener listener) {
        listeners.add(listener);
    }

    @Override public void removeThingListener(ThingListener listener) {
        listeners.remove(listener);
    }

    @Override public EdibleAspect getEdibleAspect() {
        return edibleAspect;
    }

    public void setEdibleAspect(EdibleAspect edibleAspect) {
        this.edibleAspect = edibleAspect;
    }

    @Override
    public DrinkableAspect getDrinkableAspect() {
        return drinkableAspect;
    }

    @Override public Appearance getAppearance() {
        return appearance;
    }

    @Override public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
    }

    public void setDrinkableAspect(DrinkableAspect drinkableAspect){
        this.drinkableAspect = drinkableAspect;
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public void delete() {
        if (world != null) {
            world.deleteThing(this);
        }
    }

    @Override public void update(double timeSinceLastCall, double totalGameTime) {
    }

    @Override
    public boolean isPickable() {
        return false;
    }
}
