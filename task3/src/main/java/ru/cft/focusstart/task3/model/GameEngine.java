package ru.cft.focusstart.task3.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task3.Difficulty;
import ru.cft.focusstart.task3.observer.HighlightEvent;
import ru.cft.focusstart.task3.observer.events.*;
import ru.cft.focusstart.task3.observer.observers.CellStateObserver;
import ru.cft.focusstart.task3.observer.observers.GameStateObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameEngine implements IGameEngine {
    private IBoard userBoard;
    private Difficulty difficulty;
    private final GameTimer gameTimer;
    private final List<CellStateObserver> cellStateObservers = new ArrayList<>();
    private final List<GameStateObserver> gameStateObservers = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(GameEngine.class);

    public GameState getGameState() {
        return gameState;
    }

    private GameState gameState;
    private int flags;

    public GameEngine(GameTimer gameTimer, IBoard board) {
        this.gameTimer = gameTimer;
        this.userBoard = board;
        this.gameState = GameState.READY_TO_START;
        this.difficulty = userBoard.getDifficulty();
        this.flags = difficulty.getMines();
        logger.info("IGameEngine has been created {}", getClass().getName());
    }

    @Override
    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public int getTime() {
        return gameTimer.getCurrent();
    }

    public void changeSettings(Difficulty difficulty) {
        this.difficulty = difficulty;
        newGame();
        notifyDifficultyChange(new DifficultyChanged(difficulty));
    }

    public void newGame() {
        gameTimer.stop();
        gameTimer.refresh();
        gameState = GameState.READY_TO_START;
        userBoard = new Board(difficulty);
        flags = difficulty.getMines();
        notifyNewGame(new NewGame());
    }

    @Override
    public IBoard getCurrentGameBoard() {
        return userBoard;
    }

    @Override
    public void openCell(int row, int col) {
        if (userBoard.getCell(row, col).isOpened()) {
            return;
        }
        firstClickCase(row, col);
        Cell cell = userBoard.getCell(row, col);
        if (cell.getCellState() == CellState.FLAG) {
            logger.warn("Cell {} has flag, it can't be opened", cell);
            return;
        }
        executeOpenCell(cell);
        logger.info("Opening cell {}", cell);

    }

    private void firstClickCase(int row, int col) {
        if (gameState == GameState.READY_TO_START) {
            while (userBoard.getCell(row, col).isMine()) {
                logger.warn("Mine at first click! Refreshing");
                newGame();
            }
            gameTimer.start();
            gameState = GameState.IN_PROCESS;
        }
    }

    private void executeOpenCell(Cell cell) {
        if (cell.isMine()) {
            gameTimer.stop();
            cell.setCellState(CellState.OPEN);
            notifyCellUpdate(new CellUpdate(cell));
            gameState = GameState.LOSE;
            logger.info("Cell {} has mine. Game over", cell);
            return;
        }
        if (cell.getAroundMinesCount() == 0) {
            cell.setCellState(CellState.OPEN);
            logger.info("Empty cell opened: {}", cell);
            floodOpenCells(cell);
        }
        if (gameState == GameState.IN_PROCESS) {
            cell.setCellState(CellState.OPEN);
            logger.info("Cell {} with {} mines around opened", cell, cell.getAroundMinesCount());
            notifyCellUpdate(new CellUpdate(cell));
        }
        checkWin();
    }

    private void checkWin() {
        if (gameState == GameState.IN_PROCESS) {
            int openedCells = getOpenedCells().size();
            int allCells = userBoard.getCells().size();
            int mines = userBoard.getMines();
            if (openedCells == (allCells - mines)) {
                gameState = GameState.WIN;
                logger.info("All non mines cells has been opened. Victory");
                gameTimer.stop();
            }
        }
    }

    @Override
    public void gameOver(String info, boolean isRecord) {
        if (gameState == IGameEngine.GameState.LOSE) {
            logger.info(info);
            List<ICell> mines = userBoard.getCells().stream().filter(cell -> !cell.isOpened()).filter(ICell::isMine).collect(Collectors.toList());
            notifyDefeat(new Defeat(info, mines));
        }
        if (gameState == IGameEngine.GameState.WIN) {
            logger.info(info);
            notifyVictory(new Victory(isRecord));
        }
    }

    public Cell getCell(int row, int col) {
        return userBoard.getCell(row, col);
    }

    @Override
    public void toggleFlag(int row, int col) {
        Cell cell = userBoard.getCell(row, col);
        if (!cell.hasFlag()) {
            userBoard.getCell(row, col).setCellState(CellState.FLAG);
            logger.info("Cell {} set as a flag", cell);
            flags--;
        } else {
            userBoard.getCell(row, col).setCellState(CellState.CLOSED);
            logger.info("Cell's {} flag has been removed", cell);
            flags++;
        }
        notifyFlagToggle(new FlagTumbler(cell, flags));
    }

    public void floodOpenCells(Cell cell) {
        logger.info("Recursive empty cells opening is invoked");
        List<Cell> surroundings = userBoard.getCellSurroundings(cell);
        surroundings.stream().filter(c -> !cell.isMine() && !c.isOpened())
                .forEach(c -> openCell(c.getX(), c.getY()));
    }

    @Override
    public void openSurroundings(int row, int col) {
        logger.info("Trying to open cell surroundings if flags are even to cell number");
        List<Cell> surroundingsAndCurrent = userBoard.getCellSurroundings(row, col);
        surroundingsAndCurrent.add(getCell(row, col));
        surroundingsAndCurrent.stream().filter(cell -> !cell.hasFlag())
                .filter(x -> !x.isOpened())
                .forEach(x -> notifyHighlight(new CancelHighlight(x.getX(), x.getY())));
        if (!userBoard.getCell(row, col).isOpened()) {
            logger.warn("Cell is already open - {}", getCell(row, col));
            return;
        }
        List<Cell> adjCells = userBoard.getCellSurroundings(row, col);
        if (adjCells.stream().filter(ICell::hasFlag).count() == adjCells.stream().filter(Cell::isMine).count()) {
            adjCells.stream().filter(x -> !x.hasFlag())
                    .forEach(x -> openCell(x.getX(), x.getY()));
        }
    }

    @Override
    public void highlight(int row, int col) {
        List<Cell> surroundingsAndCurrent = userBoard.getCellSurroundings(row, col);
        surroundingsAndCurrent.add(getCell(row, col));
        logger.info("Highlighting cell surroundings");
        surroundingsAndCurrent.stream().filter(x -> !x.hasFlag())
                .filter(x -> !x.isOpened())
                .forEach(x -> notifyHighlight(new Highlight(x.getX(), x.getY())));

    }

    @Override
    public void sendInfo(String info) {
        notify(new InfoEvent(info));
    }


    @Override
    public List<ICell> getOpenedCells() {
        return (userBoard.getCells().stream().filter(ICell::isOpened).collect(Collectors.toList()));
    }

    @Override
    public void addCellStateObserver(CellStateObserver observer) {
        cellStateObservers.add(observer);
    }

    @Override
    public void removeCellStateObserver(CellStateObserver observer) {
        cellStateObservers.remove(observer);
    }

    @Override
    public void notifyCellUpdate(CellUpdate e) {
        cellStateObservers.forEach(observer -> observer.updateCell(e));
    }

    @Override
    public void notifyFlagToggle(FlagTumbler e) {
        cellStateObservers.forEach(observer -> observer.updateFlag(e));
    }

    @Override
    public void notifyHighlight(HighlightEvent e) {
        cellStateObservers.forEach(observer -> observer.updateHighlight(e));
    }

    @Override
    public void addGameStateObserver(GameStateObserver observer) {
        gameStateObservers.add(observer);
    }

    @Override
    public void removeGameStateObserver(GameStateObserver observer) {
        gameStateObservers.remove(observer);
    }

    @Override
    public void notifyVictory(Victory e) {
        gameStateObservers.forEach(observer -> observer.updateVictory(e));
    }

    @Override
    public void notifyDefeat(Defeat e) {
        gameStateObservers.forEach(observer -> observer.updateDefeat(e));
    }

    @Override
    public void notifyDifficultyChange(DifficultyChanged e) {
        gameStateObservers.forEach(observer -> observer.updateDifficulty(e));
    }

    @Override
    public void notifyNewGame(NewGame e) {
        gameStateObservers.forEach(observer -> observer.updateNewGame(e));
    }

    @Override
    public void notify(InfoEvent e) {
        gameStateObservers.forEach(observer -> observer.updateInfo(e));
    }
}
