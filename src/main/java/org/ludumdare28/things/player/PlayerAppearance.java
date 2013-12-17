package org.ludumdare28.things.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.ludumdare28.Pic;
import org.ludumdare28.things.Appearance;
import org.ludumdare28.things.snake.Snake;

/**
 * Author: Shiera
 */
public class PlayerAppearance implements Appearance{

        private Player thisPlayer;
        private  int pictureFrame;
        private int frameNumber;
        private double lastX;
        private double lastY;




        public PlayerAppearance(Player thisPlayer) {
            this.thisPlayer = thisPlayer;
            pictureFrame = 0;
            frameNumber = 0;
            lastX = thisPlayer.getX();
            lastY = thisPlayer.getY();
        }

        @Override
        public void render(TextureAtlas textureAtlas, SpriteBatch spriteBatch, float screenX, float screenY) {
            //dead
            if (!thisPlayer.isAlive()){
                drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.DEAD_HUMAN.getTexture(textureAtlas, pictureFrame));

            }
            // PICTURE OF EATING
            else if (thisPlayer.isEating()){
                drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.EATING_HUMAN.getTexture(textureAtlas, pictureFrame));
            }
            // weak (=tired, wounded, hungry)
            else if (thisPlayer.isWeak()){
                drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.WOUNDED_HUMAN.getTexture(textureAtlas, pictureFrame));

            }
            // normal
            else{
                drawPic(textureAtlas, spriteBatch, screenX, screenY, Pic.HUMAN.getTexture(textureAtlas, pictureFrame));

            }


        }

        @Override
        public void update(double timeSinceLastCall, double totalGameTime) {
            if (frameNumber >= 5){


                frameNumber = 0;
                if ((thisPlayer.getY()!= lastY) || thisPlayer.getX() != lastX) {
                    pictureFrame++;

                }

                if (pictureFrame > 4) pictureFrame = 0;
            }
            lastX = thisPlayer.getX();
            lastY = thisPlayer.getY();
            frameNumber ++;

        }

        private void drawPic(TextureAtlas textureAtlas, SpriteBatch spriteBatch, float screenX, float screenY, TextureRegion texture) {

            float sizeX = texture.getRegionWidth();
            float sizeY = texture.getRegionHeight();
            float y = screenY ;

            if (!thisPlayer.isMovingLeft()){
                float x = screenX - sizeX/2;
                spriteBatch.draw(texture, x, y, sizeX, sizeY);
            }
            else{
                float x = screenX + sizeX/2;
                spriteBatch.draw(texture, x, y, 0, 0, sizeX, sizeY, -1, 1, 0);
            }



        }
}
