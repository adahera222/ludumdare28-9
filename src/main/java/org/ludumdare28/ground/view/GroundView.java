package org.ludumdare28.ground.view;

import org.ludumdare28.ground.Ground;

/**
 * Renders the ground.
 */
public class GroundView {

    // Point the camera is focused on
    private double cameraCenterX;
    private double cameraCenterY;

    // Ground we are looking at
    private Ground ground;

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
}
