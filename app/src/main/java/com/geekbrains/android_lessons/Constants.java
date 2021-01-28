package com.geekbrains.android_lessons;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {
    public static final String PREF_DEGREES = "PREF_DEGREES";
    public static final String PREF_WIND = "PREF_WIND";
    public static final String PREF_HUMID = "PREF_HUMID";
    public static final String PREF_PRESS = "PREF_PRESS";
    public static final String PREF_TYPE = "PREF_TYPE";
    public static final String PREF_ICON = "PREF_ICON";
    public static final String PREF_FEEL = "PREF_FEEL";
    public static final String PREF_BACK = "PREF_BACK";
    public static final String tag_temp="temperature";
    public static final String tag_wind="wind_force";
    public static final String tag_pressure="pressure";
    public static final String tag_cityName="cityName";
    public static final String tag_theme="theme";
    public static final String tag_time="time";
    public static final String ACCESS_MESSAGE = "Location";
    public static final int REQUEST_CODE = 1;
    public static final int RESULT_OK = -1;
    public static final int searchActivityRequestCode = 1234;
    public final static int THEME_LIGHT = 0;
    public final static int THEME_DARK = 1;
    public final static int POSTFIX_CELS = 1;
    public final static int POSTFIX_KELVIN = 0;
    public final static int WINDFORCE_MS = 1;
    public final static int WINDFORCE_KMH = 0;
    public final static int PRESSURE_MM = 1;
    public final static int PRESSURE_GPA = 0;
    public static final SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMMM", Locale.forLanguageTag(Locale.getDefault().getLanguage()));
    public static final SimpleDateFormat weekDayFormat=new SimpleDateFormat("EEEE", Locale.forLanguageTag(Locale.getDefault().getLanguage()));
    @SuppressLint("ConstantLocale")
    public static final String urlWeatherStatic = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static final SimpleDateFormat hoursFormat=new SimpleDateFormat("HH", Locale.forLanguageTag(Locale.getDefault().getLanguage()));
    public static final String urlWeather7Days="https://api.openweathermap.org/data/2.5/forecast?q=";
    public static final String lang="&lang=";
    public static final String weatherKey = "&appid=";
    public static final String TAG = "WEATHER";
    public static final String url = "https://yandex.ru/search/?text=";
    public static final String final_url=Constants.lang+Locale.forLanguageTag(Locale.getDefault().getLanguage())+Constants.weatherKey+BuildConfig.WEATHER_API_KEY;
}
