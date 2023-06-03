package com.freewaygpt.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.freewaygpt.game.design.Colors;
import com.freewaygpt.game.elements.DisplayScore;
import com.freewaygpt.game.elements.EndPoint;
import com.freewaygpt.game.elements.InitialPoint;
import com.freewaygpt.game.entity.Car;
import com.freewaygpt.game.entity.Chicken;
import com.freewaygpt.game.entity.Sidewalk;

public class FreewayGPT extends ApplicationAdapter {
	private DisplayScore score;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private HashMap<String, Sidewalk> sidewalks = new HashMap<String, Sidewalk>();
	private Chicken chicken;
	private ArrayList<Car> cars = new ArrayList<>();
	private Stage stage;

	@Override
	public void create() {
		chicken = new Chicken(480, 20, 48, 36);

		// Config the camera to use a SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 640);
		batch = new SpriteBatch();

		sidewalks.put("init", new InitialPoint());
		sidewalks.put("end", new EndPoint());

		// init config to car functionally
		cars.add(new Car(107));
		cars.add(new Car(174));
		cars.add(new Car(241));
		cars.add(new Car(330));
		cars.add(new Car(397));
		cars.add(new Car(464));
		cars.add(new Car(531));

		stage = new Stage();
		score = new DisplayScore();
		score.render();
		stage.addActor(score.getDisplay());
	}

	@Override
	public void render() {
		ScreenUtils.clear(Colors.getStreet());

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
		batch.draw(chicken.getImage(), chicken.getX(), chicken.getY());

		for(Car car: cars){
			batch.draw(car.getImage(), car.getX(), car.getY());
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
		posChicken.set(chicken.getX(), chicken.getY(), 0);

		if(posEnd.y - posChicken.y < 20){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			score.increment();
			chicken.setY(20);
		}

		// make sure the chicken stays within the screen bounds
		if(chicken.getY() < 0) chicken.setY(0);
		if(chicken.getY() > 640) chicken.setY(640);

		/**
		 * we use to create a new car
		 * 54 - 191
		 * 199 - 328
		 * 336 - 465
		 * 473 - 570
		 * (int)Math.floor(Math.random() * (max - min + 1) + min)
		 */
		for(int i = 1; i <= 4; i++){
			if(TimeUtils.nanoTime() - Car.time() > Math.pow(10, (int)(Math.random()*(8)+9))){
				cars.add(new Car((int)Math.floor(Math.random() * (191 - 54 + 1) + 54)));
				cars.add(new Car((int)Math.floor(Math.random() * (199 - 328 + 1) + 199)));
				cars.add(new Car((int)Math.floor(Math.random() * (465 - 336 + 1) + 336)));
				cars.add(new Car((int)Math.floor(Math.random() * (570 - 473 + 1) + 473)));
			}
		}

		// moving the cars with Iterator
		for(Iterator<Car> iterator = cars.iterator(); iterator.hasNext();){
			Car car = iterator.next();
			car.x += 200 * Gdx.graphics.getDeltaTime();

			if(car.getX() < 0){
				iterator.remove();
			}
			if(car.overlaps(chicken)){
				score.reset();
				chicken.setY(20);
			}
		}

		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		chicken.getImage().dispose();
		stage.dispose();
		score.dispose();

		for (Sidewalk sidewalk:sidewalks.values()) {
			sidewalk.dispose();
		}

		batch.dispose();
	}
}
