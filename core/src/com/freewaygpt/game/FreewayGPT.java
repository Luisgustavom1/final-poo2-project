package com.freewaygpt.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.freewaygpt.game.builders.GameBuilder;
import com.freewaygpt.game.design.Colors;
import com.freewaygpt.game.director.GameDirector;
import com.freewaygpt.game.entity.*;

public class FreewayGPT extends ApplicationAdapter {
	private GameBuilder gameBuilder = new FreewayGPTBuilder();
	private GameDirector gameDirector = new GameDirector();
	FreewayGPTBuilder game = (FreewayGPTBuilder) gameBuilder;

	@Override
	public void create() {
		gameDirector.buildFreewayGPT(gameBuilder);
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

		game.render();

		// mechanics to chicken move
		// we can adjust velocity to up or down
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			game.getChicken().y -= 200 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			game.getChicken().y += 200 * Gdx.graphics.getDeltaTime();
		}

		// mechanics to reset position of chicken
		// when chicken arrive in sidewalk "end" or top
		// Observable, to verify if chicken arrive in sidewalk "end" or top
		Vector3 posEnd = new Vector3();
		Vector3 posChicken = new Vector3();
		posEnd.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - 36, 0);
		posChicken.set(game.getChicken().getX(), game.getChicken().getY(), 0);

		if(posEnd.y - posChicken.y < 20){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			game.getCamera().unproject(touchPos);
			game.getScore().increment();
			game.getChicken().setY(20);
		}

		// make sure the chicken stays within the screen bounds
		if(game.getChicken().getY() < 0) game.getChicken().setY(0);
		if(game.getChicken().getY() > 640) game.getChicken().setY(640);

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
				game.getCars().cars.add(new Car((int)Math.floor(Math.random() * (191 - 54 + 1) + 54)));
				game.getCars().cars.add(new Car((int)Math.floor(Math.random() * (199 - 328 + 1) + 199)));
				game.getCars().cars.add(new Car((int)Math.floor(Math.random() * (465 - 336 + 1) + 336)));
				game.getCars().cars.add(new Car((int)Math.floor(Math.random() * (570 - 473 + 1) + 473)));
			}
		}

		// moving the cars with Iterator
		for(Iterator<Car> iterator = game.getCars().cars.iterator(); iterator.hasNext();){
			Car car = iterator.next();
			car.x += 200 * Gdx.graphics.getDeltaTime();

			if(car.getX() < 0){
				iterator.remove();
			}
			if(car.overlaps(game.getChicken())){
//				game.getScore().reset();
				game.getChicken().setY(20);
			}
		}
	}

	@Override
	public void dispose() {
		game.dispose();
	}
}
