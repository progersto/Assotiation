package com.natife.assotiation.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferUtil {

    private String TIME_MOVE_KEY = "timeMove";
    private String TIME_GAME_KEY = "timeGame";
    private String NUMBER_CIRCLE_KEY = "numberCircles";
    private String COLOR_DRAW_KEY = "numberCircles";


    public void saveTimeMove(Context context, int value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(TIME_MOVE_KEY, value);
        editor.apply();
    }

    public int restoreTimeMove(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(TIME_MOVE_KEY, 0);
    }

    public void saveTimeGame(Context context, int value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(TIME_GAME_KEY, value);
        editor.apply();
    }

    public int restoreTimeGame(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(TIME_GAME_KEY, 0);
    }

    public void saveNumberCircles(Context context, int value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NUMBER_CIRCLE_KEY, value);
        editor.apply();
    }

    public int restoreNumberCircles(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(NUMBER_CIRCLE_KEY, 0);
    }

    public void saveColorDraw(Context context, int value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(COLOR_DRAW_KEY, value);
        editor.apply();
    }

    public int restoreColorDraw(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(COLOR_DRAW_KEY, 0);
    }
}
