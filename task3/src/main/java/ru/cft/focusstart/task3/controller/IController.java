package ru.cft.focusstart.task3.controller;

import ru.cft.focusstart.task3.Difficulty;

public interface IController {
    void startGame();

    void openCell(int row, int col);

    void toggleFlag(int row, int col);

    void changeSettings(Difficulty difficulty);

    void middleButtonHold(int row, int col);

    void middleButtonRelease(int row, int col);

    void showHighScores();

    void shutdown();

    void resetHighScores();

    void setCurrentPlayer(String playerName);
}
