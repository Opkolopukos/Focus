package ru.cft.focusstart.task3.observer.events;

import ru.cft.focusstart.task3.observer.GameStateEvent;

public class Victory implements GameStateEvent {
    private final boolean isRecord;


    public Victory(boolean isRecord) {
        this.isRecord = isRecord;
    }

    public boolean isRecord() {
        return isRecord;
    }
}
