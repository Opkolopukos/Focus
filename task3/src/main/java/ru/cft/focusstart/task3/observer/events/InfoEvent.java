package ru.cft.focusstart.task3.observer.events;

import ru.cft.focusstart.task3.observer.GameStateEvent;

public class InfoEvent implements GameStateEvent {
    private final String info;

    public InfoEvent(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
