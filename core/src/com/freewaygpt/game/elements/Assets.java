package com.freewaygpt.game.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Assets {
    private Texture chickenImage;
    private Rectangle chicken;

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
        chicken.height = 84;
        chicken.width = 64;
        return chicken;
    }
}
