package ru.dimagor555.eyesave.settings;

import org.apache.commons.lang3.SystemUtils;

import java.io.*;
import java.nio.file.*;

public class StartupManager {

    public void setStartup(boolean value) {
        final Path currDir = Paths.get("").toAbsolutePath();

        if (SystemUtils.IS_OS_WINDOWS) {
            setStartupWin(value, currDir);
        } else if (SystemUtils.IS_OS_LINUX) {
            setStartupLinux(value, currDir);
        }
    }

    private void setStartupWin(boolean value, Path currDir) {
        final String regKeyName = "\"Eyesave\"";
        String pathToExeStarter = currDir
                .toString().replaceAll("/", "\\\\") + "\\Eyesave.exe";

        String execString;
        if (value) {
            execString = "cmd /C reg add HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v " +
                    regKeyName + " /t REG_SZ /d \"" + pathToExeStarter + "\" /f";
        } else {
            execString = "cmd /C reg delete HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run " +
                    "/v " + regKeyName + " /f\r\n";
        }

        try {
            Runtime.getRuntime().exec(execString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setStartupLinux(boolean value, Path currDir) {
        final String startupCommand = currDir.toString() + "/Eyesave.sh";
        File startupScript = new File("Eyesave.desktop");
        String pathToStartupDir = SystemUtils.getUserHome() + "/.config/autostart";

        if (value) {
            try {
                FileWriter fileWriter = new FileWriter(startupScript);
                String startupFileStr =
                        "[Desktop Entry]\n" +
                                "Type=Application\n" +
                                "Exec=" + startupCommand + "\n" +
                                "Hidden=false\n" +
                                "NoDisplay=false\n" +
                                "X-GNOME-Autostart-enabled=true\n" +
                                "Name[en_US]=Eyesave\n" +
                                "Name=Eyesave\n" +
                                "Comment=Time management app for your health\n";
                fileWriter.write(startupFileStr);
                fileWriter.flush();
                fileWriter.close();

                Runtime.getRuntime().exec("mv " + startupScript.getName() + " " + pathToStartupDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Files.delete(Paths.get(pathToStartupDir + "/" + startupScript.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
