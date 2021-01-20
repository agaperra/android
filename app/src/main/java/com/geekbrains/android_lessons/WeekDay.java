package com.geekbrains.android_lessons;

import android.app.Activity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class WeekDay implements Serializable {

    private String temperature;
    private String dayOfWeek;

    private static ArrayList<String> days;

    public static ArrayList<WeekDay> getDays(int value, Activity parent) {

        ArrayList<WeekDay> arrayList = new ArrayList<>();
        days = new ArrayList<>(Arrays.asList(parent.getResources().getStringArray(R.array.weekday)));
        new SimpleDateFormat("EEEE", new Locale("ru", "RF")).format(Calendar.getInstance().getTime());

        int shift = 0;

        for (int i = 0; i < value; i++) {
            WeekDay day = new WeekDay();
            day.generateData(shift, parent);
            arrayList.add(day);
            if (day.dayOfWeek.equals(days.get(days.size() - 1))) {
                String rawString = new SimpleDateFormat("EEEE", Locale.forLanguageTag(Locale.getDefault().getLanguage())).format(Calendar.getInstance().getTime());
                String currentDay = rawString.substring(0, 1).toUpperCase() + rawString.substring(1);
                shift = -days.indexOf(currentDay);
                continue;
            }
            shift++;
        }
        return arrayList;
    }

    private void generateData(int shift, Activity parent) {
        dayOfWeek = getDayName(shift);
        temperature = (int) (Math.random() * 40) + parent.getResources().getString(R.string.cels);
    }

    private String getDayName(int shift) {
        String rawString = new SimpleDateFormat("EEEE", Locale.forLanguageTag(Locale.getDefault().getLanguage())).format(Calendar.getInstance().getTime());
        String currentDay = rawString.substring(0, 1).toUpperCase() + rawString.substring(1);
        return days.get(days.indexOf(currentDay) + shift);
    }


    public String getTemperature() {
        return temperature;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }
}

