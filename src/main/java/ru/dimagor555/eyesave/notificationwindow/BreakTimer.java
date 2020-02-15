package ru.dimagor555.eyesave.notificationwindow;

import javafx.application.Platform;
import javafx.scene.text.Text;
import ru.dimagor555.eyesave.SecondsTimerWithEvents;

public class BreakTimer {

    private SecondsTimerWithEvents timer;
    private TimerDisplayController displayController;

    public BreakTimer(Text timerDisplay, long millisLeft, Runnable onFinish) {
        displayController = new TimerDisplayController(timerDisplay);
        setupTimer(millisLeft, onFinish);
    }

    private void setupTimer(long millisLeft, Runnable onFinish) {
        Runnable onEverySecond = () -> Platform.runLater(this::setTimeOnDisplay);
        timer = new SecondsTimerWithEvents(millisLeft, onFinish, onEverySecond);
    }

    private void setTimeOnDisplay() {
        displayController.setTime(timer.getMillisLeft());
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}
