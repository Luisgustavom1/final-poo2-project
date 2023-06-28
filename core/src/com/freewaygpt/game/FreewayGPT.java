package com.freewaygpt.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.freewaygpt.game.builders.GameBuilder;
import com.freewaygpt.game.design.Colors;
import com.freewaygpt.game.director.GameDirector;
import com.freewaygpt.game.components.QuestionModal.QuestionModal;
import com.freewaygpt.game.entity.Car;
import com.freewaygpt.game.entity.FreewayGPTBuilder;
import com.freewaygpt.game.observer.publishers.UserInputProcessor;

public class FreewayGPT extends ApplicationAdapter {
	private ShapeRenderer centerLineTop;
	private ShapeRenderer centerLineBottom;
	private Boolean isPaused = false;
	private GameBuilder gameBuilder = new FreewayGPTBuilder();
	private GameDirector gameDirector = new GameDirector();
	private FreewayGPTBuilder game = (FreewayGPTBuilder) gameBuilder;
	private QuestionModal questionModal;

	@Override
	public void create() {
		centerLineTop = new ShapeRenderer();
		centerLineBottom = new ShapeRenderer();
		questionModal = new QuestionModal();
		gameDirector.buildFreewayGPT(gameBuilder);

		Gdx.input.setInputProcessor(new UserInputProcessor());
	}

	@Override
	public void render() {
		ScreenUtils.clear(Colors.getStreet());

		centerLineTop.begin(ShapeRenderer.ShapeType.Filled);
		centerLineTop.setColor(Colors.getPrimary());
		centerLineTop.rect(0, ((float) Gdx.graphics.getHeight() / 2) - 4, Gdx.graphics.getWidth(), 3);
		centerLineTop.end();

		centerLineBottom.begin(ShapeRenderer.ShapeType.Filled);
		centerLineBottom.setColor(Colors.getPrimary());
		centerLineBottom.rect(0, ((float) Gdx.graphics.getHeight() / 2) + 4, Gdx.graphics.getWidth(), 3);
		centerLineBottom.end();

		game.render();

		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			// Pause the game when the 'P' key is pressed
			pause();
		}

		if (isPaused) {
			String[] answers = {"map", "reduce", "filter",  "forEach"};

			questionModal.render();
			questionModal.writeQuestion("Qual dos seguintes metodos e utilizado para aplicar uma funcao a cada elemento de uma lista em programacao funcional?");
			questionModal.writeAnswers(answers);
			questionModal.choiceAnswer();
			return;
		}

		/**
		 * Mechanics to chicken move
		 * we can adjust velocity to up or down
		 */
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			game.getChicken().y -= 200 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			game.getChicken().y += 200 * Gdx.graphics.getDeltaTime();
		}

		if(game.getSidewalks().get("end").getYStart() - game.getChicken().getY() < 20){
			game.getEvents().notify("end");
			game.getScore().increment();
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
				game.getEvents().notify("colision");
				game.getScore().reset();
			}
		}
	}

	public void pause() {
		this.isPaused = !this.isPaused;

		if (isPaused) {
//			OpenAiService service = new OpenAiService("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");
//			CompletionRequest completionRequest = CompletionRequest.builder()
//					.prompt("Gerar uma pergunta sobre programação no tema de programação funcional, onde temos uma pergunta e 4 possíveis respostas onde apenas uma está correta, o resto tem alguns erros não tão evidentes, mas tem erros.\n" +
//							"\n" +
//							"Gerar isso em um formato JSON, para ser agnóstico entre linguagens de programação.")
//					.model("gpt-3.5-turbo")
//					.echo(true)
//					.build();
//
//			for (CompletionChoice a:service.createCompletion(completionRequest).getChoices()) {
//				System.out.println(a);
//			}
		}
	}

	@Override
	public void dispose() {
		game.dispose();
	}
}
