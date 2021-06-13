package ru.cft.focusstart.task3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task3.controller.Controller;
import ru.cft.focusstart.task3.controller.IController;
import ru.cft.focusstart.task3.leaderboard.HighScores;
import ru.cft.focusstart.task3.leaderboard.IHighScores;
import ru.cft.focusstart.task3.model.*;
import ru.cft.focusstart.task3.view.View;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Program started");
        GameTimer gameTimer = new GameTimer();
        IHighScores highScores = new HighScores();
        IBoard board = new Board(Difficulty.EASY);
        IGameEngine gameEngine = new GameEngine(gameTimer, board);
        IController controller = new Controller(gameEngine, highScores);
        View view = new View(controller);
        gameEngine.addCellStateObserver(view);
        gameEngine.addGameStateObserver(view);
        gameTimer.addTimerObserver(view);
        System.out.println();
    }
}
