package org.ludumdare28.view.screens;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.ludumdare28.Updating;
import org.ludumdare28.View;
import org.ludumdare28.input.InputHandler;
import org.ludumdare28.view.ScreenView;

/**
 *
 */
public interface Screen extends View, Updating {

    void open(InputHandler inputHandler, InputMultiplexer inputMultiplexer, ScreenView screenView);

    void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch, OrthographicCamera camera);

    void close();

    boolean isQuit();
}
