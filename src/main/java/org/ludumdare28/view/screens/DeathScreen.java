package org.ludumdare28.view.screens;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.ludumdare28.SkinFactory;
import org.ludumdare28.input.InputHandler;
import org.ludumdare28.view.ScreenView;

/**
 *
 */
public class DeathScreen implements Screen {
    private static final int DELAY = 3;
    private Stage stage;
    private InputMultiplexer inputMultiplexer;
    private Table table;

    private double timeLeftToNextNotice = DELAY;
    private int notice = 0;
    private Skin skin;

    private boolean quit = false;

    @Override public void open(InputHandler inputHandler, InputMultiplexer inputMultiplexer, ScreenView screenView) {
        this.inputMultiplexer = inputMultiplexer;
    }

    @Override public void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch, OrthographicCamera camera) {
        if (stage == null) {
            stage = new Stage(camera.viewportWidth, camera.viewportHeight, true, spriteBatch);
            inputMultiplexer.addProcessor(stage);
            createUi(stage);
        }

        // Draw ui
        stage.getCamera().update();
        stage.getSpriteBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.getRoot().draw(spriteBatch, 1);
    }

    @Override public void close() {
    }

    @Override public void update(double timeSinceLastCall, double totalGameTime) {
        timeLeftToNextNotice -= timeSinceLastCall;
        if (timeLeftToNextNotice <= 0) {
            timeLeftToNextNotice = DELAY;
            notice++;
            if (notice == 1) {
                table.row();
                table.add(new Label("You survived " + (int)(totalGameTime/60) + " days.", skin));
            }
            else if (notice == 2) {
                table.row();
                table.add(new Label("There are no extra lives.", skin));
            }
            else if (notice == 3) {
                table.row();
                table.add(new Label("Now go do something awesome with the rest of your life!", skin));
            }
            else if (notice == 4) {
                table.row();
                final TextButton closeButton = new TextButton("  Close Game  ", skin);
                closeButton.addListener(new ClickListener() {
                    @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        quit = true;
                    }
                });
                table.add(closeButton);
            }
        }
    }

    private void createUi(Stage stage) {

        skin = SkinFactory.createSkin();

        // Create a table that fills the screen. Everything else will go inside this table.
        table = new Table();
        table.setFillParent(true);

        stage.addActor(table);

        table.add(new Label("Game Over", skin));
    }

    @Override public boolean isQuit() {
        return quit;
    }
}
