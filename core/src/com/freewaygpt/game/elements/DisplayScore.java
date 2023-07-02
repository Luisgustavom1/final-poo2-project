package com.freewaygpt.game.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.freewaygpt.game.infra.ScoreDAO;
import com.freewaygpt.game.design.Colors;
import com.freewaygpt.game.entity.Score;

public class DisplayScore extends Score {
    private Label display;
    private BitmapFont customFont;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;


    public DisplayScore() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/minecraft.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 32;
        parameter.color = Colors.getPrimary();

        customFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = customFont;
        display = new Label(Integer.toString(ScoreDAO.getCurrentScore()), style);
    }

    public void render() {
        display.setPosition(Gdx.graphics.getWidth() / 3 * 2, Gdx.graphics.getHeight() - 38);
    };

    public void increment() {
        ScoreDAO.incrementScore();
        display.setText(Integer.toString(ScoreDAO.getCurrentScore()));
    }

    public void reset() {
        ScoreDAO.resetScore();
        display.setText(Integer.toString(ScoreDAO.getCurrentScore()));
    }

    public Label getDisplay() {
        return display;
    }

    public void dispose() {
        customFont.dispose();
        generator.dispose();
    }
}
