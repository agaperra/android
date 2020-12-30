package com.geekbrains.android_lessons;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

//    private TextView cityView;
//    private static final String PREF_CITY = "PREF_CITY";
//    private TextView areaView;
//    private static final String PREF_AREA = "PREF_AREA";
//    private TextView typeWeatherView;
//    private static final String PREF_TYPE = "PREF_TYPE";

    private static final String PREF_DEGREES = "PREF_DEGREES";
    private TextView degreesView;
    private static final String PREF_WIND = "PREF_WIND";
    private TextView windForceView;
    private static final String PREF_HUMID = "PREF_HUMID";
    private TextView humidityView;
    private static final String PREF_PRESS = "PREF_PRESS";
    private TextView pressureView;
    private final String degreesPostfix = "\u00B0";
    private final String percents = "%";

    private SharedPreferences sPrefs;


    private void findViews() {
//        cityView = findViewById(R.id.cityNameView);
//        areaView = findViewById(R.id.areaNameView);
        degreesView = findViewById(R.id.degreesCountView);
//        typeWeatherView = findViewById(R.id.weatherTypeView);
        windForceView = findViewById(R.id.windForceParameterView);
        humidityView = findViewById(R.id.humidityParameterView);
        pressureView = findViewById(R.id.pressureParameterView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("INFO: ", "onCreate");
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        findViews();
        generateData((View) findViewById(R.id.headLayoutView));
        //с SharedPreferences не получилось пока
        sPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        updateValues(getValues(PREF_DEGREES, degreesView),
                getValues(PREF_WIND, windForceView),
                getValues(PREF_HUMID, humidityView),
                getValues(PREF_PRESS, pressureView));
    }

    @SuppressLint("CommitPrefEdits")
    private void updateValues( String degrees, String windForce, String humidity, String pressure) {

        sPrefs.edit().putString(PREF_DEGREES, degrees).apply();
        degreesView.setText(String.valueOf(degrees));

        sPrefs.edit().putString(PREF_WIND, windForce).apply();
        windForceView.setText(String.valueOf(windForce));

        sPrefs.edit().putString(PREF_HUMID, humidity).apply();
        humidityView.setText(String.valueOf(humidity));

        sPrefs.edit().putString(PREF_PRESS, pressure).apply();
        pressureView.setText(String.valueOf(pressure));
    }

    private String getValues(String key, TextView v) {
        return sPrefs.getString(key, v.getText().toString());
    }

    private void setData(String degrees, String windForce, String humidity, String pressure) {
        degreesView.setText(degrees);
        windForceView.setText(windForce);
        humidityView.setText(humidity);
        pressureView.setText(pressure);
    }

    public void generateData(View view) {

        String degrees = (int) (Math.random() * 10) + degreesPostfix;
        String wind = String.valueOf((int) (Math.random() * 10));
        String humidity = (int) (Math.random() * 100) + percents;
        String pressure = String.valueOf((int) (Math.random() * 800));

        setData(degrees, wind, humidity, pressure);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        setTitle("");
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("INFO: ", "onStart");
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("INFO: ", "onResume");
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("INFO: ", "onPause");
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("INFO: ", "onStop");
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("INFO: ", "onRestart");
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("INFO: ", "onDestroy");
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_menu:

                Intent intentMenu = new Intent(this, SearchActivity.class);
                startActivity(intentMenu);
                return true;
            case R.id.ic_settings:

                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


