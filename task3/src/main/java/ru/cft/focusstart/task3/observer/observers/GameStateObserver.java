package ru.cft.focusstart.task3.observer.observers;

import ru.cft.focusstart.task3.observer.events.*;

public interface GameStateObserver {
    void updateVictory(Victory e);

    void updateDefeat(Defeat e);

    void updateDifficulty(DifficultyChanged e);

    void updateNewGame(NewGame e);

    void updateInfo(InfoEvent e);
}
