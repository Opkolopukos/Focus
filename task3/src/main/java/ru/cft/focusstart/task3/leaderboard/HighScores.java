package ru.cft.focusstart.task3.leaderboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task3.Difficulty;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ru.cft.focusstart.task3.leaderboard.DefaultScores.DEFAULT_SCORE;
import static ru.cft.focusstart.task3.leaderboard.DefaultScores.DEFAULT_USER;

public class HighScores implements IHighScores {
    private final Map<String, Integer> easyHighScore;
    private final Map<String, Integer> normalHighScore;
    private final Map<String, Integer> hardHighScore;
    private HighScoresUpdater highScoresUpdater;
    private static final Logger logger = LoggerFactory.getLogger(HighScores.class);
    private static final String SCORE_FORMAT = "%s %s %d";

    public HighScores() {
        try {
            this.highScoresUpdater = new HighScoresUpdater();
        } catch (IOException e) {
            logger.error("HighScores file not found {}", e.getMessage());
        }
        if (highScoresUpdater != null) {
            this.easyHighScore = highScoresUpdater.getCurrentChampionByDifficulty(Difficulty.EASY);
            this.normalHighScore = highScoresUpdater.getCurrentChampionByDifficulty(Difficulty.NORMAL);
            this.hardHighScore = highScoresUpdater.getCurrentChampionByDifficulty(Difficulty.HARD);
        } else {
            this.easyHighScore = new HashMap<>();
            this.normalHighScore = new HashMap<>();
            this.hardHighScore = new HashMap<>();
            refreshHighScores();
        }
    }

    @Override
    public int getCurrentHighScoreByDifficulty(Difficulty difficulty) {
        int score = 0;
        switch (difficulty) {
            case EASY -> {
                for (Map.Entry<String, Integer> entry : easyHighScore.entrySet()) {
                    score = easyHighScore.get(entry.getKey());
                }
            }
            case NORMAL -> {
                for (Map.Entry<String, Integer> entry : normalHighScore.entrySet()) {
                    score = normalHighScore.get(entry.getKey());
                }
            }
            case HARD -> {
                for (Map.Entry<String, Integer> entry : hardHighScore.entrySet()) {
                    score = hardHighScore.get(entry.getKey());
                }
            }
        }
        return score;
    }

    @Override
    public void setNewHighScoreByDifficulty(String name, Integer score, Difficulty difficulty) {
        logger.info("new HighScore is set. Player name - {}, score - {}, difficulty {}", name, score, difficulty);
        if (highScoresUpdater != null) highScoresUpdater.updateCurrentChampion(name, score, difficulty);
        switch (difficulty) {
            case EASY -> {
                easyHighScore.entrySet().removeIf(e -> !e.getKey().equals(name));
                easyHighScore.put(name, score);
            }
            case NORMAL -> {
                normalHighScore.entrySet().removeIf(e -> !e.getKey().equals(name));
                normalHighScore.put(name, score);
            }
            case HARD -> {
                hardHighScore.entrySet().removeIf(e -> !e.getKey().equals(name));
                hardHighScore.put(name, score);
            }
        }
    }

    @Override
    public void refreshHighScores() {
        easyHighScore.clear();
        easyHighScore.put(DEFAULT_USER, DEFAULT_SCORE);
        normalHighScore.clear();
        normalHighScore.put(DEFAULT_USER, DEFAULT_SCORE);
        hardHighScore.clear();
        hardHighScore.put(DEFAULT_USER, DEFAULT_SCORE);
        logger.info("Scores set to default");
        if (highScoresUpdater != null) {
            highScoresUpdater.updateCurrentChampion(DEFAULT_USER, DEFAULT_SCORE, Difficulty.EASY);
            highScoresUpdater.updateCurrentChampion(DEFAULT_USER, DEFAULT_SCORE, Difficulty.NORMAL);
            highScoresUpdater.updateCurrentChampion(DEFAULT_USER, DEFAULT_SCORE, Difficulty.HARD);
        }
    }

    @Override
    public String getHighScores() {
        StringBuilder sb = new StringBuilder(70);
        easyHighScore.forEach((x, y) -> sb.append(String.format(SCORE_FORMAT, Difficulty.EASY.name(), x, y)).append(System.lineSeparator()));
        normalHighScore.forEach((x, y) -> sb.append(String.format(SCORE_FORMAT, Difficulty.NORMAL.name(), x, y)).append(System.lineSeparator()));
        hardHighScore.forEach((x, y) -> sb.append(String.format(SCORE_FORMAT, Difficulty.HARD.name(), x, y)).append(System.lineSeparator()));
        System.out.println(sb.capacity());
        return sb.toString();
    }
}
