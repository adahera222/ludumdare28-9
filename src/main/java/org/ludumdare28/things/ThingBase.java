package org.ludumdare28.things;

import org.ludumdare28.ground.Ground;
import org.ludumdare28.ground.GroundCell;
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
    private String name;
    private boolean canWalkAnywhere = false;

    protected ThingBase(double posX ,double posY , EdibleAspect edibleAspect, DrinkableAspect drinkableAspect) {
        this.posX = posX;
        this.drinkableAspect = drinkableAspect;
        this.posY = posY;
        this.inventoryThingIsIn = null;

        setEdibleAspect(edibleAspect);
    }

    protected ThingBase(double posX, double posY) {
       this(posX, posY, null, null);
    }



    protected ThingBase() {
        this(0,0,null, null);
    }

    protected ThingBase(String name) {
        this(0,0,null, null);
        this.name = name;
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

    @Override public GroundCell getGroundCell() {
        final Ground ground = getGround();
        if (ground != null) {
            return ground.getCell(getX(), getY());
        }
        else {
            return null;
        }
    }

    @Override public Inventory getInventoryThingIsIn() {
        return inventoryThingIsIn;
    }

    @Override public void setPos(double x, double y) {
        if (ground != null) {
            final GroundCell newCell = ground.getCell(posX, posY);
            final GroundCell oldCell = ground.getCell(x, y);
            if (oldCell != null && newCell != null) {
                final boolean oldTerrainPassable = newCell.getTerrainType().isPassable();
                final boolean newTerrainPassable = oldCell.getTerrainType().isPassable();

                // Don't allow the player to walk into unpassable terrain, but allow them to walk out of there if they get there.
                if (oldTerrainPassable && !newTerrainPassable) return;
            }
        }

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

        if (edibleAspect != null) edibleAspect.setHostThing(this);
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

    @Override public double getDistanceSquared(Thing other) {
        if (ground == null) throw new IllegalStateException("Not on ground");
        if (ground != other.getGround()) throw new IllegalArgumentException("Not on same ground");

        double dx = getX() - other.getX();
        double dy = getY() - other.getY();
        return dx*dx + dy*dy;
    }

    @Override public double getDistance(Thing other) {
        return Math.sqrt(getDistanceSquared(other));
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

    @Override public Thing getClosestThing(double maxDistance) {
        return getClosestThing(maxDistance, Thing.class);
    }

    @Override public Thing getClosestThing(double maxDistance, Class<? extends Thing> thingType) {
        return getWorld().getGround().getClosestThing(this, maxDistance, thingType);
    }

    @Override public void update(double lastFrameDurationSeconds, double totalGameTime) {
        if (appearance != null) appearance.update(lastFrameDurationSeconds, totalGameTime);
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCanWalkAnywhere() {
        return canWalkAnywhere;
    }

    public void setCanWalkAnywhere(boolean canWalkAnywhere) {
        this.canWalkAnywhere = canWalkAnywhere;
    }
}
