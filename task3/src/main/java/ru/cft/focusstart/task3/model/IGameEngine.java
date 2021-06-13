package ru.cft.focusstart.task3.model;

import ru.cft.focusstart.task3.Difficulty;
import ru.cft.focusstart.task3.observer.observable.CellStateObservable;
import ru.cft.focusstart.task3.observer.observable.GameStateObservable;

import java.util.List;

public interface IGameEngine extends CellStateObservable, GameStateObservable {

    enum GameState {
        READY_TO_START,
        IN_PROCESS,
        WIN,
        LOSE
    }

    Difficulty getDifficulty();

    IBoard getCurrentGameBoard();

    GameState getGameState();

    ICell getCell(int row, int col);

    List<ICell> getOpenedCells();

    int getTime();

    void changeSettings(Difficulty difficulty);

    void newGame();

    void openCell(int row, int col);

    void toggleFlag(int row, int col);


    void openSurroundings(int row, int col);

    void highlight(int row, int col);

    void sendInfo(String info);

    void gameOver(String info, boolean isRecord);
}
