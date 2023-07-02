package com.freewaygpt.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Chicken extends Rectangle {
    private Texture image;

    public Chicken(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        createImage();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Texture getImage() {
        return image;
    }

    public void createImage() {
        image = new Texture(Gdx.files.internal("galinha.png"));
    }

    public void moveDown(){
        y -= 200 * Gdx.graphics.getDeltaTime();
    }

    public void moveUp(){
        y += 200 * Gdx.graphics.getDeltaTime();
    }
}
