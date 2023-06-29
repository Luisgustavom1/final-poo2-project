package com.freewaygpt.game.components.QuestionModal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.freewaygpt.game.components.Camera;
import com.freewaygpt.game.design.Colors;
import com.freewaygpt.game.entity.FreewayGPTBuilder;
import com.freewaygpt.game.entity.Rendable;
import java.util.HashMap;

public class QuestionModal implements Rendable {
    private FreewayGPTBuilder game;
    private Color baseColor = Color.BLACK;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private Label question;
    private HashMap<Rectangle, Answer> answers;
    private String[] answersEnum = {"a)", "b)", "c)", "d)"};
    private int maxLineLength = 38;
    private Camera camera;
    private int correctAnswer;

    public QuestionModal(FreewayGPTBuilder game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        answers = new HashMap();

        camera = new Camera();
        camera.render();

        generateFont();
        createQuestionLabel();
        positionTheAnswers();
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
        question = new Question("", font);
        question.setPosition(128, Gdx.graphics.getHeight() - 156);
    }

    private void positionTheAnswers() {
        for (int c = 0; c < answersEnum.length; c++) {
            Answer answer = new Answer("", font, c);
            answer.setPosition(128, 128 + 64 * (4 - c));

            Rectangle rectangle = new Rectangle(128, 110 + 64 * (4 - c), 750, 30);

            answers.put(rectangle, answer);
        }
    }

    @Override
    public void render() {
        spriteBatch.begin();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        paintModal();

        shapeRenderer.end();
        spriteBatch.end();

        spriteBatch.begin();
        question.draw(spriteBatch, 1.0f);
        for (Answer answer : answers.values()) {
            answer.draw(spriteBatch, 1.0f);
        }
        spriteBatch.end();

        choiceAnswer();
    }

    private void paintModal() {
        int sizeBase = 128;
        float padding =  sizeBase/2;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Color modalColor = new Color(baseColor.r, baseColor.g, baseColor.b, 0.8f);
        shapeRenderer.setColor(modalColor);

        shapeRenderer.rect(padding, padding, Gdx.graphics.getWidth() - sizeBase, Gdx.graphics.getHeight() - sizeBase);

        for(Rectangle rectangle : answers.keySet()){
            shapeRenderer.setColor(0, 0, 0, 0);
            shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
    }

    public void writeQuestion(String questionText) {
        question.setText(breakTextByLength(questionText));
    }

    public void writeAnswers(String[] answersText, int correctAnswer) {
        this.correctAnswer = correctAnswer;
        int c = 0;

        for (Answer answer: answers.values()) {
            answer.setText(answersEnum[c] + " " + breakTextByLength(answersText[c]));
            c++;
        }
    }

    public String breakTextByLength(String text) {
        String textUpdated = "";

        for (int c = 0; c < text.length(); c++) {
            textUpdated += text.charAt(c);

            if ((c + 1) % maxLineLength == 0 && c != 0) {
                textUpdated += text.charAt(c) + "-\n";
            }
        }

        return textUpdated;
    }

    public void choiceAnswer(){
        InputProcessor inputProcessor = new InputProcessor(answers, camera, this);
        Gdx.input.setInputProcessor(inputProcessor);
    }

    public void answerQuestion(Rectangle answerSelected) {
        int answerSelectedIndex = searchAnswerIndexByRectangle(answerSelected);

        if (!(answerSelectedIndex == correctAnswer)) {
            game.getScore().reset();
        }

        game.pause();
    }

    public int searchAnswerIndexByRectangle(Rectangle rectangle) {
        int index = 0;
        int answerIndex = 0;

        for (Answer answer:answers.values()) {
            if (answers.get(rectangle).isEqual(answer)) {
                answerIndex = index;
            }

            index++;
        }

        return answerIndex;
    }

    @Override
    public void dispose() {
        font.dispose();
        spriteBatch.dispose();
        shapeRenderer.dispose();
        camera.dispose();
    }
}
