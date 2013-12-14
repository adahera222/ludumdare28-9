package org.ludumdare28;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;

/**
 *
 */
public class Game implements ApplicationListener {
    // Logging prefix
    public static final String LOG = Game.class.getSimpleName();

    // Calculates the number of frames drawn per second
    private FPSLogger fpsLogger;

    @Override public void create() {
        Gdx.app.log(LOG, "Creating game");
        fpsLogger = new FPSLogger();
    }

    @Override public void resize(int width, int height) {
        Gdx.app.log(LOG, "Resizing " + width + ", " + height);

    }

    @Override public void render() {
        // the following code clears the screen with the given RGB color (green)
        Gdx.gl.glClearColor( 0f, 1f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // output the current FPS
        fpsLogger.log();    }

    @Override public void pause() {
        Gdx.app.log(Game.LOG, "Pausing game" );
    }

    @Override public void resume() {
        Gdx.app.log(Game.LOG, "Resuming game" );
    }

    @Override public void dispose() {
        Gdx.app.log(Game.LOG, "Disposing game" );
    }
}
