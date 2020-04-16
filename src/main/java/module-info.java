module Eyesave {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires java.desktop;
    requires javafx.swing;
    requires commons.lang3;

    opens ru.dimagor555.eyesave to javafx.fxml;
    opens ru.dimagor555.eyesave.mainwindow to javafx.fxml;
    opens ru.dimagor555.eyesave.notificationwindow to javafx.fxml;
    exports ru.dimagor555.eyesave;
    exports ru.dimagor555.eyesave.controllers;
}