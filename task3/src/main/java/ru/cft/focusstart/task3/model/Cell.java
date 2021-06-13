package ru.cft.focusstart.task3.model;

public class Cell implements ICell {
    private final int x;
    private final int y;
    private CellState cellState;

    private boolean mine;
    private int aroundMinesCount;

    Cell(int x, int y) {
        cellState = CellState.CLOSED;
        this.x = x;
        this.y = y;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public void setAroundMinesCount(int aroundMinesCount) {
        this.aroundMinesCount = aroundMinesCount;
    }

    public CellState getCellState() {
        return this.cellState;
    }

    @Override
    public boolean isOpened() {
        return cellState == CellState.OPEN;
    }

    @Override
    public boolean isMine() {
        return mine;
    }

    @Override
    public boolean hasFlag() {
        return cellState == CellState.FLAG;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getAroundMinesCount() {
        return aroundMinesCount;
    }

    @Override
    public String toString() {
        return "Cell coordinates" + x + "_" + y + ". mines around: " + aroundMinesCount;
    }
}
