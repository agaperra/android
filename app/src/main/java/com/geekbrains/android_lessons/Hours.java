package com.geekbrains.android_lessons;

import android.annotation.SuppressLint;
import android.app.Activity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class Hours implements Serializable {

    private String hourInADay;
    @SuppressLint({"SimpleDateFormat", "ConstantLocale"})
    private static final SimpleDateFormat hoursFormat=Constants.hoursFormat;

    private static ArrayList<String> hours;

    public static ArrayList<Hours> getHours(int value, Activity parent) {
        ArrayList<Hours> arrayList = new ArrayList<>();
        hours = new ArrayList<>(Arrays.asList(parent.getResources().getStringArray(R.array.hours_in_a_day)));

        int shift = 0;

        for (int i = 0; i < value; i++) {
            Hours day = new Hours();
            day.generateData(shift);
            arrayList.add(day);
            if (day.hourInADay.equals(hours.get(hours.size() - 1))) {
                shift = -hours.indexOf(hoursFormat.format(Calendar.getInstance().getTime()));
                continue;
            }
            shift++;
        }
        return arrayList;
    }

    private void generateData(int shift) {
        hourInADay = getHourTime(shift);
    }

    private String getHourTime(int shift) {
        return hours.get(hours.indexOf(hoursFormat.format(Calendar.getInstance().getTime())) + shift);
    }


    public String getHourInADay() {
        return hourInADay;
    }

}


