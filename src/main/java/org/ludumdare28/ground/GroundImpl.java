package org.ludumdare28.ground;

import org.flowutils.Maths;
import org.ludumdare28.inventory.Inventory;
import org.ludumdare28.things.Thing;
import org.ludumdare28.things.ThingListener;
import org.ludumdare28.things.ThingListenerAdapter;

import java.util.Random;

/**
 *
 */
public class GroundImpl implements Ground {

    private final int sizeX;
    private final int sizeY;
    private final GroundCell[] groundCells;

    private final ThingListener thingListener = new ThingListenerAdapter() {
        @Override public void onMoved(Thing thing, double oldX, double oldY, double newX, double newY) {
            if (thing.getGround() == GroundImpl.this) {
                final GroundCell oldCell = getCell(oldX, oldY);
                final GroundCell newCell = getCell(newX, newY);

                // If the thing moved from one cells area to anothers, update the cell it is in
                if (oldCell != newCell) {
                    oldCell.removeThing(thing);
                    newCell.addThing(thing);
                }
            }
        }
    };

    public GroundImpl(int sizeX, int sizeY, int randomSeed) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        // Create ground cell array
        groundCells = new GroundCell[sizeX * sizeY];

        // Create cells
        Random random = new Random(randomSeed);
        for (int i = 0; i < groundCells.length; i++) {
            groundCells[i] = new GroundCellImpl(TerrainType.SAND, random.nextInt());
        }
    }

    @Override public GroundCell getCell(int cellX, int cellY) {
        final int index = index(cellX, cellY);
        if (index < 0) return null;
        else return groundCells[index];
    }

    @Override public GroundCell getCell(double x, double y) {
        final int index = index(x, y);
        if (index < 0) return null;
        else return groundCells[index];
    }

    @Override public void addThing(Thing thing) {
        // Set the ground to this if not already set
        thing.moveToGround(this);

        // Listen to the movements of the thing and update the ground cell it is in
        thing.addThingListener(thingListener);

        // Add it to the correct ground cell
        final GroundCell cell = getCell(thing.getX(), thing.getY());
        if (cell != null) {
            cell.addThing(thing);
        }
    }

    @Override public void removeThing(Thing thing) {
        // Remove from ground cell
        final GroundCell cell = getCell(thing.getX(), thing.getY());
        if (cell != null) {
            cell.removeThing(thing);
        }

        // Stop listening to movements
        thing.removeThingListener(thingListener);

        // Set the ground to null if not already set
        thing.moveToGround(null);
    }

    private int index(double x, double y) {
        return index(Maths.fastFloor(x), Maths.fastFloor(y));
    }

    private int index(int cellX, int cellY) {
        if (cellX < 0 || cellX >= sizeX ||
            cellY < 0 || cellY >= sizeY) return -1;
        else return cellY * sizeX + cellX;
    }
}
