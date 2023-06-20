package com.freewaygpt.game.director;

import com.freewaygpt.game.builders.GameBuilder;
import com.freewaygpt.game.components.*;
import com.freewaygpt.game.entity.FreewayGPTBuilder;

public class GameDirector {
    public void buildFreewayGPT(GameBuilder gameBuilder) {
        FreewayGPTBuilder freewayGPTBuilder = (FreewayGPTBuilder)gameBuilder;

        gameBuilder.setDAO();
        gameBuilder.setCamera(new Camera());
        gameBuilder.setFrame(new Frame(freewayGPTBuilder.getCamera()));
        gameBuilder.setChicken(new ChickenComponent(freewayGPTBuilder.getFrame()));
        gameBuilder.setCars(new Cars(freewayGPTBuilder.getFrame()));
        gameBuilder.setSidewalks(new Sidewalks());
        gameBuilder.setDisplayScore(new DisplayScoreComponent());
    }
}
