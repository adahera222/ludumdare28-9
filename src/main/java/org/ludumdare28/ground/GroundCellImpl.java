package org.ludumdare28.ground;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.ludumdare28.things.Thing;

import java.util.*;

/**
 * An area on the ground.
 */
public class GroundCellImpl implements GroundCell {

    private static final Comparator<Thing> DEPTH_COMPARATOR = new Comparator<Thing>() {
        @Override public int compare(Thing o1, Thing o2) {
            return Double.compare(o1.getY(), o2.getY());
        }
    };

    private List<Thing> things = new ArrayList<Thing>(4);
    private TerrainType terrainType;
    private double altitude;
    private int randomSeed;
    private double timeUntilAnimationChangeSeconds = 0;

    public GroundCellImpl(TerrainType terrainType, int randomSeed) {
        this.terrainType = terrainType;
        this.randomSeed = randomSeed;
    }

    @Override public TerrainType getTerrainType() {
        return terrainType;
    }

    @Override public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    @Override public Collection<Thing> getThings() {
        return things;
    }

    @Override public void addThing(Thing thing) {
        if (!things.contains(thing)) {
            things.add(thing);
        }
    }

    @Override public void removeThing(Thing thing) {
        things.remove(thing);
    }

    @Override public int getRandomSeed() {
        return randomSeed;
    }

    @Override public TextureRegion getTexture(TextureAtlas textureAtlas, double timeSinceLastCall) {
        if (terrainType.isAnimated()) updateAnimation(timeSinceLastCall);
        return terrainType.getTexture(textureAtlas, randomSeed);
    }

    @Override public void sortThingsByDistance() {
        Collections.sort(things, DEPTH_COMPARATOR);
    }

    @Override public Thing getClosestThing(Thing reference, double maxDistance, Class<? extends Thing > thingType) {
        return getClosestThingFromList(reference, things, maxDistance, thingType);
    }

    /**
     * @param reference
     * @param things
     * @return the closest thing to the reference thing from a list of things.
     */
    public static Thing getClosestThingFromList(Thing reference, final List<Thing> things, double maxDistance, Class<? extends Thing> thingType) {
        double maxDistanceSquared = maxDistance * maxDistance;
        double closestDistanceSquared = Double.POSITIVE_INFINITY;
        Thing closestThing = null;

        for (Thing thing : things) {
            if (thing != null && thing != reference && thingType.isInstance(thing)) {
                double distanceSquared = thing.getDistanceSquared(reference);
                if (distanceSquared < closestDistanceSquared && distanceSquared <= maxDistanceSquared) {
                    closestDistanceSquared = distanceSquared;
                    closestThing = thing;
                }
            }
        }

        return closestThing;
    }

    @Override public double getAltitude() {
        return altitude;
    }

    @Override public void setAltitude(double altitude) {
        this.altitude = altitude;
    }


    private void updateAnimation(double timeSinceLastCall) {
        timeUntilAnimationChangeSeconds -= timeSinceLastCall;
        if (timeUntilAnimationChangeSeconds <= 0) {
            randomSeed = (int) (Math.random() * 1000);
            timeUntilAnimationChangeSeconds = terrainType.getAnimationSpeed() * (0.5+Math.random());
        }
    }
}
