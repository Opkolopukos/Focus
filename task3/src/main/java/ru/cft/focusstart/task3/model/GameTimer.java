package ru.cft.focusstart.task3.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.task3.observer.events.TimerEvent;
import ru.cft.focusstart.task3.observer.observable.TimerObservable;
import ru.cft.focusstart.task3.observer.observers.TimerObserver;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer implements TimerObservable {
    private final Timer timer;
    private TimerTask timerTask;
    private int time;
    private final List<TimerObserver> timerObservers = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(GameTimer.class);
    private long start;


    public GameTimer() {
        logger.info("GameTimer with 1 second interval has been created");
        timer = new Timer();
        createTask();
    }

    public void start() {
        createTask();
        timer.scheduleAtFixedRate(timerTask, 1000L, 999);
    }

    public void refresh() {
        time = 0;
    }

    public synchronized void stop() {
        time = (int) (Instant.now().getEpochSecond() - start);
        timerTask.cancel();

    }

    public void createTask() {
        start = Instant.now().getEpochSecond();
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                notifyTimerObservers(new TimerEvent(Instant.now().getEpochSecond() - start));
            }
        };

    }


    public int getCurrent() {
        return time;
    }

    @Override
    public void addTimerObserver(TimerObserver observer) {
        timerObservers.add(observer);
    }


    @Override
    public void removeTimerObserver(TimerObserver observer) {
        timerObservers.remove(observer);
    }

    @Override
    public void notifyTimerObservers(TimerEvent e) {
        logger.info("Notifying observers with event {}", e.getClass().getSimpleName());
        for (TimerObserver observer : timerObservers) {
            observer.updateTimer(e);
        }
    }

}
