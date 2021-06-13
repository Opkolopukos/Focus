package ru.cft.focusstart.task3.observer.observable;

import ru.cft.focusstart.task3.observer.events.*;
import ru.cft.focusstart.task3.observer.observers.GameStateObserver;

public interface GameStateObservable {

    void addGameStateObserver(GameStateObserver s);

    void removeGameStateObserver(GameStateObserver s);

    void notifyVictory(Victory e);

    void notifyDefeat(Defeat e);

    void notifyDifficultyChange(DifficultyChanged e);

    void notifyNewGame(NewGame e);

    void notify(InfoEvent e);
}
