package ru.dimagor555.eyesave;

import javafx.application.Platform;
import javafx.scene.text.Text;

public class BreakTimer {

    private Thread timerThread;
    private Text timerDisplay;
    private int millisLeft;
    private Runnable onFinish;

    public BreakTimer(Text timerDisplay, int millisLeft, Runnable onFinish) {
        this.timerDisplay = timerDisplay;
        this.millisLeft = millisLeft;
        this.onFinish = onFinish;
    }

    public void start() {
        timerThread = new Thread(this::run);
        timerThread.start();
    }

    private void run() {
        while (millisLeft > 0) {
            try {
                Thread.sleep(Notificator.MILLIS_IN_SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            millisLeft -= Notificator.MILLIS_IN_SECOND;
            setTimeOnDisplay();
        }
        finish();
    }

    private void finish() {
        onFinish.run();
    }

    public void setTimeOnDisplay() {
        int seconds = millisLeft / Notificator.MILLIS_IN_SECOND % Notificator.SECONDS_IN_MINUTE;
        int minutes = millisLeft / Notificator.MILLIS_IN_SECOND / Notificator.SECONDS_IN_MINUTE;
        var output = formatOutput(seconds, minutes);
        Platform.runLater(() -> timerDisplay.setText(output));
    }

    private String formatOutput(int seconds, int minutes) {
        var secondsStr = seconds < 10 ? "0" + seconds : seconds;
        var minutesStr = minutes < 10 ? "0" + minutes : minutes;
        return minutesStr + ":" + secondsStr;
    }
}
