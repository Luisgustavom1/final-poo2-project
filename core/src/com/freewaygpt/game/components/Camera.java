package com.freewaygpt.game.components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.freewaygpt.game.entity.Rendable;

public class Camera extends OrthographicCamera implements Rendable {
    public Camera() {
        this.setToOrtho(false, 1024, 640);
    }

    @Override
    public void render() {
        this.update();
    }

    @Override
    public void dispose() {}
}
