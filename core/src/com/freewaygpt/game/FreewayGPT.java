package com.freewaygpt.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.freewaygpt.game.design.Colors;
import com.freewaygpt.game.design.MineFreetypeFont;
import com.freewaygpt.game.elements.Assets;
import com.freewaygpt.game.elements.DisplayScore;
import com.freewaygpt.game.elements.EndPoint;
import com.freewaygpt.game.elements.InitialPoint;
import com.freewaygpt.game.entity.Sidewalk;

public class FreewayGPT extends ApplicationAdapter {
	private DisplayScore score;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private HashMap<String, Sidewalk> sidewalks = new HashMap<String, Sidewalk>();
	private Texture chickenImage;
	private Rectangle chicken;
	private Texture carImage;
	private ArrayList<Rectangle> cars = new ArrayList<>();
//	private SpriteBatch scoreBatch = new SpriteBatch();

	@Override
	public void create() {
		chickenImage = new Assets().chickenImageCreate();
		chicken = new Assets().chickenCreate();

		// Config the camera to use a SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 640);
		batch = new SpriteBatch();

		sidewalks.put("init", new InitialPoint());
		sidewalks.put("end", new EndPoint());

		// init config to car functionally
		carImage = new Assets().carImageCreate();
		cars.add(new Assets().carCreate(107));
		cars.add(new Assets().carCreate(174));
		cars.add(new Assets().carCreate(241));
		cars.add(new Assets().carCreate(330));
		cars.add(new Assets().carCreate(397));
		cars.add(new Assets().carCreate(464));
		cars.add(new Assets().carCreate(531));

		this.score = new DisplayScore(new MineFreetypeFont());
	}

	@Override
	public void render() {
		ScreenUtils.clear(Colors.getStreet());

		this.score.render();

//		scoreBatch.begin();
//		this.score.getDisplay().draw(scoreBatch, 1f);
//		scoreBatch.end();

		ShapeRenderer centerLineTop = new ShapeRenderer();
		centerLineTop.begin(ShapeRenderer.ShapeType.Filled);
		centerLineTop.setColor(Colors.getPrimary());
		centerLineTop.rect(0, ((float) Gdx.graphics.getHeight() / 2) - 4, Gdx.graphics.getWidth(), 3);
		centerLineTop.end();

		ShapeRenderer centerLineBottom = new ShapeRenderer();
		centerLineBottom.begin(ShapeRenderer.ShapeType.Filled);
		centerLineBottom.setColor(Colors.getPrimary());
		centerLineBottom.rect(0, ((float) Gdx.graphics.getHeight() / 2) + 4, Gdx.graphics.getWidth(), 3);
		centerLineBottom.end();

		sidewalks.get("init").render();
		sidewalks.get("end").render();

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		// Draw the chicken in our rectangle
		batch.begin();
		batch.draw(chickenImage, chicken.x, chicken.y);
		for(Rectangle car: cars){
			batch.draw(carImage, car.x, car.y);
		}
		batch.end();

		// mechanics to chicken move
		// we can adjust velocity to up or down
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			chicken.y -= 200 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			chicken.y += 200 * Gdx.graphics.getDeltaTime();
		}

		// mechanics to reset position of chicken
		// when chicken arrive in sidewalk "end" or top
		// Observable, to verify if chicken arrive in sidewalk "end" or top
		Vector3 posEnd = new Vector3();
		Vector3 posChicken = new Vector3();
		posEnd.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - 36, 0);
		posChicken.set(chicken.x, chicken.y, 0);

		if(posEnd.y - posChicken.y < 20){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			score.increment();
			chicken.y = 20;
		}

		// make sure the chicken stays within the screen bounds
		if(chicken.y < 0) chicken.y = 0;
		if(chicken.y > 640) chicken.y = 640;

		// we use to create a new car
		for(int i = 1; i <= 4; i++){
			if(TimeUtils.nanoTime() - Assets.getTime() > Math.pow(10, (int)(Math.random()*(10)+9))){
				cars.add(new Assets().carCreate(i * 100));
				cars.add(new Assets().carCreate(i * 200));
			}
		}

		// moving the cars with Iterator
		for(Iterator<Rectangle> iterator = cars.iterator(); iterator.hasNext();){
			Rectangle car = iterator.next();
			car.x += 200 * Gdx.graphics.getDeltaTime();

			if(car.x < 0){
				iterator.remove();
			}
			if(car.overlaps(chicken)){
				score.reset();
				chicken.y = 20;
			}
		}
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
