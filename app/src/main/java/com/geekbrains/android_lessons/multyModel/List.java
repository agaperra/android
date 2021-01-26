package com.geekbrains.android_lessons.multyModel;

public class List {

    private long dt;
    private Main main;
    private Weather[] weather;
    private Wind wind;
    private String name;

    public long getDT() {
        return dt;
    }

    public void setDT(long dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather= weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind= wind;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
