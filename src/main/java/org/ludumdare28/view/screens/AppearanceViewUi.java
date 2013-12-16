package org.ludumdare28.view.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.ludumdare28.ground.GroundCell;
import org.ludumdare28.ground.TerrainType;
import org.ludumdare28.things.Appearance;
import org.ludumdare28.things.Thing;

import static org.flowutils.Check.notNull;

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
    private float viewScale = 0.7f;
    private float nameHeight = 20;
    private String name;
    private final BitmapFont bitmapFont;
    private final Matrix4 smallMatrix = new Matrix4();

    private Pixmap createDefaultBackground() {
        final Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        return pixmap;
    }

    public AppearanceViewUi(BitmapFont font, float w, float h) {
        this(font, w, h, null);
    }

    public AppearanceViewUi(BitmapFont bitmapFont, float w, float h, TextureAtlas textureAtlas) {
        notNull(bitmapFont, "bitmapFont");

        this.textureAtlas = textureAtlas;
        this.bitmapFont = bitmapFont;
        setWidth(w);
        setHeight(h);
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

            // Get name
            name = viewedThing.getName();
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
            batch.draw(texture, getX(), getY() + nameHeight, getWidth(), getHeight() - nameHeight);
        }

        // Draw name
        if (name != null) {
            final BitmapFont.TextBounds textBounds = bitmapFont.getBounds(name);
            final float textWidth = textBounds.width;
            final float textHeight = textBounds.height;
            bitmapFont.draw(batch, name, getX() + 0.5f*(getWidth() - textWidth), getY() + textHeight + nameHeight * 0.3f);
        }

        // Draw appearance if specified
        if (appearance != null) {
            final float xOffs = getX() + getWidth() * 0.5f;
            final float yOffs = getY() + getHeight() * 0.1f + nameHeight;

            final Matrix4 normalMatrix = batch.getProjectionMatrix();
            smallMatrix.set(normalMatrix);
            smallMatrix.translate(xOffs, yOffs, 0).scale(viewScale, viewScale, 1f).translate(-xOffs, -yOffs, 0);
            batch.setProjectionMatrix(smallMatrix);
            appearance.render(textureAtlas, batch, xOffs, yOffs);
            batch.setProjectionMatrix(normalMatrix);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
