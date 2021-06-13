package ru.cft.focusstart.task3.observer.events;


import ru.cft.focusstart.task3.model.Cell;
import ru.cft.focusstart.task3.model.CellState;
import ru.cft.focusstart.task3.observer.CellStateEvent;

public class CellUpdate implements CellStateEvent {
    private int row;
    private int col;
    private int minesAround;
    private CellState cellType;
    private boolean isMine;

    public CellUpdate(Cell cell) {
        this.row = cell.getX();
        this.col = cell.getY();
        this.isMine = cell.isMine();
        this.minesAround = cell.getAroundMinesCount();
        this.cellType = cell.getCellState();
    }

    public CellState getCellType() {
        return cellType;
    }

    public int getMinesAround() {
        return minesAround;
    }

    public boolean isMine() {
        return isMine;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
