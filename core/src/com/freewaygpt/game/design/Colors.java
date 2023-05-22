package com.freewaygpt.game.design;

import com.badlogic.gdx.graphics.Color;

public class Colors {
    private Color sidewalk = new Color(148f/255f, 148f/255f, 148f/255f, 1);
    private Color street = new Color(116f/255f, 116f/255f, 116f/255f, 1);

    private Color primary = new Color(252f/255f, 224f/255f, 176f/255f, 1);

    public Color getSidewalk() {
        return sidewalk;
    }

    public Color getStreet() {
        return street;
    }

    public Color getPrimary() {
        return primary;
    }
}
