package org.ludumdare28;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.ludumdare28.utils.MultiPic;

/**
 * Simple pictures.
 */
public enum Pic {

    STONE("stone1", "stone2", "stone3", "stone4", "stone5"),
    BUSH1("bush1"),
    BUSH1HALF("bush1half"),
    BUSH1FULL("bush1full"),
    BUSH2("bush2"),
    BUSH2HALF("bush2half"),
    BUSH2FULL("bush2full"),
    SPRING_BASE("springwaterbase"),
    SPRING_WATER("springwater1", "springwater2", "springwater3", "springwater4");

    private final MultiPic multiPic;

    private Pic(String textureName0, String... textureNames) {
        multiPic = new MultiPic(textureName0, textureNames);
    }

    /**
     * Texture for this image
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



}
