package ru.dimagor555.eyesave.settings;

import java.io.*;

public class Serializer {

    public static void write(Object o, String path) {
        var file = new File(path);
        file.getParentFile().mkdirs();
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object read(String path) {
        Object result = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream oin = new ObjectInputStream(fis);
            result = oin.readObject();
            oin.close();
            fis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
