package org.ludumdare28.ground;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.ludumdare28.utils.MultiPic;

/**
 * Type of terrain.  Has method to get texture to use.
 */
public enum TerrainType {

    DEEP_WATER("deepwater1", "deepwater2"),
    WATER("water1", "water2", "water3", "water4"),
    WATER_SHORE("watershore1", "watershore2", "watershore3", "watershore4", "watershore5"),
    WET_SAND("wetsand1", "wetsand2"),
    SAND("sand1", "sand2", "sand3"),
    GRASS("grass1", "grass2", "grass3"),
    JUNGLE("jungle1", "jungle2", "jungle3"),
    HIGHLANDS("highlands1", "highlands2", "highlands3"),
    ROCKY("rocky1", "rocky2", "rocky3"),
    MOUNTAIN("mountain1", "mountain2", "mountain3"),

    ;

    private final MultiPic multiPic;

    private TerrainType(String textureName0, String ... textureNames) {
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
}
