package com.geekbrains.android_lessons;

import java.io.Serializable;

public class DataContainer implements Serializable {

    private final String cityName;
    private final String temperature;
    private final String humidity;
    private final String pressure;
    private final String windForce;

    private DataContainer(String cityName, String temperature, String humidity, String pressure, String windForce) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windForce = windForce;
    }

    public static DataContainer saveData(String currentLocation, String temperature, String humidity, String pressure, String windForce) {
        return new DataContainer(currentLocation, temperature, humidity, pressure, windForce);
    }

    public String getCurrentLocation() {
        return cityName;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWindForce() {
        return windForce;

    }
}
