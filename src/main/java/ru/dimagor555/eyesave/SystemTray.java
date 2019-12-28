package ru.dimagor555.eyesave;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class SystemTray {

    public static String ICON_PATH = "/TrayIcon.png";

    public static void addAppToTray() {
        try {
            java.awt.Toolkit.getDefaultToolkit();

            if (!java.awt.SystemTray.isSupported()) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Tray is not supported");
            }

            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            URL imageLoc = new URL(Main.class.getResource(ICON_PATH).toExternalForm());
            java.awt.Image image = ImageIO.read(imageLoc);
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);
            trayIcon.setImageAutoSize(true);

            trayIcon.addActionListener(event -> showStage(tray, trayIcon));

            java.awt.MenuItem openItem = new java.awt.MenuItem("Show");
            openItem.addActionListener(event -> showStage(tray, trayIcon));

            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            openItem.setFont(boldFont);

            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            exitItem.addActionListener(event -> {
                Platform.exit();
                tray.remove(trayIcon);
                System.exit(0);
            });

            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

            tray.add(trayIcon);
            dontCloseNotificationWindowIfOpen();
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void dontCloseNotificationWindowIfOpen() {
        var notificationWindow = Main.notificator.notificationWindowController.notificationWindow;
        if (notificationWindow != null) {
            Platform.runLater(() ->
                    Main.notificator.notificationWindowController.notificationWindow.show());
        }
    }

    private static void showStage(java.awt.SystemTray tray, TrayIcon trayIcon) {
        Platform.runLater(Main::showStage);
        tray.remove(trayIcon);
    }
}
