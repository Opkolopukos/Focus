package ru.cft.focusstart.task3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task3.Difficulty;
import ru.cft.focusstart.task3.leaderboard.IHighScores;
import ru.cft.focusstart.task3.model.IGameEngine;


public class Controller implements IController {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private final IGameEngine gameEngine;
    private final IHighScores highScores;
    private String playerName;

    public Controller(IGameEngine gameEngine, IHighScores highScores) {
        logger.info("Controller initializing");
        this.gameEngine = gameEngine;
        logger.info("Controller injected gameEngine");
        this.highScores = highScores;
        logger.info("Controller injected HighScores Api");
    }

    public void startGame() {
        logger.info("Controller sending request to IGameEngine to start the game");
        gameEngine.newGame();
    }

    public void openCell(int row, int col) {
        logger.info("Controller sending request to IGameEngine in order to open cell: row {} col {}", row, col);
        gameEngine.openCell(row, col);
        winLoseScenario();
    }

    public void changeSettings(Difficulty difficulty) {
        logger.info("Controller sending request to IGameEngine in order to change difficulty to {}", difficulty);
        gameEngine.changeSettings(difficulty);
    }

    public void shutdown() {
        System.exit(0);
    }

    @Override
    public void resetHighScores() {
        logger.info("LeaderBoards have been reset");
        highScores.refreshHighScores();
    }

    @Override
    public void toggleFlag(int row, int col) {
        if (gameEngine.getCell(row, col).isOpened()) return;
        logger.info("Controller sending request to IGameEngine in order to toggle a flag in cell {}_{}", row, col);
        gameEngine.toggleFlag(row, col);
    }


    public void middleButtonHold(int row, int col) {
        gameEngine.highlight(row, col);
    }

    public void middleButtonRelease(int row, int col) {
        gameEngine.openSurroundings(row, col);
        winLoseScenario();
    }

    @Override
    public void showHighScores() {
        logger.info("Controller showing highScores");
        gameEngine.sendInfo(highScores.getHighScores());
    }

    private void winLoseScenario() {
        int finishTime = gameEngine.getTime();
        if (gameEngine.getGameState() == IGameEngine.GameState.LOSE) {
            gameEngine.gameOver("Game lost", false);
        }
        if (gameEngine.getGameState() == IGameEngine.GameState.WIN) {
            if (finishTime < highScores.getCurrentHighScoreByDifficulty(gameEngine.getDifficulty())) {
                logger.info("New record is set. New High score - {}", gameEngine.getTime());
                gameEngine.gameOver("Game won ", true);
                highScores.setNewHighScoreByDifficulty(playerName, finishTime, gameEngine.getDifficulty());
            } else {
                gameEngine.gameOver("Game won", false);
            }
        }
    }

    public void setCurrentPlayer(String playerName) {
        this.playerName = playerName;
    }
}
