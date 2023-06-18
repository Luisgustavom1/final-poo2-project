package com.freewaygpt.game.components;

import com.freewaygpt.game.elements.EndPoint;
import com.freewaygpt.game.elements.InitialPoint;
import com.freewaygpt.game.entity.Rendable;
import com.freewaygpt.game.entity.Sidewalk;

import java.util.HashMap;

public class Sidewalks implements Rendable {
    private HashMap<String, Sidewalk> sidewalks = new HashMap<String, Sidewalk>();

    public Sidewalks() {
        sidewalks.put("init", new InitialPoint());
        sidewalks.put("end", new EndPoint());
    }


    @Override
    public void render() {
        sidewalks.get("init").render();
        sidewalks.get("end").render();
    }

    @Override
    public void dispose() {
        for (Sidewalk sidewalk:sidewalks.values()) {
            sidewalk.dispose();
        }
    }
}
