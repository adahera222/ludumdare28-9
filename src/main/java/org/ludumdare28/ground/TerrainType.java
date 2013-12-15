package org.ludumdare28.ground;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Type of terrain.  Has method to get texture to use.
 */
public enum TerrainType {

    SAND("sand1", "sand2", "sand3");

    private final List<String> textureNames = new ArrayList<String>();
    private final List<TextureRegion> textures = new ArrayList<TextureRegion>();

    private TerrainType(String textureName0, String ... textureNames) {
        this.textureNames.add(textureName0);
        this.textureNames.addAll(Arrays.asList(textureNames));

        for (int i = 0; i < this.textureNames.size(); i++) {
            textures.add(null);
        }
    }

    /**
     * Texture for this terrain type
     * @param textureAtlas atlas with loaded textures
     * @param seed random seed that uniquely identifies the texture to get
     * @return the texture.
     */
    public TextureRegion getTexture(TextureAtlas textureAtlas, int seed) {
        int index = Math.abs(seed) % getNumTextures();
        TextureRegion texture = textures.get(index);

        if (texture == null) {
            final String name = textureNames.get(index);
            texture = textureAtlas.findRegion(name);
            if (texture == null) throw new IllegalStateException("No texture region named '" +  name + "' found in texture atlas!");
            textures.set(index, texture);
        }

        return texture;
    }

    public int getNumTextures() {
        return textureNames.size();
    }

}
