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
import com.freewaygpt.game.entity.QuestionModel;
import com.freewaygpt.game.infra.ChatGPT;

public class FreewayGPT extends ApplicationAdapter {
	private CenterLine centerLineTop;
	private CenterLine centerLineBottom;
	private GameBuilder gameBuilder = new FreewayGPTBuilder();
	private GameDirector gameDirector = new GameDirector();
	private FreewayGPTBuilder game = (FreewayGPTBuilder) gameBuilder;
	private QuestionModal questionModal;

	@Override
	public void create() {
		centerLineTop = new CenterLine();
		centerLineBottom = new CenterLine();
		questionModal = new QuestionModal(game);
		gameDirector.buildFreewayGPT(gameBuilder);
	}

	@Override
	public void render() {
		ScreenUtils.clear(Colors.getStreet());

		centerLineTop.renderWithOffset(4);
		centerLineBottom.renderWithOffset(-4);

		game.render();

		if (game.isPaused) {
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

		if(game.getChicken().getY() < 0) game.getChicken().setY(0);
		if(game.getChicken().getY() > 640) game.getChicken().setY(640);

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
				pause();
			}
		}
	}

	public void pause() {
		game.pause();

		if (game.isPaused) {
			ChatGPT chatGPTService = new ChatGPT();
			QuestionModel questionModel = chatGPTService.generateQuestion("Gere uma pergunta técnica sobre o universo da programação, onde temos uma pergunta e 4 respostas onde apenas uma está correta, o resto tem alguns erros não tão evidentes, mas tem erros. A pergunta possui no máximo 100 caracteres e cada resposta no máximo 30 caracteres. Dê o JSON nesse formato { \"question\":  String, \"answers\": String[], \"correct_answer\": number}");

			questionModal.writeQuestion(questionModel.question);
			questionModal.writeAnswers(questionModel.answers, questionModel.correct_answer);
		}
	}

	@Override
	public void dispose() {
		game.dispose();
	}
}
