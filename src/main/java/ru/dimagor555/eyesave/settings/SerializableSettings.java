package ru.dimagor555.eyesave.settings;

import java.io.Serializable;

public class SerializableSettings implements Serializable {

    public final static long serialVersionUID = 171717L;

    public Profile currentProfile;
    public boolean hideInTrayAtFirstRun;
    public boolean playSound;
    public boolean addToStartup;
    public boolean repeatNotification;

    public SerializableSettings(Profile currentProfile, boolean hideInTrayAtFirstRun,
                                boolean playSound, boolean addToStartup, boolean repeatNotification) {
        this.currentProfile = currentProfile;
        this.hideInTrayAtFirstRun = hideInTrayAtFirstRun;
        this.playSound = playSound;
        this.addToStartup = addToStartup;
        this.repeatNotification = repeatNotification;
    }
}
