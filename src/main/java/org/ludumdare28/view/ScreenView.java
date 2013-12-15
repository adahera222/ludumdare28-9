package org.ludumdare28.view;

import org.ludumdare28.Updating;
import org.ludumdare28.View;
import org.ludumdare28.view.screens.Screen;

/**
 * A view that delegates rendering and updates to the currently selected screen.
 */
public class ScreenView implements View {
    private Screen currentScreen;

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
            this.currentScreen.open();
        }
    }

    @Override public void update(double timeSinceLastCall, double totalGameTime) {
        if (currentScreen != null) currentScreen.update(timeSinceLastCall, totalGameTime);
    }

    @Override public void render() {
        if (currentScreen != null) currentScreen.render();
    }
}
