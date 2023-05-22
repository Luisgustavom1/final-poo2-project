package com.freewaygpt.game.elements;

import com.badlogic.gdx.Gdx;
import com.freewaygpt.game.entity.Sidewalk;

// TODO: think better name
public class EndPoint extends Sidewalk {
    public void render() {
        this.begin(this.getShapeType());
        this.setColor(this.getColor());
        this.rect(0, Gdx.graphics.getHeight() - 64, Gdx.graphics.getWidth(), 64);
        this.end();
    }
}
