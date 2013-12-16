package org.ludumdare28.world;

import org.flowutils.Check;
import org.ludumdare28.things.player.Player;
import org.ludumdare28.ground.Ground;
import org.ludumdare28.things.Thing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.flowutils.Check.notNull;

/**
 *
 */
public class WorldImpl implements World {

    private final Ground ground;
    private final Player player;
    private List<Thing> things = new ArrayList<Thing>(100);
    private Set<Thing> thingsToDelete = new HashSet<Thing>();
    private Set<Thing> thingsToAdd = new HashSet<Thing>();

    public WorldImpl(Ground ground, Player player) {
        notNull(ground, "ground");
        notNull(player, "player");

        this.ground = ground;
        this.player = player;

        addThing(player, 10, 10);
    }

    public Ground getGround() {
        return ground;
    }

    public Player getPlayer() {
        return player;
    }

    @Override public void deleteThing(Thing thing) {
        Check.notNull(thing, "thing");

        if (thingsToAdd.contains(thing)) {
            // If we were about to add it, cancel that
            thingsToAdd.remove(thing);
        }
        else {
            thingsToDelete.add(thing);
        }
    }

    @Override public void addThing(Thing thing) {
        Check.notNull(thing, "thing");

        if (thingsToDelete.contains(thing)) {
            // If we were about to delete it, cancel that
            thingsToDelete.remove(thing);
        }
        else {
            thingsToAdd.add(thing);
        }
    }

    @Override public void addThing(Thing thing, double x, double y) {
        addThing(thing);
        thing.setPos(x, y);
        ground.addThing(thing);
    }

    @Override public void update(double timeSinceLastCall, double totalGameTime) {
        // Add things
        for (Thing thing : thingsToAdd) {
            doAddThing(thing);
        }
        thingsToAdd.clear();

        // Delete things
        for (Thing thing : thingsToDelete) {
            doRemoveThing(thing);
        }
        thingsToDelete.clear();

        // Update
        for (Thing thing : things) {
            thing.update(timeSinceLastCall, totalGameTime);
        }
    }

    private void doRemoveThing(Thing thing) {
        thing.setWorld(null);
        thing.moveToGround(null);
        thing.moveToInventory(null);
        things.remove(thing);
    }

    private void doAddThing(Thing thing) {
        thing.setWorld(this);
        things.add(thing);
    }

    @Override public List<Thing> getThings() {
        return things;
    }
}
