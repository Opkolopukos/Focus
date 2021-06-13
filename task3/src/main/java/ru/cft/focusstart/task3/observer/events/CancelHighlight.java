package ru.cft.focusstart.task3.observer.events;

import ru.cft.focusstart.task3.observer.HighlightEvent;

public class CancelHighlight implements HighlightEvent {
    private final int row;
    private final int col;

    public CancelHighlight(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
