package com.geekbrains.android_lessons;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {
    public static final String PREF_DEGREES = "PREF_DEGREES";
    public static final String PREF_WIND = "PREF_WIND";
    public static final String PREF_HUMID = "PREF_HUMID";
    public static final String PREF_PRESS = "PREF_PRESS";
    public static final String tag_temp="temperature";
    public static final String tag_wind="wind_force";
    public static final String tag_pressure="pressure";
    public static final String tag_cityName="cityName";
    public static final String tag_theme="theme";
    public static final String ACCESS_MESSAGE = "Location";
    public static final int REQUEST_CODE = 1;
    public static final int RESULT_OK = -1;
    public static final int searchActivityRequestCode = 1234;
    public final static int THEME_LIGHT = 0;
    public final static int THEME_DARK = 1;
    public final static int POSTFIX_CELS = 0;
    public final static int POSTFIX_FARING = 1;
    public final static int WINDFORCE_MS = 0;
    public final static int WINDFORCE_KMH = 1;
    public final static int PRESSURE_MM = 0;
    public final static int PRESSURE_GPA = 1;
    public static final SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMMM", Locale.forLanguageTag(Locale.getDefault().getLanguage()));
    public static final SimpleDateFormat weekDayFormat=new SimpleDateFormat("EEEE", Locale.forLanguageTag(Locale.getDefault().getLanguage()));
    public static final SimpleDateFormat hoursFormat=new SimpleDateFormat("HH:00", Locale.forLanguageTag(Locale.getDefault().getLanguage()));
}
