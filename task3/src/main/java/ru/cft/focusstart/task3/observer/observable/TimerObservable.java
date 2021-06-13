package ru.cft.focusstart.task3.observer.observable;

import ru.cft.focusstart.task3.observer.events.TimerEvent;
import ru.cft.focusstart.task3.observer.observers.TimerObserver;

public interface TimerObservable {

    void addTimerObserver(TimerObserver s);

    void removeTimerObserver(TimerObserver s);

    void notifyTimerObservers(TimerEvent e);
}
