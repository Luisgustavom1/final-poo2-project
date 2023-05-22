package com.freewaygpt.game;

import java.util.HashMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.freewaygpt.game.design.Colors;
import com.freewaygpt.game.elements.EndPoint;
import com.freewaygpt.game.elements.InitialPoint;
import com.freewaygpt.game.entity.Sidewalk;

public class FreewayGPT extends ApplicationAdapter {
	private Colors colors;
	private SpriteBatch batch;
	private HashMap<String, Sidewalk> sidewalks = new HashMap<String, Sidewalk>();

	@Override
	public void create() {
		colors = new Colors();

		sidewalks.put("init", new InitialPoint());
		sidewalks.put("end", new EndPoint());
	}

	@Override
	public void render() {
		ScreenUtils.clear(colors.getStreet());

		sidewalks.get("init").render();
		sidewalks.get("end").render();
	}

	@Override
	public void dispose() {
		batch.dispose();

		for (Sidewalk sidewalk:sidewalks.values()) {
			sidewalk.dispose();
		}
	}
}
