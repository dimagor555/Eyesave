package ru.dimagor555.eyesave.notificationwindow;

import javafx.scene.text.Text;

public class TimerDisplayController {

    private Text display;

    public TimerDisplayController(Text display) {
        this.display = display;
    }

    private TimerDisplayTimeFormatter timeFormatter = new TimerDisplayTimeFormatter();

    public void setTime(long millis) {
        var timeStr = timeFormatter.formatTime((int) millis);
        display.setText(timeStr);
    }
}
