package ru.dimagor555.eyesave.notificationwindow;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import ru.dimagor555.eyesave.Main;
import ru.dimagor555.eyesave.controllers.ButtonGraphicEffects;

public class NotificationPaneController {

    @FXML
    private Text notificationMsg;

    @FXML
    private Text timeText;

    @FXML
    private Button startBtn;

    @FXML
    private Button closeBtn;

    private Runnable onBreakStarted;

    public NotificationPaneController() {

    }

    public NotificationPaneController(Pane root) {
        notificationMsg = (Text) root.lookup("#notificationMsg");
        timeText = (Text) root.lookup("#timeText");
        startBtn = (Button) root.lookup("#startBtn");
        closeBtn = (Button) root.lookup("#closeBtn");

        ButtonGraphicEffects.addBtnClickEffect(startBtn);
        startBtn.setOnAction(actionEvent -> startBreak());

        ButtonGraphicEffects.addBtnClickEffect(closeBtn);
        closeBtn.setOnAction(actionEvent -> closeWindow());

        setInitialTimerDisplayValue();
    }

    public BreakTimer breakTimer;

    private void startBreak() {
        var notificator = Main.notificator;
        Runnable onFinish = () -> Platform.runLater(this::onTimerFinish);
        breakTimer = new BreakTimer(timeText, notificator.getDuration(), onFinish);

        if (onBreakStarted != null)
            breakTimer.setOnStarted(onBreakStarted);

        breakTimer.start();
        startBtn.setVisible(false);
    }

    public static final String BREAK_TIME_ENDED_MSG =
            "Break time is up!\nYou can continue to use your computer";

    private void onTimerFinish() {
        notificationMsg.setText(BREAK_TIME_ENDED_MSG);
        timeText.setVisible(false);
        closeBtn.setVisible(true);
    }

    private void closeWindow() {
        Main.notificator.closeNotificationWindow();
    }

    private void setInitialTimerDisplayValue() {
        new TimerDisplayController(timeText).setTime(Main.notificator.getDuration());
    }

    public void setOnBreakStarted(Runnable onBreakStarted) {
        this.onBreakStarted = onBreakStarted;
    }
}
