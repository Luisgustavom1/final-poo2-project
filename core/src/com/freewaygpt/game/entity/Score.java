package com.freewaygpt.game.entity;

public class Score {
    int score = 0;

    public void increment() {
        this.score += 1;
    }

    public void reset() {
        this.score = 0;
    }

    public int getScore() {
        return this.score;
    }
}
