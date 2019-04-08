package com.ukk.latihan1.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPref{

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void putPref(Context ctx, String Key, String value) {
        SharedPreferences.Editor editor = getPreferences(ctx).edit();
        editor.putString(Key, value);
        editor.apply();
    }

    public static String getPref(Context ctx, String key) {
        return getPreferences(ctx).getString(key, "");
    }

    public static void setLogined(Context ctx){
        SharedPreferences.Editor editor = getPreferences(ctx).edit();
        editor.putBoolean(KeyVal.logined, true);
        editor.apply();
    }

    public static Boolean isLogined(Context ctx) {
        return getPreferences(ctx).getBoolean(KeyVal.logined, false);
    }

    public static void logout(Context ctx){
        SharedPreferences.Editor editor = getPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}
