package ru.dimagor555.eyesave.settings;

import ru.dimagor555.eyesave.Main;

import java.io.File;
import java.util.ArrayList;

public class Settings {

    public static final String SETTINGS_DIR =
            System.getProperty("user.home") + File.separator + ".eyesave";
    public static final String PROFILES_FILE_NAME = "profiles.data";
    public static final String SETTINGS_FILE_NAME = "settings.data";

    private static ArrayList<Profile> profiles = new ArrayList<>();
    public static Profile currentProfile;

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
        saveCurrentProfile();
        if (Main.notificator != null && !lastCurrProfile.equals(profile)) {
            Main.notificator.restart();
            Main.notificator.setProfile(currentProfile);
        }
    }

    private static void saveCurrentProfile() {
        var path = SETTINGS_DIR + File.separator + SETTINGS_FILE_NAME;
        Serializer.write(currentProfile, path);
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
        var deserializedSettings = (Profile) Serializer.read(path);
        if (deserializedSettings != null) {
            for (var profile :
                    profiles) {
                if (deserializedSettings.equals(profile)){
                    currentProfile = profile;
                    return;
                }
            }
        }
    }

    private static void readProfiles() {
        var path = SETTINGS_DIR + File.separator + PROFILES_FILE_NAME;
        var deserializedProfiles = (ArrayList<Profile>) Serializer.read(path);
        if (deserializedProfiles != null) {
            profiles = deserializedProfiles;
        }
    }
}
