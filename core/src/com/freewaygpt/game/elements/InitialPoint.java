package com.freewaygpt.game.elements;

import com.badlogic.gdx.Gdx;
import com.freewaygpt.game.design.Colors;
import com.freewaygpt.game.entity.Sidewalk;

public class InitialPoint extends Sidewalk {
    public void render() {
        this.begin(this.getShapeType());
        this.setColor(this.getColor());
        this.rect(0, 0, Gdx.graphics.getWidth(), 36);
        this.end();

        this.begin(this.getBorderBottom());
        this.setColor(Colors.getBlack());
        this.rect(0, 0, Gdx.graphics.getWidth(), 2);
        this.end();

        this.begin(this.getBorderTop());
        this.setColor(Colors.getBlack());
        this.rect(0, 34, Gdx.graphics.getWidth(), 2);
        this.end();
    }
}
