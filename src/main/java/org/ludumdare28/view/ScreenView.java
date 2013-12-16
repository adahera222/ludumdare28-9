package org.ludumdare28.view;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.ludumdare28.View;
import org.ludumdare28.input.InputHandler;
import org.ludumdare28.view.screens.Screen;

/**
 * A view that delegates rendering and updates to the currently selected screen.
 */
public class ScreenView implements View {
    private final InputHandler inputHandler;
    private final InputMultiplexer inputMultiplexer;
    private Screen currentScreen;

    public ScreenView(InputHandler inputHandler, InputMultiplexer inputMultiplexer) {
        this.inputHandler = inputHandler;
        this.inputMultiplexer = inputMultiplexer;
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    /**
     * @param currentScreen the screen to show
     */
    public void setCurrentScreen(Screen currentScreen) {
        // Notify old screen that it is now close
        if (this.currentScreen != null) {
            this.currentScreen.close();
        }

        // Change screen
        this.currentScreen = currentScreen;

        // Notify new screen that it is now open
        if (this.currentScreen != null) {
            this.currentScreen.open(inputHandler, inputMultiplexer);
        }
    }

    @Override public void update(double timeSinceLastCall, double totalGameTime) {
        if (currentScreen != null) currentScreen.update(timeSinceLastCall, totalGameTime);
    }

    @Override public void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch, OrthographicCamera camera) {
        if (currentScreen != null) currentScreen.render(textureAtlas, spriteBatch, camera);
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }
}
