package com.crux.util;

import android.content.Context;

import com.crux.Crux;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Utilities for performing IO operations
 *
 * @author gauravarora
 * @since 29/04/16.
 */
public class IOUtils {

    public static void writeToFile(String fileName, Object object) throws Exception {
        String text = new Gson().toJson(fileName);
        writeToFile(fileName, text);
    }

    public static <T> T readFromFile(String fileName, Class<T> clazz) throws Exception {
        String text = readFromFile(fileName);
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        return new Gson().fromJson(text, clazz);
    }

    public static void writeToFile(String fileName, String textToWrite, boolean append) throws Exception {
        Context context = Crux.getContext();
        File path = context.getFilesDir();

        File file = new File(path, fileName);
        FileOutputStream stream = new FileOutputStream(file, append);
        stream.write(textToWrite.getBytes());
        stream.close();
    }

    public static String readFromFile(String fileName) throws Exception {
        Context context = Crux.getContext();
        File path = context.getFilesDir();

        File file = new File(path, fileName);
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        FileInputStream in = new FileInputStream(file);
        in.read(bytes);
        in.close();
        return new String(bytes);
    }

}
