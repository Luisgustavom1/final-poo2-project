package com.freewaygpt.game.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.freewaygpt.game.design.Colors;

public class CenterLine extends ShapeRenderer {

    public void renderWithOffset(int offset) {
        begin(ShapeRenderer.ShapeType.Filled);
        setColor(Colors.getPrimary());
        rect(0, ((float) Gdx.graphics.getHeight() / 2) + offset, Gdx.graphics.getWidth(), 3);
        end();
    }
}
