package com.freewaygpt.game.components.QuestionModal;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.freewaygpt.game.components.Camera;
import java.util.ArrayList;

public class InputProcessor extends InputAdapter {
    private ArrayList<Rectangle> rectangles;
    private Camera camera;

    public InputProcessor(ArrayList<Rectangle> rectangles, Camera camera) {
        this.rectangles = rectangles;
        this.camera = camera;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPoint = new Vector3(screenX, screenY, 0);
        camera.unproject(touchPoint);

        for (Rectangle rectangle : rectangles) {
            if (rectangle.contains(touchPoint.x, touchPoint.y)) {
                System.out.println(rectangle);
            }
        }

        return true;
    }
}
