package org.ludumdare28.things;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.ludumdare28.Pic;

/**
 *
 */
public class BushAppearance implements Appearance {

    private static final int BUSH_TYPE_COUNT = 2;
    private final int bushAppearanceSeed;
    private final Color berryColor;
    private final Color bushColor;
    private double berryAmount;
    private Pic bushPic;
    private Pic halfBerryPic;
    private Pic fullBerryPic;
    private float scaleX = 1;
    private float scaleY = 1;

    public BushAppearance(int bushAppearanceSeed, Color berryColor, Color bushColor) {
        this.bushColor = bushColor;
        scaleX = (float) (0.75 + Math.random() * 0.5);
        scaleY = (float) (0.75 + Math.random() * 0.5);

        this.bushAppearanceSeed = bushAppearanceSeed;
        this.berryColor = berryColor;

        int bushNum = 1 + bushAppearanceSeed % BUSH_TYPE_COUNT;
        if (bushNum == 1) {
            setPics(Pic.BUSH1, Pic.BUSH1HALF, Pic.BUSH1FULL);
        }
        else {
            setPics(Pic.BUSH2, Pic.BUSH2HALF, Pic.BUSH2FULL);
        }
    }

    private void setPics(final Pic bush, final Pic half, final Pic full) {
        bushPic = bush;
        halfBerryPic = half;
        fullBerryPic = full;
    }

    @Override public void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch, float screenX, float screenY) {
        // Draw bush
        drawPic(textureAtlas, spriteBatch, screenX, screenY, bushPic, bushColor);

        // Draw berries
        if (berryAmount > 0.1 && berryAmount < 0.5) {
            drawPic(textureAtlas, spriteBatch, screenX, screenY, halfBerryPic, berryColor);
        }
        else if (berryAmount >= 0.5) {
            drawPic(textureAtlas, spriteBatch, screenX, screenY, fullBerryPic, berryColor);
        }
    }

    private void drawPic(TextureAtlas textureAtlas,
                         SpriteBatch spriteBatch,
                         float screenX,
                         float screenY,
                         final Pic pic,
                         Color color) {
        final TextureRegion bushTexture = pic.getTexture(textureAtlas, bushAppearanceSeed);
        float w = scaleX * bushTexture.getRegionWidth();
        float h = scaleY * bushTexture.getRegionHeight();
        float x = screenX - w / 2;
        float y = screenY;

        spriteBatch.setColor(color);
        spriteBatch.draw(bushTexture, x, y, w, h);
        spriteBatch.setColor(Color.WHITE);
    }

    @Override public void update(double timeSinceLastCall, double totalGameTime) {
    }

    public void setBerryAmount(double berryAmount) {
        this.berryAmount = berryAmount;
    }
}
