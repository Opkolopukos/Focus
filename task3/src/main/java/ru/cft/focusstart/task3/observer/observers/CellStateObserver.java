package ru.cft.focusstart.task3.observer.observers;

import ru.cft.focusstart.task3.observer.HighlightEvent;
import ru.cft.focusstart.task3.observer.events.CellUpdate;
import ru.cft.focusstart.task3.observer.events.FlagTumbler;

public interface CellStateObserver {
    void updateCell(CellUpdate e);

    void updateFlag(FlagTumbler e);

    void updateHighlight(HighlightEvent e);
}
