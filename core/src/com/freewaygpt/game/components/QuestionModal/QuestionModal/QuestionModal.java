package com.freewaygpt.game.elements.QuestionModal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.freewaygpt.game.design.Colors;
import com.freewaygpt.game.entity.Rendable;

import java.util.ArrayList;

public class QuestionModal implements Rendable {
    private Color baseColor = Color.BLACK;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private Label question;
    private ArrayList<Label> answers;
    private String[] answersEnum = {"a)", "b)", "c)", "d)"};
    private int maxLineLength = 58;

    public QuestionModal() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        answers = new ArrayList<Label>();

        generateFont();
        createQuestionLabel();
        createAnswersLabel();
    }

    private void generateFont() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 32;
        parameter.color = Colors.getPrimary();

        font = fontGenerator.generateFont(parameter);
        fontGenerator.dispose();
    }

    private void createQuestionLabel() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        question = new Label("", style);
        question.setPosition(128, Gdx.graphics.getHeight() - 128);
    }

    private void createAnswersLabel() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;

        for (int c = 0; c < answersEnum.length; c++) {
            final Label answer = new Label("", style);
            answer.setPosition(128, 128 + 64 * c);

            answer.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Handle the click on the answer
                    System.out.println(answer.getText().toString());
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    // Set hover opacity
                    answer.getColor().a = 0.5f;
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    // Reset opacity on hover exit
                    answer.getColor().a = 1.0f;
                }
            });

            answers.add(c, answer);
        }
    }

    @Override
    public void render() {
        spriteBatch.begin();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        paintModal();
        question.draw(spriteBatch, 1.0f);
        for (Label answer : answers) {
            answer.draw(spriteBatch, 1.0f);
        }

        shapeRenderer.end();
        spriteBatch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void paintModal() {
        int sizeBase = 128;
        float padding =  sizeBase/2;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Color modalColor = new Color(baseColor.r, baseColor.g, baseColor.b, 0.8f);
        shapeRenderer.setColor(modalColor);

        shapeRenderer.rect(padding, padding, Gdx.graphics.getWidth() - sizeBase, Gdx.graphics.getHeight() - sizeBase);
    }

    public void writeQuestion(String questionText) {
        question.setText(breakTextByLength(questionText));
    }

    public void writeAnswers(String[] answersText) {
        for (int c = 0; c < answers.size(); c++) {
            answers.get(c).setText(answersEnum[c] + " " + breakTextByLength(answersText[c]));
        }
    }

    public String breakTextByLength(String text) {
        String textUpdated = "";

        for (int c = 0; c < text.length(); c++) {
            textUpdated += text.charAt(c);

            if (c % maxLineLength == 0 && c != 0) {
                textUpdated += text.charAt(c) + "_\n";
            }
        }

        return textUpdated;
    }

    @Override
    public void dispose() {
        font.dispose();
//        fontGenerator.dispose();
    }
}
