package ru.dimagor555.eyesave.settings;

import ru.dimagor555.eyesave.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Settings {

    public static final String SETTINGS_DIR =
            System.getProperty("user.home") + File.separator + ".eyesave";
    public static final String PROFILES_FILE_NAME = "profiles.data";
    public static final String SETTINGS_FILE_NAME = "settings.data";

    private static ArrayList<Profile> profiles = new ArrayList<>();
    public static Profile currentProfile;
    public static boolean hideInTrayAtFirstRun = false;

    public static void addProfile(Profile profile) {
        profiles.add(profile);
        saveProfiles();
        Main.mainUIController.updateProfileChoiceBoxesItems();
    }

    public static void removeProfile(Profile profile) {
        profiles.remove(profile);
        saveProfiles();
        Main.mainUIController.updateProfileChoiceBoxesItems();
    }

    public static ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public static void changeCurrentProfile(Profile profile) {
        if (profile == null) {
            return;
        }
        var lastCurrProfile = currentProfile;
        currentProfile = profile;
        saveSettings();
        if (Main.notificator != null && !Objects.equals(lastCurrProfile, profile)) {
            Main.notificator.restart();
            Main.notificator.setProfile(currentProfile);
        }
    }

    public static void changeHideInTrayAtFirstRun(boolean value) {
        hideInTrayAtFirstRun = value;
        saveSettings();
    }

    private static void saveSettings() {
        var path = SETTINGS_DIR + File.separator + SETTINGS_FILE_NAME;
        var settingsToSave = new SerializableSettings(currentProfile, hideInTrayAtFirstRun);
        Serializer.write(settingsToSave, path);
    }

    private static void saveProfiles() {
        if (profiles == null) {
            profiles = new ArrayList<>();
        }
        var path = SETTINGS_DIR + File.separator + PROFILES_FILE_NAME;
        Serializer.write(profiles, path);
    }

    public static void readSettingsData() {
        readProfiles();
        readSettings();
    }

    private static void readSettings() {
        var path = SETTINGS_DIR + File.separator + SETTINGS_FILE_NAME;
        var deserializedSettings = (SerializableSettings) Serializer.read(path);
        if (deserializedSettings != null) {
            if (deserializedSettings.currentProfile != null) {
                for (var profile :
                        profiles) {
                    if (deserializedSettings.currentProfile.equals(profile)) {
                        currentProfile = profile;
                        break;
                    }
                }
            }
            hideInTrayAtFirstRun = deserializedSettings.hideInTrayAtFirstRun; }
    }

    private static void readProfiles() {
        var path = SETTINGS_DIR + File.separator + PROFILES_FILE_NAME;
        var deserializedProfiles = (ArrayList<Profile>) Serializer.read(path);
        if (deserializedProfiles != null) {
            profiles = deserializedProfiles;
        }
    }
}
