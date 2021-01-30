package com.geekbrains.android_lessons.model;

import android.app.Activity;

import com.geekbrains.android_lessons.Constants;
import com.geekbrains.android_lessons.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class AllList implements Serializable {

    private String dayOfWeek;
    private final SimpleDateFormat dateFormat= Constants.DD_MMMM;
    private static final SimpleDateFormat weekDayFormat=Constants.weekDayFormat;
    private String date;
    private String dt;
    private WeatherRequest[] list;
    private static ArrayList<String> days;

    public WeatherRequest[] getList(){return list;}



    public static ArrayList<AllList> getDays(int value, Activity parent) {

        ArrayList<AllList> arrayList = new ArrayList<>();
        days = new ArrayList<>(Arrays.asList(parent.getResources().getStringArray(R.array.weekday)));

        int shift = 0;

        for (int i = 0; i < value; i++) {
            AllList day = new AllList();
            day.generateData(shift);
            arrayList.add(day);
            if (day.dayOfWeek.equals(days.get(days.size() - 1))) {
                String rawString = weekDayFormat.format(Calendar.getInstance().getTime());
                String currentDay = rawString.substring(0, 1).toUpperCase() + rawString.substring(1);
                shift = -days.indexOf(currentDay);
                continue;
            }
            shift++;
        }
        return arrayList;
    }

    public static ArrayList<AllList> getHours(int value) {
        ArrayList<AllList> arrayList = new ArrayList<>();
        for (int i = 0; i < value; i++) {
            AllList hour = new AllList();
            arrayList.add(hour);
        }
        return arrayList;
    }

    private void generateData(int shift) {
        Calendar now = Calendar.getInstance();
        if (shift>=0) {
            now.add(Calendar.DAY_OF_MONTH, shift);
        }
        else {
            now.add(Calendar.DAY_OF_MONTH, shift+7);
        }
        dt=Constants.YYYYM_MDD.format(now.getTime());
        date = dateFormat.format(now.getTime());
        dayOfWeek = getDayName(shift);


    }

    private String getDayName(int shift) {
        String rawString = weekDayFormat.format(Calendar.getInstance().getTime());
        String currentDay = rawString.substring(0, 1).toUpperCase() + rawString.substring(1);
        return days.get(days.indexOf(currentDay) + shift);
    }


    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String  getDay(){
        return date;
    }
    public String  getDt(){
        return dt;
    }

    public void setList(WeatherRequest[] list) {
        this.list = list;
    }
}
