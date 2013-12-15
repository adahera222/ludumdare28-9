package org.ludumdare28.view.screens;

import org.ludumdare28.Updating;
import org.ludumdare28.View;

/**
 *
 */
public interface Screen extends View, Updating {

    void open();

    void render();

    void close();
}
