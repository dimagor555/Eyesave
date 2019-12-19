package ru.dimagor555.eyesave.settings;

import java.io.Serializable;

public class SerializableSettings implements Serializable {

    public final static long serialVersionUID = 171717L;

    public Profile currentProfile;
    public boolean hideInTrayAtFirstRun;

    public SerializableSettings(Profile currentProfile, boolean hideInTrayAtFirstRun) {
        this.currentProfile = currentProfile;
        this.hideInTrayAtFirstRun = hideInTrayAtFirstRun;
    }
}
