package com.freewaygpt.game.builders;

import com.freewaygpt.game.components.*;

public interface GameBuilder {
    void setDAO();
    void setChicken(ChickenComponent chicken);
    void setCars(Cars cars);
    void setCamera(Camera camera);
    void setFrame(Frame frame);
    void setDisplayScore(DisplayScoreComponent displayScore);
    void setSidewalks(Sidewalks sidewalks);
}
