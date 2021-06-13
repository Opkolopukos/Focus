package ru.cft.focusstart.task3.observer.events;

import ru.cft.focusstart.task3.Difficulty;
import ru.cft.focusstart.task3.observer.GameStateEvent;

public class DifficultyChanged implements GameStateEvent {
    private final Difficulty difficulty;

    public DifficultyChanged(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
