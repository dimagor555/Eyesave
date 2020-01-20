package ru.dimagor555.eyesave;

import javafx.application.Platform;
import ru.dimagor555.eyesave.mainwindow.SecondsTimerWithEventsAndPause;
import ru.dimagor555.eyesave.notificationwindow.NotificationWindowController;
import ru.dimagor555.eyesave.settings.Profile;

public class Notificator {

    public static final int SECONDS_IN_MINUTE = 60;
    public static final int MILLIS_IN_SECOND = 1000;

    private int frequency;
    private int duration;
    private Thread notificatorThread;
    private SecondsTimerWithEventsAndPause notificatorTimer;
    public NotificationWindowController notificationWindowController =
            new NotificationWindowController();

    public void start() {
        notificatorThread = new Thread(() -> {
            try {
                while (true) {
                    if (!notificationWindowController.isNotificationWindowOpen()) {
                        startTimerToNextNotification();
                    }
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        notificatorThread.start();
    }

    private void startTimerToNextNotification() throws InterruptedException {
        Runnable onFinish = () -> Platform.runLater(this::sendNotification);
        Runnable onEverySecond = () -> Platform.runLater(() -> Main.mainUIController
                .setNextNotificationTime(notificatorTimer.getMillisLeft()));
        notificatorTimer = new SecondsTimerWithEventsAndPause(frequency, onFinish, onEverySecond);
        notificatorTimer.start();

        while (!notificatorTimer.isFinished()) {
            Thread.sleep(1000);
        }
    }

    private void sendNotification() {
        Platform.runLater(notificationWindowController::createNotificationWindow);
        notificationWindowController.setNotificationWindowOpen(true);
    }

    public void pauseNotificatorTimer() {
        notificatorTimer.pause();
    }

    public void continueNotificatorTimer() {
        notificatorTimer.continue_();
    }

    public void restart() {
        stop();
        start();
    }

    public void stop() {
        if (notificatorThread != null) {
            notificatorThread.interrupt();
        }
        notificatorTimer.stop();
    }

    public void closeNotificationWindow() {
        notificationWindowController.closeNotificationWindow();
    }

    public void setProfile(Profile profile) {
        if (profile == null) {
            return;
        }
        setFrequency(profile.getFrequency());
        setDuration(profile.getDuration());
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency * SECONDS_IN_MINUTE * MILLIS_IN_SECOND;
    }

    public void setDuration(int duration) {
        this.duration = duration * MILLIS_IN_SECOND;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getDuration() {
        return duration;
    }
}
