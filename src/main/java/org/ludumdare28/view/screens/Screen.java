package org.ludumdare28.view.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.ludumdare28.Updating;
import org.ludumdare28.View;

/**
 *
 */
public interface Screen extends View, Updating {

    void open();

    void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch, OrthographicCamera camera);

    void close();
}
