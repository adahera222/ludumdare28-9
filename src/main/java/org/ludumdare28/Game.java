package org.ludumdare28;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.flowutils.time.RealTime;
import org.flowutils.time.Time;
import org.ludumdare28.input.InputHandler;
import org.ludumdare28.view.ScreenView;
import org.ludumdare28.view.screens.Screen;

/**
 *
 */
public class Game implements ApplicationListener {
    // Logging prefix
    public static final String LOG = Game.class.getSimpleName();

    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();
    private final ScreenView screenView;
    private final int width;
    private final int height;
    private final String textureAtlasFile;

    private Time time = new RealTime();

    private TextureAtlas textureAtlas;
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;

    // Calculates the number of frames drawn per second
    private FPSLogger fpsLogger;
    private final InputHandler inputHandler;

    public Game(int width, int height, String textureAtlasFile) {
        this.width = width;
        this.height = height;
        this.textureAtlasFile = textureAtlasFile;

        inputHandler = new InputHandler();
        inputMultiplexer.addProcessor(inputHandler);
        screenView = new ScreenView(inputHandler, inputMultiplexer);
    }

    @Override public void create() {
        Gdx.app.log(LOG, "Creating game");
        fpsLogger = new FPSLogger();

        textureAtlas = new TextureAtlas(textureAtlasFile);
        camera = new OrthographicCamera(width, height);
        spriteBatch =  new SpriteBatch();

        // Handle input
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override public void resize(int width, int height) {
        Gdx.app.log(LOG, "Resizing " + width + ", " + height);
        //camera.setToOrtho(false, width, height);
    }

    @Override public void render() {
        // output the current FPS
        //fpsLogger.log();

        // Update
        doUpdate();

        // Render
        doRender();
    }

    private void doUpdate() {
        time.nextStep();
        screenView.update(time.getLastStepDurationSeconds(), time.getSecondsSinceStart());
    }

    private void doRender() {
        GLCommon gl = Gdx.gl;

        // Clear the screen to Black
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        camera.update();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.enableBlending();
        spriteBatch.setColor(Color.WHITE);

        spriteBatch.begin();
        screenView.render(textureAtlas, spriteBatch, camera);
        spriteBatch.end();
    }

    @Override public void pause() {
        Gdx.app.log(Game.LOG, "Pausing game" );
    }

    @Override public void resume() {
        Gdx.app.log(Game.LOG, "Resuming game" );
    }

    @Override public void dispose() {
        Gdx.app.log(Game.LOG, "Disposing game" );
    }

    public Screen getCurrentScreen() {return screenView.getCurrentScreen();}

    public void setCurrentScreen(Screen currentScreen) {
        screenView.setCurrentScreen(currentScreen);
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }
}
