package ru.dimagor555.eyesave;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ru.dimagor555.eyesave.settings.Settings;

public class SoundPlayer {

    public static final String NOTIFICATION_SOUND_PATH = "/sounds/NotificationSound.wav";

    private MediaPlayer mediaPlayer;
    private Media sound;

    public SoundPlayer() {
        sound = new Media(Main.class.getResource(NOTIFICATION_SOUND_PATH).toExternalForm());
    }

    public void playNotificationSound() {
        if (!Settings.playSound) {
            return;
        }
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.dispose());
        mediaPlayer.setOnReady(() -> mediaPlayer.play());
    }
}
