package org.ludumdare28.ground.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.flowutils.Maths;
import org.ludumdare28.View;
import org.ludumdare28.ground.Ground;
import org.ludumdare28.ground.GroundCell;
import org.ludumdare28.things.Appearance;
import org.ludumdare28.things.Thing;

/**
 * Renders the ground.
 */
public class GroundView implements View {

    // Point the camera is focused on
    private double cameraCenterX;
    private double cameraCenterY;

    // Ground we are looking at
    private Ground ground;

    private static final int SCREEN_GRID_BORDER_X = 32;
    private static final int SCREEN_GRID_BORDER_Y = 32;
    private static final int SCREEN_GRID_SIZE_X = 192-SCREEN_GRID_BORDER_X*2;
    private static final int SCREEN_GRID_SIZE_Y = 128-SCREEN_GRID_BORDER_Y*2;
    private static final int CELL_IMAGE_SIZE_X = 192;
    private static final int CELL_IMAGE_SIZE_Y = 128;

    public double getCameraCenterX() {
        return cameraCenterX;
    }

    public void setCameraCenterX(double cameraCenterX) {
        this.cameraCenterX = cameraCenterX;
    }

    public double getCameraCenterY() {
        return cameraCenterY;
    }

    public void setCameraCenterY(double cameraCenterY) {
        this.cameraCenterY = cameraCenterY;
    }

    public Ground getGround() {
        return ground;
    }

    public void setGround(Ground ground) {
        this.ground = ground;
    }

    @Override public void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch) {

        final int startY = 0;
        final int endY = 10;
        final int startX = 0;
        final int endX = 10;

        for (int y = endY-1; y >= startY; y--) {
            for (int x = startX; x < endX; x++) {
                final GroundCell cell = ground.getCell(x, y);
                if (cell != null) {
                    final TextureRegion texture = cell.getTexture(textureAtlas);
                    float screenPosX = SCREEN_GRID_SIZE_X * x - SCREEN_GRID_BORDER_X;
                    float screenPosY = SCREEN_GRID_SIZE_Y * y - SCREEN_GRID_BORDER_Y;

                    // Draw cell
                    spriteBatch.draw(texture, screenPosX, screenPosY, CELL_IMAGE_SIZE_X, CELL_IMAGE_SIZE_Y);

                    // Sort items in cell in depth order
                    cell.sortThingsByDistance();

                    // Draw items in cell
                    for (Thing thing : cell.getThings()) {
                        final Appearance appearance = thing.getAppearance();
                        if (appearance != null) {
                            float thingX = (float) Maths.map(thing.getX(), x, x+1, screenPosX, screenPosX + SCREEN_GRID_SIZE_X);
                            float thingY = (float) Maths.map(thing.getY(), y, y+1, screenPosY, screenPosY + SCREEN_GRID_SIZE_Y);
                            appearance.render(textureAtlas, spriteBatch, thingX, thingY);
                        }
                    }
                }
            }
        }

    }

    @Override public void update(double timeSinceLastCall, double totalGameTime) {
    }
}
