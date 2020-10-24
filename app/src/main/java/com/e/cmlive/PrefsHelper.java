package com.e.cmlive;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsHelper {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    private static PrefsHelper instance;

    public static final String USER_ID = "user_id";
    public static final String DEVICE_TOKEN = "device_token";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String DOB = "dob";
    public static final String GENDER = "gender";
    public static final String STATUS = "status";
    public static final String PROFILE_IMAGE = "profile_image";

    public static PrefsHelper getPrefsHelper() {
        return instance;
    }

    public PrefsHelper(Context context) {
        instance = this;
        String prefsFile = context.getPackageName();
        sharedPreferences = context.getSharedPreferences(prefsFile, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void delete(String key) {
        if (sharedPreferences.contains(key)) {
            editor.remove(key).commit();
        }
    }

    public void savePref(String key, Object value) {
        delete(key);

        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Enum) {
            editor.putString(key, value.toString());
        } else if (value != null) {
            throw new RuntimeException("Attempting to save non-primitive preference");
        }

        editor.commit();
    }

    @SuppressWarnings("unchecked")
    public String getPref(String key) {
        return (String) sharedPreferences.getAll().get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key, T defValue) {
        T returnValue = (T) sharedPreferences.getAll().get(key);
        return returnValue == null ? defValue : returnValue;
    }

    public boolean isPrefExists(String key) {
        return sharedPreferences.contains(key);
    }

    public void clearAllPref() {
        editor.clear();
        editor.commit();

    }

}
