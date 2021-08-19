package ru.dimagor555.eyesave.mainwindow;

import ru.dimagor555.eyesave.SecondsTimerWithEvents;

public class NotificationRepeater {
    private static final int MINUTE_MILLIS = 60_000;

    private SecondsTimerWithEvents timer;
    private final Runnable onRepeatNotification;
    private boolean stopped = false;

    public NotificationRepeater(Runnable onRepeatNotification) {
        this.onRepeatNotification = onRepeatNotification;
    }

    public void startRepeating() {
        if (stopped) return;

        timer = new SecondsTimerWithEvents(
                MINUTE_MILLIS,
                this::onFinishTimer,
                null
        );
        timer.start();
    }

    private void onFinishTimer() {
        if (!stopped)
            onRepeatNotification.run();
    }

    public void stopRepeating() {
        stopped = true;
        timer.stop();
    }
}
