package ru.dimagor555.eyesave;

public class SecondsTimerWithEvents {

    public static final long ONE_SECOND = 1000;

    private long millisLeft;
    private Thread timerThread;
    private Runnable onFinish;
    private Runnable onEverySecond;

    public SecondsTimerWithEvents(long millisLeft, Runnable onFinish, Runnable onEverySecond) {
        this.millisLeft = millisLeft;
        this.onFinish = onFinish;
        this.onEverySecond = onEverySecond;
    }

    public void start() {
        timerThread = new Thread(this::run);
        timerThread.start();
    }

    private void run() {
        try {
            while (millisLeft > 0) {
                runEverySecondEventHandler();
                millisLeft -= ONE_SECOND;
                Thread.sleep(ONE_SECOND);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runFinishEventHandler();
    }

    private void runEverySecondEventHandler() {
        if (onEverySecond != null) {
            onEverySecond.run();
        }
    }

    private void runFinishEventHandler() {
        if (onFinish != null) {
            onFinish.run();
        }
    }

    private void stop() {
        timerThread.interrupt();
    }

    public long getMillisLeft() {
        return millisLeft;
    }
}
