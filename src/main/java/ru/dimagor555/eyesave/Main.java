package ru.dimagor555.eyesave;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.dimagor555.eyesave.controllers.MainUIController;
import ru.dimagor555.eyesave.settings.Settings;

public class Main extends Application {

    public static final String PATH_TO_MAIN_PANE = "/fxml/Main.fxml";

    public static Stage stage;
    public static Pane root;
    public static Loader loader = new Loader();
    public static Notificator notificator;
    public static MainUIController mainUIController;
    public static SoundPlayer soundPlayer = new SoundPlayer();

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = loader.loadPane(PATH_TO_MAIN_PANE);
        mainUIController = new MainUIController(root);
        var scene = new Scene(root);

        primaryStage = new Stage();
        stage = primaryStage;
        primaryStage.setScene(scene);
        primaryStage.setTitle("Eyesave");
        primaryStage.getIcons()
                .add(new Image(Main.class.getResource(SystemTray.ICON_PATH).toExternalForm()));
        primaryStage.setOnCloseRequest(event -> SystemTray.addAppToTray());
        primaryStage.setResizable(false);
        Platform.setImplicitExit(false);

        notificator = new Notificator();
        notificator.setProfile(Settings.currentProfile);
        notificator.start();

        if (!Settings.hideInTrayAtFirstRun) {
            showStage();
        } else {
            SystemTray.addAppToTray();
        }
    }

    public static void showStage() {
        stage.show();
        stage.toFront();
    }

    public static void closeStage() {
        stage.close();
    }

    @Override
    public void init() throws Exception {
        Settings.readSettingsData();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
