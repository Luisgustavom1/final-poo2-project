package com.freewaygpt.game.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class Assets {
    private Texture chickenImage;
    private Texture carImage;
    private Rectangle car;
    private Rectangle chicken;
    public static long time;

    /**
     * Create an object that represent a chicken image.
     * @return object Texture
     */
    public Texture chickenImageCreate(){
        chickenImage = new Texture(Gdx.files.internal("galinha.png"));
        return chickenImage;
    }

    /**
     * Create a logical part that represent the chicken.
     * Logically the chicken represent a rectangle in screen.
     * We center horizontally in axis x and
     * @return object Rectangle
     */
    public Rectangle chickenCreate(){
        chicken = new Rectangle();
        chicken.x = 1024/2 - 64/2;
        chicken.y = 20;
        chicken.height = 48;
        chicken.width = 36;
        return chicken;
    }

    /**
     * Create an object that represent a car image.
     * @return object Texture
     */
    public Texture carImageCreate(){
        return new Texture(Gdx.files.internal("car.png"));
    }

    /**
     * Create a logical part that represent the car.
     * Logically the car represent a rectangle in screen.
     * @return object Rectangle
     */
    public Rectangle carCreate(float y){
        time = TimeUtils.nanoTime();
        return new Rectangle(0, y, 58, 32);
    }

    public static long getTime() {
        return time;
    }
}
