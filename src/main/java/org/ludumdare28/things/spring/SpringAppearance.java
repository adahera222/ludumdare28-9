package org.ludumdare28.things.spring;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.ludumdare28.Pic;
import org.ludumdare28.things.Appearance;

/**
 * Author: Shiera
 */
public class SpringAppearance implements Appearance {
    private double frameNumber = 0;
    private boolean water1On = false;
    private boolean water2On = false;
    private boolean water3On = false;
    private boolean water4On = false;

    @Override
    public void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch, float screenX, float screenY) {
        drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.SPRING_BASE.getTexture(textureAtlas, 0));
        if (water1On) drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.SPRING_WATER.getTexture(textureAtlas,0));
        if (water2On) drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.SPRING_WATER.getTexture(textureAtlas,1));
        if (water3On) drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.SPRING_WATER.getTexture(textureAtlas,2));
        if (water4On) drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.SPRING_WATER.getTexture(textureAtlas,3));
    }


    @Override
    public void update(double lastFrameDurationSeconds, double totalGameTime) {
        if (frameNumber >= 8){
           if (Math.random() > 0.6) water1On = true;
           else water1On = false;

           if (Math.random() > 0.6) water2On = true;
           else water2On = false;

           if (Math.random() > 0.6) water3On = true;
           else water3On = false;

           if (Math.random() > 0.6) water4On = true;
           else water4On = false;
            frameNumber = 0;
        }

        frameNumber ++;

    }

    private void drawPic(TextureAtlas textureAtlas, SpriteBatch spriteBatch, float screenX, float screenY, TextureRegion texture) {

        float sizeX = texture.getRegionWidth()*1.5f;
        float sizeY = texture.getRegionHeight()*1.5f;
        float x = screenX - sizeX/2;
        float y = screenY + sizeY/6;
        spriteBatch.draw(texture, x, y, sizeX, sizeY);
    }

}
