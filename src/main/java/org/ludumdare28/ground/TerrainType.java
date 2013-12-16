package org.ludumdare28.ground;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.ludumdare28.utils.MultiPic;

/**
 * Type of terrain.  Has method to get texture to use.
 */
public enum TerrainType {

    DEEP_WATER(0, true, false, true, 1.5, "deepwater1", "deepwater2", "deepwater3"),
    WATER(0, true, false, true, 0.6, "water1", "water2", "water3"),
    WATER_SHORE(0, true, true, true, 0.3, "watershore1", "watershore2", "watershore3", "watershore4", "watershore5"),
    WET_SAND(1, false, true, false, 1, "wetsand1", "wetsand2", "wetsand3", "wetsand4"),
    SAND(2, false, true, false, 1, "sand1", "sand2", "sand3"),
    GRASS(4, false, true, false, 1, "drygrass1", "drygrass2", "drygrass3", "drygrass4"),
    JUNGLE(4, false, true, false, 1, "junglegrass1", "junglegrass2", "junglegrass3", "junglegrass4"),
    ROCKY(3, false, true, false, 1, "rock1", "rock2", "rock3", "rock4"),

    ;

    private final MultiPic multiPic;
    private final boolean passable;
    private final boolean water;
    private final int layer;
    private final boolean animated;
    private final double animationSpeed;

    private TerrainType(int layer,
                        boolean water, boolean passable,
                        boolean animated,
                        double animationSpeed,
                        String textureName0,
                        String... textureNames) {
        this.layer = layer;
        this.water = water;
        this.passable = passable;
        this.animated = animated;
        this.animationSpeed = animationSpeed;
        multiPic = new MultiPic(textureName0, textureNames);
    }

    /**
     * Texture for this terrain type
     * @param textureAtlas atlas with loaded textures
     * @param seed random seed that uniquely identifies the texture to get
     * @return the texture.
     */
    public TextureRegion getTexture(TextureAtlas textureAtlas, int seed) {
        return multiPic.getTexture(textureAtlas, seed);
    }

    public int getNumTextures() {
        return multiPic.getNumTextures();
    }

    public MultiPic getMultiPic() {
        return multiPic;
    }

    public int getLayer() {
        return layer;
    }

    public boolean isAnimated() {
        return animated;
    }

    public static boolean layerInUse(int layer) {
        for (TerrainType terrainType : TerrainType.values()) {
            if (terrainType.getLayer() == layer) return true;
        }

        return false;
    }

    public static int getMaxLayer() {
        int maxLayer = 0;
        for (TerrainType terrainType : TerrainType.values()) {
            final int layer = terrainType.getLayer();
            if (layer > maxLayer) maxLayer = layer;
        }

        return maxLayer;
    }

    public double getAnimationSpeed() {
        return animationSpeed;
    }

    public boolean isPassable() {
        return passable;
    }

    public boolean isWater() {
        return water;
    }
}
