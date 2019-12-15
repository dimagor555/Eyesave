package ru.dimagor555.eyesave;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Loader {

    public Pane loadPane(String pathToFile) {
        var path = getClass().getResource(pathToFile);
        try {
            return FXMLLoader.load(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
