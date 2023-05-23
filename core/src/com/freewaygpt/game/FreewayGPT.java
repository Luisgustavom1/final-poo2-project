package com.freewaygpt.game;

import java.util.HashMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.freewaygpt.game.design.Colors;
import com.freewaygpt.game.elements.Assets;
import com.freewaygpt.game.elements.EndPoint;
import com.freewaygpt.game.elements.InitialPoint;
import com.freewaygpt.game.entity.Sidewalk;

public class FreewayGPT extends ApplicationAdapter {
	private Colors colors;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private HashMap<String, Sidewalk> sidewalks = new HashMap<String, Sidewalk>();
	private Texture chickenImage;
	private Rectangle chicken;

	@Override
	public void create() {
		colors = new Colors();
		chickenImage = new Assets().chickenImageCreate();
		chicken = new Assets().chickenCreate();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 520);
		batch = new SpriteBatch();

		sidewalks.put("init", new InitialPoint());
		sidewalks.put("end", new EndPoint());
	}

	@Override
	public void render() {
		ScreenUtils.clear(colors.getStreet());

		sidewalks.get("init").render();
		sidewalks.get("end").render();

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the chicken and
		batch.begin();
		batch.draw(chickenImage, chicken.x, chicken.y);
		batch.end();

		// process user input
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			chicken.y = 20;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) chicken.y -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) chicken.y += 200 * Gdx.graphics.getDeltaTime();

		// make sure the chicken stays within the screen bounds
		if(chicken.y < 0) chicken.y = 0;
		if(chicken.y > 436) chicken.y = 436;
	}

	@Override
	public void dispose() {
		chickenImage.dispose();

		for (Sidewalk sidewalk:sidewalks.values()) {
			sidewalk.dispose();
		}

		batch.dispose();
	}
}
