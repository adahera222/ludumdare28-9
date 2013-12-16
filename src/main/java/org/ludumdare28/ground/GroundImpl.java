package org.ludumdare28.ground;

import org.flowutils.Maths;
import org.ludumdare28.things.Thing;
import org.ludumdare28.things.ThingListener;
import org.ludumdare28.things.ThingListenerAdapter;

import java.util.ArrayList;
import java.util.List;
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
                    if (oldCell != null) oldCell.removeThing(thing);
                    if (newCell != null) newCell.addThing(thing);
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

    @Override public Thing getClosestThing(Thing reference, double maxDistance) {
        // Get reference location
        if (reference.getGround() != this) return null;
        final double refX = reference.getX();
        final double refY = reference.getY();
        final GroundCell refCell = getCell(refX, refY);
        if (refCell == null) return null;

        // Gather closest things from neighbouring cells in expanding rectangles
        List<Thing> closeThings = new ArrayList<Thing>();
        for (int distance = 0; distance < maxDistance; distance++) {
            if (distance == 0) {
                // At the center there is only one cell
                closeThings.add(getCell(refX, refY).getClosestThing(reference, maxDistance));
            }
            else {
                // Rectangle of neighbours, first cells on horizontal edges
                for (int dx = -distance; dx <= distance; dx++) {
                    getClosestThingFromCell(closeThings, reference, maxDistance, refX + dx, refY + distance);
                    getClosestThingFromCell(closeThings, reference, maxDistance, refX + dx, refY - distance);
                }
                // Then cells on vertical edges, except the corners that we already checked as a part of the horizontal edges
                for (int dy = -distance+1; dy <= distance-1; dy++) {
                    getClosestThingFromCell(closeThings, reference, maxDistance, refX + distance, refY + dy);
                    getClosestThingFromCell(closeThings, reference, maxDistance, refX - distance, refY - dy);
                }
            }
        }

        // Find the closest thing and return it (or null if none found)
        return GroundCellImpl.getClosestThingFromList(reference, closeThings, maxDistance);
    }

    private void getClosestThingFromCell(List<Thing> closeThings,
                                         Thing reference,
                                         double maxDistance,
                                         final double cellX,
                                         final double cellY) {
        // Get the cell
        final GroundCell cell = getCell(cellX, cellY);

        // If cell is on map..
        if (cell != null) {
            // Get closest thing in cell
            final Thing closestThing = cell.getClosestThing(reference, maxDistance);

            // If there was a close thing in cell..
            if (closestThing != null) {
                // Add it to the given list
                closeThings.add(closestThing);
            }
        }
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
