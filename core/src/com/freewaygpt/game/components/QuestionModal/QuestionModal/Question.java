package com.freewaygpt.game.components.QuestionModal.QuestionModal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Question extends Label {
    public Question(String text, BitmapFont font) {
        super(text, new Label.LabelStyle(font, Color.WHITE));
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }
}
