package com.freewaygpt.game.design;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.freewaygpt.game.entity.FreetypeFont;

public class MineFreetypeFont extends FreetypeFont {
    public MineFreetypeFont() {
        super("fonts/minecraft.ttf");
    }

    public BitmapFont generateFont() {
        return this.getGenerator().generateFont(this.getParameter());
    }
}
