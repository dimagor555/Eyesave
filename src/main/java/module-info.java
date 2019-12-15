module Eyesave {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires java.desktop;
    requires javafx.swing;

    opens ru.dimagor555.eyesave to javafx.fxml;
    opens ru.dimagor555.eyesave.controllers to javafx.fxml;
    exports ru.dimagor555.eyesave;
    exports ru.dimagor555.eyesave.controllers;
}