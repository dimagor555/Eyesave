package ru.dimagor555.eyesave.settings;

import ru.dimagor555.eyesave.Notificator;

import java.io.Serializable;

public class Profile implements Serializable {

    static final long serialVersionUID = 6889477762228107324L;

    private String name;
    private int frequency;
    private int duration;

    public Profile(String name, int frequency, int duration) {
        this.name = name;
        this.frequency = frequency;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getDuration() {
        return duration;
    }

    public boolean equals(Profile profile) {
        return name.equals(profile.name)
                && frequency == profile.frequency
                && duration == profile.duration;
    }

    public static String createName(int frequency, int duration, boolean durationInSeconds) {
        return "every " + frequency + " minutes for " +
                (durationInSeconds ? duration + " seconds"
                        : duration / Notificator.SECONDS_IN_MINUTE + " minutes");
    }

    @Override
    public String toString() {
        return name;
    }
}
