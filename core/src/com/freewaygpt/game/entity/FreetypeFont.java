package com.freewaygpt.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public abstract class FreetypeFont {
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public FreetypeFont(String path) {
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    }

    public abstract BitmapFont generateFont();

    public FreeTypeFontGenerator getGenerator() {
        return generator;
    }

    public FreeTypeFontGenerator.FreeTypeFontParameter getParameter() {
        return parameter;
    }
}
