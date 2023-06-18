package com.freewaygpt.game.entity;

import com.freewaygpt.game.builders.GameBuilder;
import com.freewaygpt.game.components.*;

public class FreewayGPTBuilder implements GameBuilder, Rendable {
    private DisplayScoreComponent score;
    private Camera camera;
    private Frame frame;
    private Sidewalks sidewalks;
    private ChickenComponent chicken;
    private Cars cars;

    @Override
    public void setChicken(ChickenComponent chicken) {
        this.chicken = chicken;
    }

    @Override
    public void setCars(Cars cars) {
        this.cars = cars;
    }

    @Override
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    @Override
    public void setDisplayScore(DisplayScoreComponent displayScore) {
        this.score = displayScore;
    }

    @Override
    public void setSidewalks(Sidewalks sidewalks) {
        this.sidewalks = sidewalks;
    }

    public DisplayScoreComponent getScore() {
        return score;
    }

    public Camera getCamera() {
        return camera;
    }

    public Frame getFrame() {
        return frame;
    }

    public Sidewalks getSidewalks() {
        return sidewalks;
    }

    public ChickenComponent getChicken() {
        return chicken;
    }

    public Cars getCars() {
        return cars;
    }

    @Override
    public void render() {
        this.sidewalks.render();
        this.camera.render();
        this.frame.render();
        this.chicken.render();
//        this.score.render();
        this.cars.render();
    }

    @Override
    public void dispose() {
        this.chicken.dispose();
//        this.score.dispose();
        this.sidewalks.dispose();
        this.frame.dispose();
        this.camera.dispose();
    }
}
