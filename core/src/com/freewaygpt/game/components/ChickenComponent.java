package com.freewaygpt.game.components;

import com.badlogic.gdx.Gdx;
import com.freewaygpt.game.entity.Chicken;
import com.freewaygpt.game.entity.Rendable;
import com.freewaygpt.game.observer.observers.Observer;

public class ChickenComponent extends Chicken implements Rendable, Observer {
    private Frame frame;

    public ChickenComponent(Frame frame) {
        super(480, 20, 48, 36);

        this.frame = frame;
    }

    public void moveDown(){
        super.moveDown();

        if(getY() < 0) setY(0);
    }

    public void moveUp(){
        super.moveUp();

        if(getY() > 640) setY(640);
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

    @Override
    public void notify(String event) {
        switch (event) {
            case "colision":
            case "end":
                this.setY(20);
            break;
        }
    }
}
