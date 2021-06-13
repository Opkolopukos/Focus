package ru.cft.focusstart.task3.observer.events;

import ru.cft.focusstart.task3.model.Cell;
import ru.cft.focusstart.task3.observer.CellStateEvent;

public class FlagTumbler implements CellStateEvent {
    private final int row;
    private final int col;

    private final int flags;
    private final Boolean isFlag;

    public FlagTumbler(Cell cell, int flags) {
        this.row = cell.getX();
        this.col = cell.getY();
        this.isFlag = cell.hasFlag();
        this.flags = flags;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Boolean getFlag() {
        return isFlag;
    }

    public int getFlags() {
        return flags;
    }
}
