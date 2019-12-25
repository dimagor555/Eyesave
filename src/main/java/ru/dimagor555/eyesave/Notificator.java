package ru.dimagor555.eyesave;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.dimagor555.eyesave.controllers.NotificationController;
import ru.dimagor555.eyesave.settings.Profile;

public class Notificator {

    public static final int SECONDS_IN_MINUTE = 60;
    public static final int MILLIS_IN_SECOND = 1000;
    public static final String PATH_TO_NOTIFICATION_PANE = "/fxml/NotificationPane.fxml";

    private int frequency;
    private int duration;
    private boolean isNotificationOpen = false;
    private Thread notificatorThread;

    public void start() {
        notificatorThread = new Thread(() -> {
            try {
                while (true) {
                    while (!isNotificationOpen) {
                        waitForNextNotification();
                        sendNotification();
                    }
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        notificatorThread.start();
    }

    private void waitForNextNotification() throws InterruptedException {
        Thread.sleep(frequency);
    }

    private void sendNotification() {
        Platform.runLater(this::createNotificationWindow);
        isNotificationOpen = true;
    }

    public Stage notificationWindow;

    private void createNotificationWindow() {
        notificationWindow = new Stage();
        notificationWindow.initOwner(Main.stage);
        notificationWindow.initModality(Modality.NONE);
        notificationWindow.setTitle("It's time for a break");
        var notificationPane = Main.loader.loadPane(PATH_TO_NOTIFICATION_PANE);
        new NotificationController(notificationPane);
        notificationWindow.setScene(new Scene(notificationPane));
        notificationWindow.setResizable(false);
        notificationWindow.setAlwaysOnTop(true);
        notificationWindow.setOnCloseRequest(windowEvent -> closeNotificationWindow());

        notificationWindow.show();

        Main.soundPlayer.playNotificationSound();
    }

    public void closeNotificationWindow() {
        Platform.runLater(() -> {
            notificationWindow.close();
            notificationWindow = null;
            isNotificationOpen = false;
        });
    }

    public void restart() {
        stop();
        start();
    }

    public void stop() {
        notificatorThread.interrupt();
    }

    public void setProfile(Profile profile) {
        if (profile == null) {
            setFrequency(999);
            setDuration(999);
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
