package com.crux.database;

import com.crux.util.IOUtils;

/**
 * Dumps your object to a file. Use this for cases where you don't want to save data to tables/traditional databases
 *
 * @author gauravarora
 * @since 29/04/16.
 */
public class DataDump {

    public static boolean saveData(String key, Object value) {
        try {
            IOUtils.writeToFile(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static <T> T getData(String key, Class<T> clazz) {
        try {
            return IOUtils.readFromFile(key, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
