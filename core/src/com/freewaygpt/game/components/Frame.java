package com.freewaygpt.game.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.freewaygpt.game.entity.Rendable;

public class Frame extends SpriteBatch implements Rendable {
    private Camera camera;

    public Frame(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void render() {
        this.setProjectionMatrix(camera.combined);
    }

    public void dispose() {
        super.dispose();
    }
}
