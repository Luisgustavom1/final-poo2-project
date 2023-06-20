package com.freewaygpt.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.freewaygpt.game.design.Colors;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;

public abstract class Sidewalk extends ShapeRenderer {
    private Color color = Colors.getSidewalk();
    private ShapeType shapeType = ShapeRenderer.ShapeType.Filled;
    private ShapeType borderTop = ShapeRenderer.ShapeType.Filled;
    private ShapeType borderBottom = ShapeRenderer.ShapeType.Filled;
    private float yStart;


    @Override
    public Color getColor() {
        return color;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public ShapeType getBorderTop() {
        return borderTop;
    }

    public ShapeType getBorderBottom() {
        return borderBottom;
    }

    abstract public void render();

    public float getYStart() {
        return yStart;
    }

    public void setYStart(float y) {
        this.yStart = y;
    }
}