package org.ludumdare28.things;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.ludumdare28.Updating;
import org.ludumdare28.View;

/**
 * Appearance of a thing
 */
public interface Appearance extends Updating {

    void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch, float screenX, float screenY);


}
