package com.geekbrains.android_lessons;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.geekbrains.android_lessons.model.WeatherRequest;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private  DrawerLayout drawer;
    private NavigationView navigationView;
    private  NavController navController;
    public ConstraintLayout constraintLayout;
    private View headerView;
    public static SharedPreferencesManager preferencesManager;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setBackgroundColor(getResources().getColor(R.color.design_default_color_background));
        setSupportActionBar(toolbar);
        //constraintLayout=findViewById(R.id.navHeader);
        preferencesManager= new SharedPreferencesManager(this);

        drawer = findViewById(R.id.drawerLayoutView);
        navigationView = findViewById(R.id.navigationView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home_nav, R.id.settings_nav, R.id.developers_nav)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//preferencesManager.retrieveString(Constants.tag_cityName,getString(R.string.moscow))
        getWeather.getWeather(preferencesManager.retrieveString(Constants.tag_cityName,getString(R.string.moscow)),this);
        //getWeather.getWeather(String.valueOf((TextView)findViewById(R.id.cityNameView)),this);
        //MainFragment.sPrefs.storeString(Constants.tag_cityName, String.valueOf(((TextView) headerView.findViewById(R.id.navHeaderLocationView)).getText()));
    }




    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (navController.getCurrentDestination().getId() == R.id.home_nav) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.exit))
                    .setPositiveButton(getString(R.string.yes), (dialog, which) -> this.finishAffinity())
                    .setNegativeButton(getString(R.string.chancel), (dialog, which) -> dialog.cancel()).show();
        } else {
            Navigation.findNavController(this, R.id.nav_host_fragment).popBackStack();
        }
    }



    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    public void displayWeather(WeatherRequest list) {
       headerView = navigationView.getHeaderView(0);
        ((TextView) headerView.findViewById(R.id.navHeaderLocationView)).setText(list.getName());
       constraintLayout= (ConstraintLayout) headerView.findViewById(R.id.navHeader);
        String weatherStatusValue = String.valueOf(list.getWeather()[0].getDescription());
        weatherStatusValue = weatherStatusValue.substring(0, 1).toUpperCase() + weatherStatusValue.substring(1);
        ((TextView) headerView.findViewById(R.id.navWeatherStatus)).setText(weatherStatusValue);
        ((TextView) headerView.findViewById(R.id.texthumid)).setText(String.format("%d", list.getMain().getHumidity())+"%");


        double k=list.getMain().getTemp();
        if (k<273.15&&k>272.15){
            k=273.15;
        }

        switch (preferencesManager.retrieveInt(Constants.tag_temp, Constants.POSTFIX_KELVIN)) {
            case 0:
                ((TextView) headerView.findViewById(R.id.textdDegrees)).setText(k + "K\u00B0");
                break;
            case 1:
                String parameter=String.valueOf(k);
                parameter = parameter.replaceAll(",", ".");
                double value = Double.parseDouble(parameter) - 273.15;
                ( (TextView) headerView.findViewById(R.id.textdDegrees)).setText(String.format("%.0f", value) + "С\u00B0");
                break;
        }
        switch (preferencesManager.retrieveInt(Constants.tag_wind, Constants.WINDFORCE_KMH)) {
            case 0:
                ((TextView) headerView.findViewById(R.id.textWind)).setText(String.format("%.0f", list.getWind().getSpeed())+getString(R.string.km_h));
                break;
            case 1:
                String parameter = String.valueOf(list.getWind().getSpeed());
                parameter = parameter.replaceAll(",", ".");
                double value = Double.parseDouble(parameter) * 0.27;
                ((TextView) headerView.findViewById(R.id.textWind)).setText(String.format("%.0f", value)+getString(R.string.m_s));
                break;

        }
        switch (preferencesManager.retrieveInt(Constants.tag_pressure, Constants.PRESSURE_GPA)) {
            case 0:
                ((TextView) headerView.findViewById(R.id.textPress)).setText(String.format("%d", list.getMain().getPressure())+getString(R.string.gPa));
                break;
            case 1:
                String parameter = String.valueOf(list.getMain().getPressure());
                parameter = parameter.replaceAll(",", ".");
                double value = Double.parseDouble(parameter) * 0.75;
                ((TextView) headerView.findViewById(R.id.textPress)).setText(String.format("%.0f", value)+"\n"+getString(R.string.mm_of_m_c));
                break;

        }
       String icon = list.getWeather()[0].getIcon();
        setBackgroundMode(constraintLayout,icon);
        setIcons(list,headerView.findViewById(R.id.navHeaderIconView));


    }


    public static void setIcons(WeatherRequest list, View v) {
        int t = list.getWeather()[0].getId();
        boolean day;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, 0);
        int time = Integer.parseInt(new SimpleDateFormat("HH", Locale.forLanguageTag(Locale.getDefault().getLanguage())).format(now.getTime()));
        day= time > 6 && time < 18;
        if (t >= 200 && t <= 232) {
            v.setBackgroundResource(R.drawable.thunderstorm);
        }
        if (t >= 300 && t <= 321) {
            v.setBackgroundResource(R.drawable.rain);
        }
        if (t >= 500 && t <= 504) {
            if (day)
                v.setBackgroundResource(R.drawable.clouds_sun);
            else v.setBackgroundResource(R.drawable.moon_cloud);
        }
        if (t == 511) {
            v.setBackgroundResource(R.drawable.snow);
        }
        if (t >= 520 && t <= 531) {
            v.setBackgroundResource(R.drawable.rain);
        }
        if (t >= 600 && t <= 622) {
            v.setBackgroundResource(R.drawable.snow);
        }
        if (t >= 701 && t <= 781) {
            if (day)
                v.setBackgroundResource(R.drawable.mist);
            else v.setBackgroundResource(R.drawable.fog);
        }
        if (t == 800) {
            if (day)
                v.setBackgroundResource(R.drawable.sun);
            else v.setBackgroundResource(R.drawable.moon);
        }
        switch (t) {
            case 801:
                if (day)
                    v.setBackgroundResource(R.drawable.clouds_15);
                else v.setBackgroundResource(R.drawable.moon_cloud_15);
                break;
            case 802:
                if (day)
                    v.setBackgroundResource(R.drawable.clouds_30);
                else v.setBackgroundResource(R.drawable.moon_cloud_30);
                break;
            case 803:
            case 804:
                v.setBackgroundResource(R.drawable.clouds_60);
                break;
        }
    }

    public void setBackgroundMode(ConstraintLayout constraintLayout,String icon) {
        String tmp = "01";
        String mode = "d";
        if (icon.contains("d")) {
            mode = "d";
            tmp = icon.replaceAll("d", "");
        } else if (icon.contains("n")) {
            mode = "n";
            tmp = icon.replaceAll("n", "");
        }
        switch (tmp) {
            case "01":
                check(constraintLayout,mode, R.drawable.icon_01d, R.drawable.icon_01n);
                break;
            case "02":
                check(constraintLayout,mode, R.drawable.icon_02d, R.drawable.icon_02n);
                break;
            case "03":
                check(constraintLayout,mode, R.drawable.icon_03d, R.drawable.icon_03d);
                break;
            case "04":
                check(constraintLayout,mode, R.drawable.icon_04d, R.drawable.icon_04d);
                break;
            case "09":
                check(constraintLayout,mode, R.drawable.icon_09d, R.drawable.icon_09n);
                break;
            case "10":
                check(constraintLayout,mode, R.drawable.icon_10d, R.drawable.icon_10n);
                break;
            case "11":
                check(constraintLayout,mode, R.drawable.icon_11d, R.drawable.icon_11n);
                break;
            case "13":
                check(constraintLayout,mode, R.drawable.icon_13d, R.drawable.icon_13n);
                break;
            case "50":
                check(constraintLayout,mode, R.drawable.icon_50d, R.drawable.icon_50n);
                break;
        }


    }

    public void check(ConstraintLayout constraintLayout,String mode, int... tags) {
        if (mode.equals("d")) {
           constraintLayout.setBackgroundResource(tags[0]);
        } else if (mode.equals("n")) {
            constraintLayout.setBackgroundResource(tags[1]);
        }
    }

}