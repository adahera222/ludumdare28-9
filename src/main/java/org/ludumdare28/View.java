package org.ludumdare28;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Something that can render something.
 */
public interface View extends Updating {

    void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch);

}
