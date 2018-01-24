//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.silence.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Objs {
    public Objs() {
    }

    public static void serialize(Object obj, String path) {
        try {
            ObjectOutputStream e = new ObjectOutputStream(new FileOutputStream(path));
            e.writeObject(obj);
            e.flush();
            e.close();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public static Object deserialize(String path) {
        try {
            ObjectInputStream e = new ObjectInputStream(new FileInputStream(path));
            Object obj = e.readObject();
            e.close();
            return obj;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }
}
