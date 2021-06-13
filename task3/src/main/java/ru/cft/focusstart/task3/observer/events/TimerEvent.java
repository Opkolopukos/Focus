package ru.cft.focusstart.task3.observer.events;

public class TimerEvent {
    private final long time;

    public TimerEvent(long seconds) {
        this.time = seconds;
    }

    public long getTime() {
        return time;
    }
}
