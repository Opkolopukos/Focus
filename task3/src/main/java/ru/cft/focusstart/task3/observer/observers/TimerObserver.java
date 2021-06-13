package ru.cft.focusstart.task3.observer.observers;

import ru.cft.focusstart.task3.observer.events.TimerEvent;

public interface TimerObserver {
    void updateTimer(TimerEvent e);
}
