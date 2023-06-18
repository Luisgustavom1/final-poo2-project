package com.freewaygpt.game.components;

import com.freewaygpt.game.entity.Car;
import com.freewaygpt.game.entity.Rendable;

import java.util.ArrayList;

public class Cars implements Rendable {
    public ArrayList<Car> cars = new ArrayList<Car>();
    private Frame frame;

    public Cars(Frame frame) {
        cars.add(new Car(107));
        cars.add(new Car(174));
        cars.add(new Car(241));
        cars.add(new Car(330));
        cars.add(new Car(397));
        cars.add(new Car(464));
        cars.add(new Car(531));

        this.frame = frame;
    }


    @Override
    public void render() {
        frame.begin();
        for(Car car: cars){
            frame.draw(car.getImage(), car.getX(), car.getY());
        }
        frame.end();
    }

    @Override
    public void dispose() {
        frame.dispose();
    }
}
