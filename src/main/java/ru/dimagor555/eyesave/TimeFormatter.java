package ru.dimagor555.eyesave;

public class TimeFormatter {

    public static final int MILLIS_IN_SECOND = 1000;
    public static final int MILLIS_IN_MINUTE = 60000;

    public String formatTime(int millis) {
        int minutes = calculateMinutes(millis);
        int seconds = calculateSeconds(millis - (minutes * MILLIS_IN_MINUTE));

        var secondsStr = seconds < 10 ? "0" + seconds : seconds;
        var minutesStr = minutes < 10 ? "0" + minutes : minutes;

        return minutesStr + ":" + secondsStr;
    }

    private int calculateMinutes(int millis) {
        return millis / MILLIS_IN_MINUTE;
    }

    private int calculateSeconds(int millis) {
        return millis / MILLIS_IN_SECOND;
    }
}
