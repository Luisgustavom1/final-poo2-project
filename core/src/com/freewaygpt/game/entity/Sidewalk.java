package com.freewaygpt.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.freewaygpt.game.design.Colors;

public abstract class Sidewalk extends ShapeRenderer {
    private Color color = new Colors().getSidewalk();
    private ShapeType shapeType = ShapeRenderer.ShapeType.Filled;

    @Override
    public Color getColor() {
        return color;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    abstract public void render();
}