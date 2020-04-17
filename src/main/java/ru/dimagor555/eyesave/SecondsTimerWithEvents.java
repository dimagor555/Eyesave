package ru.dimagor555.eyesave;

public class SecondsTimerWithEvents {

    public static final long ONE_SECOND = 1000;

    protected long millisLeft;
    protected Thread timerThread;
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

    protected void run() {
        try {
            while (millisLeft > 0) {
                runEverySecondEventHandler();
                millisLeft -= ONE_SECOND;
                Thread.sleep(ONE_SECOND);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            runFinishEventHandler();
            return;
        }

        runFinishEventHandler();
        Main.soundPlayer.playNotificationSound();
    }

    protected void runEverySecondEventHandler() {
        if (onEverySecond != null) {
            onEverySecond.run();
        }
    }

    protected void runFinishEventHandler() {
        if (onFinish != null) {
            onFinish.run();
        }
    }

    public void stop() {
        if (timerThread != null) {
            timerThread.interrupt();
        }
    }

    public long getMillisLeft() {
        return millisLeft;
    }
}
