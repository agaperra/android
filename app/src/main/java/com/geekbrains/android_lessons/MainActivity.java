package com.geekbrains.android_lessons;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private static final String PREF_DEGREES = "PREF_DEGREES";
    private TextView degreesCountView;

    private static final String PREF_WIND = "PREF_WIND";
    private TextView windForceParameterView;

    private static final String PREF_HUMID = "PREF_HUMID";
    private TextView humidityParameterView;

    private static final String PREF_PRESS = "PREF_PRESS";
    private TextView pressureParameterView;

    private TextView cityNameView;

    private SharedPreferences sPrefs;

    private final String url = "https://yandex.ru/search/?text=";
    private String message = "";

    static String textKey = "textKey";
    private static final int REQUEST_CODE = 1;
    public static final String ACCESS_MESSAGE = "Location";


    private void findViews() {

        degreesCountView = findViewById(R.id.degreesCountView);
        windForceParameterView = findViewById(R.id.windForceParameterView);
        humidityParameterView = findViewById(R.id.humidityParameterView);
        pressureParameterView = findViewById(R.id.pressureParameterView);
        cityNameView = findViewById(R.id.cityNameView);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        findViews();
        //для проверки работоспособности при перевороте экрана
        updateValue(getValue(PREF_DEGREES),
                getValue(PREF_WIND),
                getValue(PREF_HUMID),
                getValue(PREF_PRESS));

//при нажатии на текстовое поле "влажность" - увеличение всех вью на 1
        findViewById(R.id.humidityView).setOnClickListener(v -> {
            updateValue(String.valueOf(Integer.parseInt(getValue(PREF_DEGREES)) + 1),
                    String.valueOf(Integer.parseInt(getValue(PREF_WIND)) + 1),
                    String.valueOf(Integer.parseInt(getValue(PREF_HUMID)) + 1),
                    String.valueOf(Integer.parseInt(getValue(PREF_PRESS)) + 1));
        });


        Intent intent = getIntent();
        if (intent.hasExtra("cityName")) {
            message = intent.getStringExtra("cityName");
            cityNameView.setText(message);
        }

//по нажатию на название города - открытие поискового запроса в яндексе
        findViewById(R.id.cityNameView).setOnClickListener(v -> {
            if (!message.trim().equals("")) {
                Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
                openURL.setData(Uri.parse(url + message));
                startActivity(openURL);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String location = Objects.requireNonNull(data).getStringExtra(ACCESS_MESSAGE);
                cityNameView.setText(location);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void updateValue(String degrees, String wind, String humidity, String pressure) {
        sPrefs.edit().putString(PREF_DEGREES, degrees).apply();
        degreesCountView.setText(degrees);

        sPrefs.edit().putString(PREF_WIND, wind).apply();
        windForceParameterView.setText(wind);

        sPrefs.edit().putString(PREF_HUMID, humidity).apply();
        humidityParameterView.setText(humidity);

        sPrefs.edit().putString(PREF_PRESS, pressure).apply();
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


