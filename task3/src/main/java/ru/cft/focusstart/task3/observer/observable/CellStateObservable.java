package ru.cft.focusstart.task3.observer.observable;

import ru.cft.focusstart.task3.observer.HighlightEvent;
import ru.cft.focusstart.task3.observer.events.CellUpdate;
import ru.cft.focusstart.task3.observer.events.FlagTumbler;
import ru.cft.focusstart.task3.observer.observers.CellStateObserver;


public interface CellStateObservable {

    void addCellStateObserver(CellStateObserver s);

    void removeCellStateObserver(CellStateObserver s);

    void notifyCellUpdate(CellUpdate e);

    void notifyFlagToggle(FlagTumbler e);

    void notifyHighlight(HighlightEvent e);
}
