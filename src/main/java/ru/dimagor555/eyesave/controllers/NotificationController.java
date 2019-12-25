package ru.dimagor555.eyesave.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import ru.dimagor555.eyesave.BreakTimer;
import ru.dimagor555.eyesave.Main;

public class NotificationController {

    @FXML
    private Text notificationMsg;

    @FXML
    private Text timeText;

    @FXML
    private Button startBtn;

    @FXML
    private Button closeBtn;

    public NotificationController() {

    }

    public NotificationController(Pane root) {
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

    private void startBreak() {
        var notificator = Main.notificator;
        Runnable onFinish = this::onTimerFinish;
        new BreakTimer(timeText, notificator.getDuration(), onFinish).start();
        startBtn.setVisible(false);
    }

    public static final String BREAK_TIME_UP_MSG =
            "Break time is up!\nYou can continue to use your computer" ;

    private void onTimerFinish() {
        notificationMsg.setText(BREAK_TIME_UP_MSG);
        timeText.setVisible(false);
        closeBtn.setVisible(true);
        Main.soundPlayer.playNotificationSound();
    }

    private void closeWindow() {
        Main.notificator.closeNotificationWindow();
    }

    private void setInitialTimerDisplayValue() {
        new BreakTimer(timeText, Main.notificator.getDuration(), null).setTimeOnDisplay();
    }

}
