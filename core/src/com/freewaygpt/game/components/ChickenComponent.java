package com.freewaygpt.game.components;

import com.freewaygpt.game.entity.Chicken;
import com.freewaygpt.game.entity.Rendable;

public class ChickenComponent extends Chicken implements Rendable {
    private Frame frame;

    public ChickenComponent(Frame frame) {
        super(480, 20, 48, 36);

        this.frame = frame;
    }

    @Override
    public void render() {
        frame.begin();
        frame.draw(this.getImage(), this.getX(), this.getY());
        frame.end();
    }

    @Override
    public void dispose() {
        this.getImage().dispose();
    }
}
