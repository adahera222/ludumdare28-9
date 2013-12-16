package org.ludumdare28;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 *
 */
public class SkinFactory {

    public static Skin createSkin() {
        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
        Skin skin = new Skin();

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Store the default libgdx font under the name "default".
        skin.add("default", new BitmapFont());

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up      = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down    = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over    = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font    = skin.getFont("default");
        skin.add("default", textButtonStyle);

        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = skin.newDrawable("white", Color.DARK_GRAY);
        sliderStyle.knob  = skin.newDrawable("white", Color.LIGHT_GRAY);
        sliderStyle.knobAfter = skin.newDrawable("white", Color.DARK_GRAY);
        sliderStyle.knobBefore = skin.newDrawable("white", Color.WHITE);
        skin.add("default-horizontal", sliderStyle);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.background = skin.newDrawable("white", Color.BLACK);
        labelStyle.font       = skin.getFont("default");
        labelStyle.fontColor  = Color.WHITE;
        skin.add("default", labelStyle);

        return skin;
    }
}
