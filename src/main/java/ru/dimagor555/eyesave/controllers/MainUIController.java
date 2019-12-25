package ru.dimagor555.eyesave.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.dimagor555.eyesave.Main;
import ru.dimagor555.eyesave.SystemTray;
import ru.dimagor555.eyesave.settings.Profile;
import ru.dimagor555.eyesave.settings.Settings;

public class MainUIController {

    private static final String PATH_TO_PROFILE_CREATION_PANE = "/fxml/ProfileCreationPane.fxml";

    @FXML
    private Button hideBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button createNewProfileBtn;

    @FXML
    private ChoiceBox<Profile> profileSelectChoiceBox;

    @FXML
    private Button deleteBtn;

    @FXML
    private ChoiceBox<Profile> profileDeleteChoiceBox;

    @FXML
    private Label deleteErrLabel;

    @FXML
    private CheckBox hideOnTrayCheckBox;

    @FXML
    private CheckBox playSoundCheckBox;

    public MainUIController() {

    }

    public MainUIController(Pane root) {
        hideBtn = (Button) root.lookup("#hideBtn");
        exitBtn = (Button) root.lookup("#exitBtn");
        createNewProfileBtn = (Button) root.lookup("#createNewProfileBtn");
        deleteBtn = (Button) root.lookup("#deleteBtn");
        profileSelectChoiceBox = (ChoiceBox<Profile>) root.lookup("#profileSelectChoiceBox");
        profileDeleteChoiceBox = (ChoiceBox<Profile>) root.lookup("#profileDeleteChoiceBox");
        deleteErrLabel = (Label) root.lookup("#deleteErrLabel");
        hideOnTrayCheckBox = (CheckBox) root.lookup("#hideOnTrayCheckBox");
        playSoundCheckBox = (CheckBox) root.lookup("#playSoundCheckBox");

        ButtonGraphicEffects.addBtnClickEffect(hideBtn);
        hideBtn.setOnAction(event -> hideToTray());
        ButtonGraphicEffects.addBtnClickEffect(exitBtn);
        exitBtn.setOnAction(event -> System.exit(0));
        ButtonGraphicEffects.addBtnClickEffect(createNewProfileBtn);
        createNewProfileBtn.setOnAction(event -> createNewProfile());
        ButtonGraphicEffects.addBtnClickEffect(deleteBtn);
        deleteBtn.setOnAction(event -> deleteProfile());

        profileSelectChoiceBox.setOnAction(event ->
                Settings.changeCurrentProfile(profileSelectChoiceBox.getValue()));

        hideOnTrayCheckBox.setOnAction(event ->
                Settings.changeHideInTrayAtFirstRun(hideOnTrayCheckBox.isSelected()));

        playSoundCheckBox.setOnAction(event ->
                Settings.changePlaySound(playSoundCheckBox.isSelected()));

        updateProfileChoiceBoxesItems();
        hideOnTrayCheckBox.setSelected(Settings.hideInTrayAtFirstRun);
        playSoundCheckBox.setSelected(Settings.playSound);
    }

    private void createNewProfile() {
        var profileCreationWindow = new Stage();
        profileCreationWindow.initOwner(Main.stage);
        profileCreationWindow.initModality(Modality.WINDOW_MODAL);
        profileCreationWindow.setTitle("Create new profile");
        var profileCreationPane = Main.loader.loadPane(PATH_TO_PROFILE_CREATION_PANE);
        new ProfileCreationController(profileCreationPane, profileCreationWindow);
        profileCreationWindow.setScene(new Scene(profileCreationPane));
        profileCreationWindow.setResizable(false);
        profileCreationWindow.setAlwaysOnTop(true);
        profileCreationWindow.setOnCloseRequest(event -> profileCreationWindow.close());

        profileCreationWindow.showAndWait();
    }

    private void deleteProfile() {
        var profileToDelete = profileDeleteChoiceBox.getValue();
        if (profileToDelete == Settings.currentProfile) {
            showDeleteErrorLabelFor5Seconds();
        } else {
            Settings.removeProfile(profileToDelete);
        }
    }

    private void showDeleteErrorLabelFor5Seconds() {
        deleteErrLabel.setVisible(true);
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> deleteErrLabel.setVisible(false));
        }).start();
    }

    public void updateProfileChoiceBoxesItems() {
        updateProfileSelectChoiceBoxItems();
        updateProfileDeleteChoiceBoxItems();
    }

    private void updateProfileSelectChoiceBoxItems() {
        profileSelectChoiceBox.setItems(FXCollections.observableArrayList(Settings.getProfiles()));
        profileSelectChoiceBox.setValue(Settings.currentProfile);
    }

    private void updateProfileDeleteChoiceBoxItems() {
        profileDeleteChoiceBox.setItems(FXCollections.observableArrayList(Settings.getProfiles()));
    }

    private void hideToTray() {
        SystemTray.addAppToTray();
        Main.closeStage();
    }
}
