package ru.cft.focusstart.task3.observer.events;

import ru.cft.focusstart.task3.model.ICell;
import ru.cft.focusstart.task3.observer.GameStateEvent;

import java.util.List;

public class Defeat implements GameStateEvent {
    private final String info;

    private final List<ICell> minesLocation;

    public Defeat(String info, List<ICell> minesLocation) {
        this.info = info;
        this.minesLocation = minesLocation;
    }

    public String getInfo() {
        return info;
    }

    public List<ICell> getMinesLocation() {
        return minesLocation;
    }
}
