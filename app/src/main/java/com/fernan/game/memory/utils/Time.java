package com.fernan.game.memory.utils;

import java.util.Locale;

public class Time {
    public static String getMinutes(int time) {
        int min = time / 60;
        int sec = time - min*60;
        String format = "%02d:%02d";
        return String.format(Locale.getDefault(),format, min,sec);
    }
}
