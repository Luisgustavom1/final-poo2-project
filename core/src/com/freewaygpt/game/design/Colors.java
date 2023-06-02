package com.freewaygpt.game.design;

import com.badlogic.gdx.graphics.Color;

public class Colors {
    static private Color sidewalk = new Color(148f/255f, 148f/255f, 148f/255f, 1);
    static private Color street = new Color(116f/255f, 116f/255f, 116f/255f, 1);
    static private Color primary = new Color(252f/255f, 224f/255f, 176f/255f, 1);
    static private Color black = new Color(0f/255f, 0f/255f, 0f/255f, 1);

    static public Color getSidewalk() {
        return sidewalk;
    }

    static public Color getStreet() {
        return street;
    }

    static public Color getPrimary() {
        return primary;
    }

    static public Color getBlack() {
        return black;
    }
}
