package com.geekbrains.android_lessons;

import android.annotation.SuppressLint;
import android.app.Activity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class WeekDay implements Serializable {

    private String dayOfWeek;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMMM", RUSSIA);
    private String date;
    static Locale RUSSIA = new Locale("ru","RU");

    private static ArrayList<String> days;

    public static ArrayList<WeekDay> getDays(int value, Activity parent) {

        //Locale.setDefault(RUSSIA);
        ArrayList<WeekDay> arrayList = new ArrayList<>();
        days = new ArrayList<>(Arrays.asList(parent.getResources().getStringArray(R.array.weekday)));
        new SimpleDateFormat("EEEE", RUSSIA).format(Calendar.getInstance().getTime());

        int shift = 0;

        for (int i = 0; i < value; i++) {
            WeekDay day = new WeekDay();
            day.generateData(shift);
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

    private void generateData(int shift) {
        Calendar now = Calendar.getInstance();
        int days ;
        if (shift>=0) {
            days = (Calendar.SATURDAY - now.get(Calendar.DAY_OF_WEEK) + shift) % 7;
        }
        else{
            days = (Calendar.SATURDAY - now.get(Calendar.DAY_OF_WEEK) + shift+7) % 7;
        }
        now.add(Calendar.DAY_OF_YEAR, days);
        date = simpleDateFormat.format(now.getTime());
        dayOfWeek = getDayName(shift);
    }

    private String getDayName(int shift) {
        String rawString = new SimpleDateFormat("EEEE", Locale.forLanguageTag(Locale.getDefault().getLanguage())).format(Calendar.getInstance().getTime());
        String currentDay = rawString.substring(0, 1).toUpperCase() + rawString.substring(1);
        return days.get(days.indexOf(currentDay) + shift);
    }


    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String  getDay(){return date;}
}

