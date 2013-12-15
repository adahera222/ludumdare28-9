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
    private final int randomSeed;

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

    @Override public TextureRegion getTexture(TextureAtlas textureAtlas) {
        return terrainType.getTexture(textureAtlas, randomSeed);
    }

    @Override public void sortThingsByDistance() {
        Collections.sort(things, DEPTH_COMPARATOR);
    }
}
