package com.freewaygpt.game.components.QuestionModal;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.freewaygpt.game.components.Camera;
import java.util.HashMap;

public class InputProcessor extends InputAdapter {
    private HashMap<Rectangle, Answer> answers;
    private Camera camera;
    private QuestionModal questionModal;

    public InputProcessor(HashMap<Rectangle, Answer> answers, Camera camera, QuestionModal questionModal) {
        this.answers = answers;
        this.camera = camera;
        this.questionModal = questionModal;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPoint = new Vector3(screenX, screenY, 0);
        camera.unproject(touchPoint);

        for (Rectangle answerSelected : answers.keySet()) {
            if (answerSelected.contains(touchPoint.x, touchPoint.y)) {
                questionModal.answerQuestion(answerSelected);
            }
        }

        return true;
    }
}
