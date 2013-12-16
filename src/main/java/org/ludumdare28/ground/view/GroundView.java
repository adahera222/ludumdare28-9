package org.ludumdare28.ground.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.flowutils.Maths;
import org.ludumdare28.View;
import org.ludumdare28.ground.Ground;
import org.ludumdare28.ground.GroundCell;
import org.ludumdare28.ground.TerrainType;
import org.ludumdare28.things.Appearance;
import org.ludumdare28.things.Thing;

/**
 * Renders the ground.
 */
public class GroundView implements View {

    // Point the camera is focused on
    private float cameraCenterX;
    private float cameraCenterY;

    // Ground we are looking at
    private Ground ground;

    private Thing focusedThing;

    private static final int SCREEN_GRID_BORDER_X = 32;
    private static final int SCREEN_GRID_BORDER_Y = 32;
    private static final int SCREEN_GRID_SIZE_X = 192-SCREEN_GRID_BORDER_X*2;
    private static final int SCREEN_GRID_SIZE_Y = 128-SCREEN_GRID_BORDER_Y*2;
    private static final int CELL_IMAGE_SIZE_X = 192;
    private static final int CELL_IMAGE_SIZE_Y = 128;

    private double timeSinceLastCall = 0;

    public float getCameraCenterX() {
        return cameraCenterX;
    }

    public void setCameraCenterX(float cameraCenterX) {
        this.cameraCenterX = cameraCenterX;
    }

    public float getCameraCenterY() {
        return cameraCenterY;
    }

    public void setCameraCenterY(float cameraCenterY) {
        this.cameraCenterY = cameraCenterY;
    }

    public Ground getGround() {
        return ground;
    }

    public void setGround(Ground ground) {
        this.ground = ground;
    }

    @Override public void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch, OrthographicCamera camera) {

        final int screenWidth  = (int) camera.viewportWidth;
        final int screenHeight = (int) camera.viewportHeight;

        int screenSizeGridsX = screenWidth / SCREEN_GRID_SIZE_X + 2;
        int screenSizeGridsY = screenHeight / SCREEN_GRID_SIZE_Y + 2;

        final int startX = (int) (cameraCenterX - screenSizeGridsX/2 - 1);
        final int endX   = (int) (cameraCenterX + screenSizeGridsX/2 + 1);
        final int startY = (int) (cameraCenterY - screenSizeGridsY/2 - 1);
        final int endY   = (int) (cameraCenterY + screenSizeGridsY/2 + 1);

        // Draw all terrain
        // Draw terrain layer by layer
        for (int layer = 0; layer <= TerrainType.getMaxLayer(); layer++) {
            if (TerrainType.layerInUse(layer)) {
                // Draw all terrain types with the specified layer
                for (int y = endY-1; y >= startY; y--) {
                    for (int x = startX; x < endX; x++) {
                        final GroundCell cell = ground.getCell(x, y);
                        if (cell != null) {
                            final TerrainType terrainType = cell.getTerrainType();
                            if (terrainType.getLayer() == layer) {
                                final TextureRegion texture = cell.getTexture(textureAtlas, timeSinceLastCall);
                                float xOffs = - cameraCenterX * SCREEN_GRID_SIZE_X;
                                float yOffs = - cameraCenterY * SCREEN_GRID_SIZE_Y;
                                float screenPosX = SCREEN_GRID_SIZE_X * x - SCREEN_GRID_BORDER_X + xOffs;
                                float screenPosY = SCREEN_GRID_SIZE_Y * y - SCREEN_GRID_BORDER_Y + yOffs;

                                // Draw terrain
                                spriteBatch.draw(texture, screenPosX, screenPosY, CELL_IMAGE_SIZE_X, CELL_IMAGE_SIZE_Y);
                            }
                        }
                    }
                }
            }
        }
        

        // Draw all things
        for (int y = endY-1; y >= startY; y--) {
            for (int x = startX; x < endX; x++) {
                final GroundCell cell = ground.getCell(x, y);
                if (cell != null) {
                    float xOffs = - cameraCenterX * SCREEN_GRID_SIZE_X;
                    float yOffs = - cameraCenterY * SCREEN_GRID_SIZE_Y;
                    float screenPosX = SCREEN_GRID_SIZE_X * x - SCREEN_GRID_BORDER_X + xOffs;
                    float screenPosY = SCREEN_GRID_SIZE_Y * y - SCREEN_GRID_BORDER_Y + yOffs;

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
        if (focusedThing != null) {
            cameraCenterX = (float) focusedThing.getX();
            cameraCenterY = (float) focusedThing.getY();
        }

        this.timeSinceLastCall = timeSinceLastCall;
    }

    public Thing getFocusedThing() {
        return focusedThing;
    }

    public void setFocusedThing(Thing focusedThing) {
        this.focusedThing = focusedThing;
    }
}
