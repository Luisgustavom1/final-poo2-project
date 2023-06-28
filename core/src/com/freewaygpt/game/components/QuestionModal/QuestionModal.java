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
import com.freewaygpt.game.entity.Rendable;
import java.util.ArrayList;

public class QuestionModal implements Rendable {
    private Color baseColor = Color.BLACK;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private Label question;
    private ArrayList<Answer> answers;
    private String[] answersEnum = {"a)", "b)", "c)", "d)"};
    private int maxLineLength = 44;
    private ArrayList<Rectangle> rectangles;
    private Camera camera;

    public QuestionModal() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        answers = new ArrayList<Answer>();
        rectangles = new ArrayList<>();

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
            Answer answer = new Answer("", font);
            answer.setPosition(128, 128 + 64 * (4 - c));
            answers.add(c, answer);
            rectangles.add(new Rectangle(128, 110 + 64 * (4 - c), 750, 30));
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
        for (Answer answer : answers) {
            answer.draw(spriteBatch, 1.0f);
        }
        spriteBatch.end();
    }

    private void paintModal() {
        int sizeBase = 128;
        float padding =  sizeBase/2;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Color modalColor = new Color(baseColor.r, baseColor.g, baseColor.b, 0.8f);
        shapeRenderer.setColor(modalColor);

        shapeRenderer.rect(padding, padding, Gdx.graphics.getWidth() - sizeBase, Gdx.graphics.getHeight() - sizeBase);

        for(Rectangle rectangle : rectangles){
            shapeRenderer.setColor(0, 0, 0, 0);
            shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
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

            if ((c + 1) % maxLineLength == 0 && c != 0) {
                textUpdated += text.charAt(c) + "-\n";
            }
        }

        return textUpdated;
    }

    public void choiceAnswer(){
        InputProcessor inputProcessor = new InputProcessor(rectangles, camera);
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void dispose() {
        font.dispose();
        spriteBatch.dispose();
        shapeRenderer.dispose();
        camera.dispose();
    }
}