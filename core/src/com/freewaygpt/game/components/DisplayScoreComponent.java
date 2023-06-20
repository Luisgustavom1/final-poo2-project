package com.freewaygpt.game.components;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.freewaygpt.game.elements.DisplayScore;
import com.freewaygpt.game.entity.Rendable;

public class DisplayScoreComponent extends DisplayScore implements Rendable {
    private Stage stage;

    public DisplayScoreComponent() {
        stage = new Stage();
    }

    @Override
    public void render() {
        super.render();
        stage.addActor(this.getDisplay());
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}
