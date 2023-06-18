package com.freewaygpt.game.observer.publishers;

import com.freewaygpt.game.observer.observers.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObserverManager {
    Map<String, List<Observer>> observers = new HashMap<>();

    public ObserverManager(String... eventTypes) {
        for (String type: eventTypes) {
            this.observers.put(type, new ArrayList<Observer>());
        }
    }

    public void subscribe(String eventType, Observer observer) {
        observers.get(eventType).add(observer);
    }

    public void unsubscribe(String eventType, Observer observer) {
        observers.get(eventType).remove(observer);
    }

    public void notify(String eventType) {
        List<Observer> users = observers.get(eventType);
        for (Observer observer : users) {
            observer.notify(eventType);
        }
    }
}
