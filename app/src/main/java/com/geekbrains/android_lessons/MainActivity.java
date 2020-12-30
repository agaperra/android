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
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String PREF_DEGREES = "PREF_DEGREES";
    private TextView degreesCountView;
    private String degrees;

    private static final String PREF_WIND = "PREF_WIND";
    private TextView windForceParameterView;
    private String wind;

    private static final String PREF_HUMID = "PREF_HUMID";
    private TextView humidityParameterView;
    private String humidity;

    private static final String PREF_PRESS = "PREF_PRESS";
    private TextView pressureParameterView;
    private String pressure;

    private SharedPreferences sPrefs;


    private void findViews() {

        degreesCountView = findViewById(R.id.degreesCountView);
        windForceParameterView = findViewById(R.id.windForceParameterView);
        humidityParameterView = findViewById(R.id.humidityParameterView);
        pressureParameterView = findViewById(R.id.pressureParameterView);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("INFO: ", "onCreate");
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        sPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        findViews();
        //для проверки работоспособности при перевороте экрана
       updateValue(getValue(PREF_DEGREES),
               getValue(PREF_WIND),
               getValue(PREF_HUMID),
               getValue(PREF_PRESS));


        findViewById(R.id.humidityView).setOnClickListener(v -> {
            updateValue(String.valueOf(Integer.parseInt(getValue(PREF_DEGREES)) + 1),
                    String.valueOf(Integer.parseInt(getValue(PREF_WIND)) + 1),
                    String.valueOf(Integer.parseInt(getValue(PREF_HUMID)) + 1),
                    String.valueOf(Integer.parseInt(getValue(PREF_PRESS)) + 1));
        });


    }


    private void updateValue(String degrees,String wind, String humidity, String pressure) {
        sPrefs.edit().putString( PREF_DEGREES, degrees).apply();
        degreesCountView.setText(degrees);

        sPrefs.edit().putString( PREF_WIND, wind).apply();
        windForceParameterView.setText(wind);

        sPrefs.edit().putString( PREF_HUMID, humidity).apply();
        humidityParameterView.setText(humidity);

        sPrefs.edit().putString( PREF_PRESS, pressure).apply();
        pressureParameterView.setText(pressure);


    }

    private String getValue(String key) {
        return sPrefs.getString(key, "0");
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


