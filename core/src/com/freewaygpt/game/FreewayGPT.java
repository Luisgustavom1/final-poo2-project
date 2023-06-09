package com.freewaygpt.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.freewaygpt.game.builders.GameBuilder;
import com.freewaygpt.game.design.Colors;
import com.freewaygpt.game.director.GameDirector;
import com.freewaygpt.game.components.QuestionModal.QuestionModal;
import com.freewaygpt.game.elements.CenterLine;
import com.freewaygpt.game.entity.Car;
import com.freewaygpt.game.entity.FreewayGPTBuilder;
import com.freewaygpt.game.infra.ChatGPT;

public class FreewayGPT extends ApplicationAdapter {
	private CenterLine centerLineTop;
	private CenterLine centerLineBottom;
	private GameBuilder gameBuilder = new FreewayGPTBuilder();
	private GameDirector gameDirector = new GameDirector();
	private FreewayGPTBuilder game = (FreewayGPTBuilder) gameBuilder;
	private QuestionModal questionModal;
	private boolean shouldGenerateQuestion = false;

	@Override
	public void create() {
		centerLineTop = new CenterLine();
		centerLineBottom = new CenterLine();
		questionModal = new QuestionModal(game, new ChatGPT());
		gameDirector.buildFreewayGPT(gameBuilder);
	}

	@Override
	public void render() {
		ScreenUtils.clear(Colors.getStreet());

		centerLineTop.renderWithOffset(4);
		centerLineBottom.renderWithOffset(-4);

		game.render();

		if (game.isPaused) {
			if (shouldGenerateQuestion) {
				questionModal.generateQuestion();
				shouldGenerateQuestion = false;
			}

			questionModal.render();
			return;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			game.getChicken().moveDown();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			game.getChicken().moveUp();
		}

		if(game.getSidewalks().get("end").getYStart() - game.getChicken().getY() < 20){
			game.getEvents().notify("end");
			game.getScore().increment();
		}

		for(int i = 1; i <= 4; i++){
			if(TimeUtils.nanoTime() - Car.time() > Math.pow(10, (int)(Math.random()*(8)+9))){
				game.getCars().cars.add(new Car((int)Math.floor(Math.random() * (191 - 54 + 1) + 54)));
				game.getCars().cars.add(new Car((int)Math.floor(Math.random() * (199 - 328 + 1) + 199)));
				game.getCars().cars.add(new Car((int)Math.floor(Math.random() * (465 - 336 + 1) + 336)));
				game.getCars().cars.add(new Car((int)Math.floor(Math.random() * (570 - 473 + 1) + 473)));
			}
		}

		for(Iterator<Car> iterator = game.getCars().cars.iterator(); iterator.hasNext();){
			Car car = iterator.next();
			car.move();

			if(car.getX() < 0){
				iterator.remove();
			}
			if(car.isCrashed(game.getChicken())){
				game.getEvents().notify("colision");
				questionModal.renderWithDefaultQuestion("Estamos gerando sua pergunta...");
				shouldGenerateQuestion = true;
				pause();
			}
		}
	}

	public void pause() {
		game.pause();
	}

	@Override
	public void dispose() {
		game.dispose();
	}
}
