package org.ludumdare28.ground;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.ludumdare28.Updating;
import org.ludumdare28.things.Thing;

import java.util.Collection;

/**
 * Information about one terrain square.
 */
public interface GroundCell {

    /**
     * @return type of terrain at this location.
     */
    TerrainType getTerrainType();

    /**
     * @return things in this cell.
     */
    Collection<Thing> getThings();

    /**
     * @param thing thing that belongs in this cell.  The thing must have coordinates that are in this cell.
     */
    void addThing(Thing thing);

    /**
     * @param thing thing to remove from this cell.
     */
    void removeThing(Thing thing);

    void setTerrainType(TerrainType terrainType);

    int getRandomSeed();

    TextureRegion getTexture(TextureAtlas textureAtlas, double timeSinceLastCall);

    void sortThingsByDistance();

    Thing getClosestThing(Thing reference, double maxDistance, Class<? extends Thing> thingType);

    double getAltitude();

    void setAltitude(double altitude);
}
