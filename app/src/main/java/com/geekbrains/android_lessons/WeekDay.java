package com.geekbrains.android_lessons;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;

import com.geekbrains.android_lessons.fragments.MainFragment;
import com.geekbrains.android_lessons.model.AllList;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class WeekDay implements Serializable {

    private String dayOfWeek;
    private String degrees;
    private String  iconW;
    private final SimpleDateFormat dateFormat=Constants.dateFormat;
    private static final SimpleDateFormat weekDayFormat=Constants.weekDayFormat;
    private String date;
    private static final SharedPreferencesManager sPrefs= MainFragment.sPrefs;

    private static ArrayList<String> days;

    public static ArrayList<WeekDay> getDays(int value, Activity parent) {

        //Locale.setDefault(RUSSIA);
        ArrayList<WeekDay> arrayList = new ArrayList<>();
        days = new ArrayList<>(Arrays.asList(parent.getResources().getStringArray(R.array.weekday)));

        int shift = 0;

        for (int i = 0; i < value; i++) {
            WeekDay day = new WeekDay();
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

    private void generateData(int shift) {
        Calendar now = Calendar.getInstance();

        if (shift>=0) {
            now.add(Calendar.DAY_OF_MONTH, shift);
            setData(Constants.urlWeather7Days +
                    URLEncoder.encode(getCityName()) +
                    Constants.lang+
                    Locale.forLanguageTag(Locale.getDefault().getLanguage())+
                    Constants.weatherKey, shift);
        }
        else {
            now.add(Calendar.DAY_OF_MONTH, shift+7);
            setData(Constants.urlWeather7Days +
                    URLEncoder.encode(getCityName()) +
                    Constants.lang+
                    Locale.forLanguageTag(Locale.getDefault().getLanguage())+
                    Constants.weatherKey, shift+7);
        }
        date = dateFormat.format(now.getTime());
        dayOfWeek = getDayName(shift);
        degrees=getDegrees();
        iconW=getIcon();


    }

    public  String getCityName() {
        return sPrefs.retrieveString(Constants.tag_cityName, "Moscow");
    }

    private String getDayName(int shift) {
        String rawString = weekDayFormat.format(Calendar.getInstance().getTime());
        String currentDay = rawString.substring(0, 1).toUpperCase() + rawString.substring(1);
        return days.get(days.indexOf(currentDay) + shift);
    }


    public String getIcon(){return iconW;}
    public String getDegrees(){return degrees;}
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String  getDay(){return date;}

//
//    @SuppressLint("DefaultLocale")
//    public void displayWeather(AllList list) {
//
//
//        degreesCountView.setText(String.format("%.2f", list.getList()[0].getMain().getTemp()));
//        sPrefs.storeString(Constants.PREF_DEGREES, String.format("%.2f", list.getList()[0].getMain().getTemp()));
//
//        DateFormat df = DateFormat.getDateTimeInstance();
//        String updatedOn = df.format(new Date(1000 * list.getList()[0].getDT()));
//        timeView.setText(String.format("%s", updatedOn));
//        sPrefs.storeString(Constants.tag_time, String.format("%s", updatedOn));
//
//
//        feelsLike.setText(String.format("%.2f", list.getList()[0].getMain().getFeels_like()));
//        sPrefs.storeString(Constants.PREF_FEEL, String.format("%.2f", list.getList()[0].getMain().getFeels_like()));
//
//    }

    public void setData(String url, int shift) {
        try {
            final URL uri = new URL(url);
            System.out.println(uri);
            final Handler handler = new Handler(Looper.getMainLooper()); // Запоминаем основной поток
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
                        urlConnection.setReadTimeout(10000); // установка таймаута - 10 00 миллисекунд
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
                        String result = getLines(in);
                        System.out.println(result);
                        // преобразование данных запроса в модель
                        Gson gson = new Gson();
                        final AllList list = gson.fromJson(result, AllList.class);
                        // Возвращаемся к основному потоку
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayWeather(list,shift);
                            }
                        });
                    } catch (Exception e) {
                        //Log.e(Constants.TAG, getString(R.string.fall), e);
                        e.printStackTrace();
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        }
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            //Log.e(Constants.TAG, getString(R.string.fall), e);
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }

    @SuppressLint("DefaultLocale")
    public void displayWeather(AllList list, int shift) {

        degrees=String.format("%.2f", list.getList()[shift].getMain().getTemp());

        String  icon = list.getList()[shift].getWeather()[0].getIcon();

        iconW = "http://openweathermap.org/img/wn/" + icon + "@4x.png";

        //feelsLike.setText(String.format("%.2f", weatherRequest.getMain().getFeels_like()));
    }
}

