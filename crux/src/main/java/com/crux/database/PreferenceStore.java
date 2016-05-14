package com.crux.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.crux.Crux;
import com.crux.util.MapUtils;

import java.util.Map;
import java.util.Set;

/**
 * Used for retaining key/value pair in {@link SharedPreferences}. Supports only basic object types : String, Integer, Long, Float,
 * Boolean, Set<String>
 *
 * @author gauravarora
 * @since 14/05/16.
 */
public class PreferenceStore {

    private SharedPreferences sharedPreferences;

    public PreferenceStore() {
        Context context = Crux.getContext();
        sharedPreferences = context.getSharedPreferences("PreferenceStore", Context.MODE_PRIVATE);
    }

    public <T> T getValue(String key) {
        Map<String, ?> map = getAllValues();
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        return (T) map.get(key);
    }

    public <T> void saveValue(String key, T value) {
        if (value == null) {
            return;
        }

        if (value instanceof String) {
            saveString(key, (String) value);
        } else if (value instanceof Integer) {
            saveInteger(key, (Integer) value);
        } else if (value instanceof Long) {
            saveLong(key, (Long) value);
        } else if (value instanceof Float) {
            saveFloat(key, (Float) value);
        } else if (value instanceof Boolean) {
            saveBoolean(key, (Boolean) value);
        } else if (value instanceof Set) {
            saveStringSet(key, (Set<String>) value);
        } else {
            throw new IllegalArgumentException("Unsupported object for shared preferences");
        }
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void saveInteger(String key, Integer value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public Integer getInteger(String key, Integer defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void saveLong(String key, Long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public Long getString(String key, Long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public void saveFloat(String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void saveStringSet(String key, Set<String> value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return sharedPreferences.getStringSet(key, defaultValue);
    }

    public void removeKey(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public void clearStore() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public Map<String, ?> getAllValues() {
        return sharedPreferences.getAll();
    }

}
