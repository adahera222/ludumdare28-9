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
    SPRING_WATER("springwater1", "springwater2", "springwater3", "springwater4"),

    MOVING_SNAKE_BASE("snakeBase1", "snakeBase2", "snakeBase3", "snakeBase2"),
    MOVING_SNAKE_TOP("snakeTop1", "snakeTop2", "snakeTop3", "snakeTop2"),
    BITING_SNAKE_BASE("snakeBiteBase1", "snakeBiteBase2"),
    BITING_SNAKE_TOP("snakeBiteTop1", "snakeBiteTop2"),

    HUMAN("pirate_walk1", "pirate_stand", "pirate_walk2","pirate_stand"),
    WOUNDED_HUMAN("pirate_walk1", "pirate_stand", "pirate_walk2","pirate_stand"),
    EATING_HUMAN("pirate_stand"),
    DEAD_HUMAN("pirate_stand");




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
