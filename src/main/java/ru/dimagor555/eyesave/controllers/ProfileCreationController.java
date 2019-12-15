package ru.dimagor555.eyesave.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.dimagor555.eyesave.Notificator;
import ru.dimagor555.eyesave.settings.Profile;
import ru.dimagor555.eyesave.settings.Settings;

import java.util.Objects;

public class ProfileCreationController {

    public static final String SECONDS = "seconds";
    public static final String MINUTES = "minutes";

    @FXML
    private TextField notificationFrequencyInput;

    @FXML
    private TextField notificationDurationInput;

    @FXML
    private Button createBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private ChoiceBox<String> durationUnitChoiceBox;

    public ProfileCreationController() {

    }

    public ProfileCreationController(Pane root, Stage window) {
        notificationFrequencyInput = (TextField) root.lookup("#notificationFrequencyInput");
        notificationDurationInput = (TextField) root.lookup("#notificationDurationInput");
        createBtn = (Button) root.lookup("#createBtn");
        cancelBtn = (Button) root.lookup("#cancelBtn");
        durationUnitChoiceBox = (ChoiceBox<String>) root.lookup("#durationUnitChoiceBox");

        notificationFrequencyInput.setOnKeyReleased(event ->
                handleInputLimits(event, notificationFrequencyInput.getText()));
        notificationDurationInput.setOnKeyReleased(event ->
                handleInputLimits(event, notificationDurationInput.getText()));

        ButtonGraphicEffects.addBtnClickEffect(createBtn);
        createBtn.setOnAction(event -> createProfile(window));
        ButtonGraphicEffects.addBtnClickEffect(cancelBtn);
        cancelBtn.setOnAction(event -> window.close());

        durationUnitChoiceBox.setItems(FXCollections.observableArrayList(SECONDS, MINUTES));
    }

    private void createProfile(Stage window) {
        String frequencyText = notificationFrequencyInput.getText();
        String durationText = notificationDurationInput.getText();


        if (Objects.equals(frequencyText, "") || Objects.equals(durationText, "")
                || durationUnitChoiceBox.getValue() == null) {
            return;
        }

        int frequency = Integer.parseInt(frequencyText);

        int durationValue = Integer.parseInt(durationText);
        boolean durationInSeconds = durationUnitChoiceBox.getValue().equals(SECONDS);
        int duration = durationInSeconds ?
                durationValue : durationValue * Notificator.SECONDS_IN_MINUTE;

        String name = Profile.createName(frequency, duration, durationInSeconds);

        var profile = new Profile(name, frequency, duration);
        Settings.addProfile(profile);

        window.close();
    }

    private void handleInputLimits(KeyEvent event, String input) {
        if ((!event.getCode().isDigitKey() && !event.getCode().isArrowKey())
                || input.length() > 3) {
            if (event.getSource() instanceof TextField) {
                ((TextField) event.getSource()).clear();
            }
        }
    }

}
