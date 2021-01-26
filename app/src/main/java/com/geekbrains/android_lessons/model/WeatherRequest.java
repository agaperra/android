package com.geekbrains.android_lessons.model;

public class WeatherRequest {
    private Weather[] weather;
    private Main main;
    private Wind wind;
    private String name;
    private long dt;

    public long getDate(){return dt;}

    public void setDate(long dt){ this.dt=dt;}


    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
