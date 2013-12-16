package org.ludumdare28.view.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.ludumdare28.ground.GroundCell;
import org.ludumdare28.ground.TerrainType;
import org.ludumdare28.things.Appearance;
import org.ludumdare28.things.Thing;

/**
 *
 */
public class AppearanceViewUi extends Actor {
    private TerrainType terrainType;
    private Appearance appearance;
    private TextureAtlas textureAtlas;
    private int randomSeed = (int) (Math.random() * 1000);
    private Pixmap pixmap = createDefaultBackground();
    private Texture bgTexture;
    private Thing viewedThing;
    private float viewScale = 0.5f;

    private Pixmap createDefaultBackground() {
        final Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        return pixmap;
    }

    public AppearanceViewUi(float w, float h) {
        this(w, h, null);
    }

    public AppearanceViewUi(float w, float h, TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        setWidth(w);
        setHeight(h);
    }

    public AppearanceViewUi(TextureAtlas textureAtlas, TerrainType terrainType, Appearance appearance) {
        this.textureAtlas = textureAtlas;
        this.terrainType = terrainType;
        this.appearance = appearance;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    public Thing getViewedThing() {
        return viewedThing;
    }

    public void setViewedThing(Thing viewedThing) {
        this.viewedThing = viewedThing;
    }

    private void updateFromThing() {
        if (viewedThing != null) {
            // Get ground the thing is on
            final GroundCell groundCell = viewedThing.getGroundCell();
            if (groundCell != null) {
                setTerrainType(groundCell.getTerrainType());
            }
            else {
                setTerrainType(null);
            }

            // Set appearance the thing has
            setAppearance(viewedThing.getAppearance());
        }
    }

    public int getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(int randomSeed) {
        this.randomSeed = randomSeed;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    @Override public void draw(SpriteBatch batch, float parentAlpha) {

        // Update view if we are looking at a thing
        updateFromThing();

        batch.setColor(Color.WHITE);

        // Draw background
        if (bgTexture == null) bgTexture = new Texture(pixmap);
        batch.draw(bgTexture, getX(), getY(), getWidth(), getHeight());

        // Draw background if specified
        if (terrainType != null) {
            final TextureRegion texture = terrainType.getTexture(textureAtlas, randomSeed);
            batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        }

        // Draw appearance if specified
        if (appearance != null) {
            final float xOffs = getX() + getWidth() * 0.5f;
            final float yOffs = getY() + getHeight() * 0.0f;

            final Matrix4 matrix = batch.getProjectionMatrix();
            batch.setProjectionMatrix(matrix.cpy().translate(xOffs, yOffs, 0).scale(viewScale, viewScale, 1f).translate(-xOffs, -yOffs, 0));
            appearance.render(textureAtlas, batch, xOffs, yOffs);
            batch.setProjectionMatrix(matrix);
        }


    }

    public void update(double deltaTime, double totalTime) {

    }

    public float getViewScale() {
        return viewScale;
    }

    public void setViewScale(float viewScale) {
        this.viewScale = viewScale;
    }
}
