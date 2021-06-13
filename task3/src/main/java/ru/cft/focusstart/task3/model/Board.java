package ru.cft.focusstart.task3.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task3.Difficulty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Board implements IBoard {
    private final Cell[][] gameBoard;
    private final int height;
    private final int width;
    private final Difficulty difficulty;
    private static final Logger logger = LoggerFactory.getLogger(Board.class.getName());
    private final int mines;

    public Board(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.width = difficulty.getWidth();
        this.height = difficulty.getHeight();
        this.gameBoard = createBoard(height, width);
        this.mines = difficulty.getMines();
        putMinesAtBoard();
        calculateNonMinesCells();
        logger.info("Board has been created. Difficulty {}", getDifficulty());
    }

    @Override
    public Difficulty getDifficulty() {
        return difficulty;
    }

    private Cell[][] createBoard(int height, int width) {
        Cell[][] filledBoard = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                filledBoard[i][j] = new Cell(i, j);
            }
        }
        return filledBoard;
    }

    private void putMinesAtBoard() {
        logger.info("Mines have been placed");
        List<Cell> allCells = getCells();
        Collections.shuffle(allCells);
        allCells.stream().limit(mines).collect(Collectors.toList()).forEach(randomCell -> randomCell.setMine(true));
    }


    @Override
    public List<Cell> getCells() {
        List<Cell> cellList = new ArrayList<>();
        for (Cell[] rows : gameBoard) {
            cellList.addAll(Arrays.asList(rows));
        }
        return cellList;
    }

    public void calculateNonMinesCells() {
        logger.info("Non Mines Cells have been calculated");
        List<Cell> cellSurroundingsList;
        int mineCount;
        for (Cell[] cells : gameBoard) {
            for (Cell value : cells) {
                mineCount = 0;
                if (value.isMine()) {
                    continue;
                }
                cellSurroundingsList = getCellSurroundings(value);
                for (Cell cell : cellSurroundingsList) {
                    if (cell.isMine()) {
                        mineCount++;
                    }
                }
                value.setAroundMinesCount(mineCount);
            }
        }
    }

    @Override
    public List<Cell> getCellSurroundings(final int row, final int col) {
        int[][] surroundingsCoordinates = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0},
                {1, 1}};
        List<Cell> result = new ArrayList<>(8);
        for (int[] cord : surroundingsCoordinates) {
            int rowGet = cord[0] + row;
            int colGet = cord[1] + col;
            if (checkBounds(rowGet, colGet)) {
                result.add(gameBoard[rowGet][colGet]);
            }
        }
        return result;
    }

    @Override
    public Cell getCell(int row, int col) {
        if (!checkBounds(row, col)) {
            throw new IllegalArgumentException("Cell does not exist at this location");
        }
        return gameBoard[row][col];
    }

    private boolean checkBounds(int row, int col) {
        return col >= 0 && row >= 0 && col < width && row < height;
    }


    @Override
    public int getMines() {
        return mines;
    }

    @Override
    public List<Cell> getCellSurroundings(Cell cell) {
        return getCellSurroundings(cell.getX(), cell.getY());
    }

    @Override
    public String toString() {
        return Arrays.stream(gameBoard).map(rows -> Arrays.stream(rows).map(ICell::toString).collect(Collectors.joining("|")))
                .collect(Collectors.joining("\n" + "------+".repeat(width) + "\n"));
    }
}
