package com.geekbrains.android_lessons;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.geekbrains.android_lessons.fragments.MainFragment;
import com.geekbrains.android_lessons.model.AllList;
import com.geekbrains.android_lessons.model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class getWeather {


    private static final Gson gson = new Gson();

    public static void getWeather( MainFragment parent) {
        try {
            URL url = new URL(Constants.urlWeatherStatic +
                    URLEncoder.encode(parent.getCityName())+Constants.final_url);
            Handler handler = new Handler(Looper.getMainLooper());
            new Thread(() -> {
                HttpsURLConnection urlConnection;
                try {
                    urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(1000);
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String result = getLines(in);

                    WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                    handler.post(() -> parent.displayWeather(weatherRequest));
                    urlConnection.disconnect();
                } catch (IOException e) {
                    handler.post(parent::error);
                }
            }).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void getWeatherForecast(MainFragment parent) {
        try {
            URL url = new URL(Constants.urlWeather7Days +
                    URLEncoder.encode(parent.getCityName())+Constants.final_url);
          Handler handler = new Handler(Looper.getMainLooper());
                new Thread(() -> {
                    HttpsURLConnection urlConnection;
                    try {
                        urlConnection = (HttpsURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(1000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String result = getLines(in);

                        AllList weatherList = gson.fromJson(result, AllList.class);
                        handler.post(() -> parent.setupRecyclerView(weatherList.getList()));
                        urlConnection.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
    }

    private static String getLines(BufferedReader in) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return in.lines().collect(Collectors.joining("\n"));
        }
        return null;
    }
}
