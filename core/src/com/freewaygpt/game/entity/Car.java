package com.freewaygpt.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class Car extends Rectangle{
    public float x;
    private float y;
    private float width;
    private float height;
    private Texture image;
    private static long time;

    public Car(float y){
        this.y = y;
        x = 0;
        width = 58;
        height = 32;
        createImage();
        time = TimeUtils.nanoTime();
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
        image = new Texture(Gdx.files.internal("car.png"));
    }

    public static long time(){
        return time;
    }

    public boolean overlaps(Chicken chicken){
        return x < chicken.getX() + chicken.width && x + width > chicken.getX() && y < chicken.getY() + chicken.height && y + height > chicken.getY();
    }

    public void move(){
        x += 200 * Gdx.graphics.getDeltaTime();
    }
}
