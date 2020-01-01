package ru.dimagor555.eyesave.notificationwindow;

import javafx.scene.text.Text;
import ru.dimagor555.eyesave.TimeFormatter;

public class TimerDisplayController {

    private Text display;

    public TimerDisplayController(Text display) {
        this.display = display;
    }

    private TimeFormatter timeFormatter = new TimeFormatter();

    public void setTime(long millis) {
        var timeStr = timeFormatter.formatTime((int) millis);
        display.setText(timeStr);
    }
}
