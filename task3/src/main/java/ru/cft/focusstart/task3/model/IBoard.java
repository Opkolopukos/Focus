package ru.cft.focusstart.task3.model;

import ru.cft.focusstart.task3.Difficulty;

import java.util.List;

public interface IBoard {

    Cell getCell(int row, int col);

    int getMines();

    Difficulty getDifficulty();

    List<Cell> getCells();

    List<Cell> getCellSurroundings(int row, int col);

    @Override
    String toString();

    List<Cell> getCellSurroundings(Cell cell);

}
