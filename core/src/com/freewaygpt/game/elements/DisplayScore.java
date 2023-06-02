package com.freewaygpt.game.elements;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.freewaygpt.game.entity.FreetypeFont;
import com.freewaygpt.game.entity.Score;

public class DisplayScore extends Score {
    private FreetypeFont freetypeFont;
    private Label display;

    public DisplayScore(FreetypeFont freetypeFont) {
        this.freetypeFont = freetypeFont;
    }

    public void render() {
        this.freetypeFont.getParameter().size = 24;

        Label.LabelStyle fontStyle = new Label.LabelStyle();
        fontStyle.font = this.freetypeFont.generateFont();
        this.display = new Label(Integer.toString(this.getScore()), fontStyle);

        this.freetypeFont.getGenerator().dispose();
    };

    public Label getDisplay() {
        return display;
    }
}
