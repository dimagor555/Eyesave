package ru.dimagor555.eyesave.notificationwindow;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.dimagor555.eyesave.Main;

public class NotificationWindowController {

    public static final String PATH_TO_NOTIFICATION_PANE = "/fxml/NotificationPane.fxml";

    private boolean notificationWindowOpen = false;

    public Stage notificationWindow;

    public void createNotificationWindow() {
        notificationWindow = new Stage();
        notificationWindow.initOwner(Main.stage);
        notificationWindow.initModality(Modality.NONE);
        notificationWindow.setTitle("It's time for a break");
        var notificationPane = Main.loader.loadPane(PATH_TO_NOTIFICATION_PANE);
        new NotificationPaneController(notificationPane);
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
            notificationWindowOpen = false;
        });
    }

    public boolean isNotificationWindowOpen() {
        return notificationWindowOpen;
    }

    public void setNotificationWindowOpen(boolean notificationWindowOpen) {
        this.notificationWindowOpen = notificationWindowOpen;
    }
}
