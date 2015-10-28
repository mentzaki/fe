package org.fe.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yew_mentzaki
 */
public class FSerialization {

    public static void writeToFile(byte[] bytes, File file) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.close();
    }

    public static byte[] readFromFile(File file) throws IOException {
        RandomAccessFile f = new RandomAccessFile(file, "r");
        byte[] b = new byte[(int) f.length()];
        f.read(b);
        return b;
    }

    public static byte[] serialize(Serializable o) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
        } catch (IOException ex) {
            Logger.getLogger(FSerialization.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        try {
            oos.writeObject(o);
        } catch (IOException ex) {
            Logger.getLogger(FSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
        return baos.toByteArray();
    }

    public static Object deserialize(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
        } catch (IOException ex) {
            Logger.getLogger(FSerialization.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        try {
            return ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(FSerialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Object deserializeAndMerge(byte[] bytes, Object merge) {
        Object o = deserialize(bytes);
        for (Field f : merge.getClass().getDeclaredFields()) {
            if ((f.getModifiers() & Modifier.TRANSIENT) == 0) {
                try {
                    f.set(merge, f.get(o));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Can't replace " + f.getName());
                } catch (IllegalAccessException ex) {
                    System.out.println("Can't replace " + f.getName());
                } catch (Exception e){
                    System.out.println(f.getName());
                    e.printStackTrace();
                }
            }
        }
        return merge;
    }
}
