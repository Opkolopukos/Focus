package ru.cft.focusstart.task3.leaderboard;

import ru.cft.focusstart.task3.Difficulty;

public interface IHighScores {
    int getCurrentHighScoreByDifficulty(Difficulty difficulty);

    void setNewHighScoreByDifficulty(String name, Integer score, Difficulty difficulty);

    void refreshHighScores();

    String getHighScores();
}
