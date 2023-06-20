package com.freewaygpt.game.components;

import com.freewaygpt.game.elements.EndPoint;
import com.freewaygpt.game.elements.InitialPoint;
import com.freewaygpt.game.entity.Rendable;
import com.freewaygpt.game.entity.Sidewalk;

import java.util.HashMap;

public class Sidewalks extends HashMap<String, Sidewalk> implements Rendable {
    public Sidewalks() {
        this.put("init", new InitialPoint());
        this.put("end", new EndPoint());
    }


    @Override
    public void render() {
        this.get("init").render();
        this.get("end").render();
    }

    @Override
    public void dispose() {
        for (Sidewalk sidewalk:this.values()) {
            sidewalk.dispose();
        }
    }
}
