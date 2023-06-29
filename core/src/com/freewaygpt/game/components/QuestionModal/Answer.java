package com.freewaygpt.game.components.QuestionModal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Answer extends Label {
    private int id;

    public Answer(String text, BitmapFont font, int id) {
        super(text, new Label.LabelStyle(font, Color.WHITE));

        this.id = id;

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(getText().toString());
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                getColor().a = 0.5f;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                getColor().a = 1.0f;
            }
        });
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

    public boolean isEqual(Answer answerToCompare) {
        return answerToCompare.getId() == id;
    }

    private int getId() {
        return id;
    }
}
