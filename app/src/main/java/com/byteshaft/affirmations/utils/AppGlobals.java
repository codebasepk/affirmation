package com.byteshaft.affirmations.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppGlobals extends Application {

    private static Context sContext;
    public static final String TODAYS_NUMBER = "number";
    public static final String KEY_BOOLEAN = "boolean";
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }

    // get default sharedPreferences.
    private static SharedPreferences getPreferenceManager() {
        return PreferenceManager.getDefaultSharedPreferences(AppGlobals.getContext());
    }

    public static void saveDataToSharedPreferences(String key, int value) {
        SharedPreferences sharedPreferences = getPreferenceManager();
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static int getDataFromSharedPreferences(String key) {
        SharedPreferences sharedPreferences = getPreferenceManager();
        return sharedPreferences.getInt(key, -1);
    }


    public static void saveState(boolean type) {
        SharedPreferences sharedPreferences = getPreferenceManager();
        sharedPreferences.edit().putBoolean(AppGlobals.KEY_BOOLEAN, type).apply();
    }

    public static boolean isEnabled() {
        SharedPreferences sharedPreferences = getPreferenceManager();
        return sharedPreferences.getBoolean(AppGlobals.KEY_BOOLEAN, false);
    }
}
