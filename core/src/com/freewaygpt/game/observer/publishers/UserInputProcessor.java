package com.freewaygpt.game.observer.publishers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputProcessor extends InputAdapter {
    @Override
    public boolean keyDown(int keycode) {
        // Handle key down events
        if (keycode == Keys.SPACE) {
            // Example: Space key is pressed
            return true; // Return true to indicate the event is handled
        }
        return false; // Return false if the event is not handled
    }

    @Override
    public boolean keyUp(int keycode) {
        // Handle key up events
        return false;
    }

    // Override other necessary methods such as touch events, mouse events, etc.
}
