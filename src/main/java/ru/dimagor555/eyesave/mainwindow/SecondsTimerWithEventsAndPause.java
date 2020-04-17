package ru.dimagor555.eyesave.mainwindow;

import ru.dimagor555.eyesave.SecondsTimerWithEvents;

public class SecondsTimerWithEventsAndPause extends SecondsTimerWithEvents {

    private final Object lock = new Object();

    private boolean paused = false;
    private boolean finished = false;

    public SecondsTimerWithEventsAndPause(long millisLeft, Runnable onFinish, Runnable onEverySecond) {
        super(millisLeft, onFinish, onEverySecond);
    }

    @Override
    protected void run() {
        try {
            while (millisLeft > 0 && !Thread.interrupted()) {
                runEverySecondEventHandler();
                millisLeft -= ONE_SECOND;
                Thread.sleep(ONE_SECOND);

                if (paused) {
                    synchronized (lock) {
                        lock.wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            finished = true;
            return;
        }

        finished = true;
        runFinishEventHandler();
    }

    public void pause() {
        paused = true;
    }

    public void continue_() {
        paused = false;
        synchronized (lock) {
            lock.notify();
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
