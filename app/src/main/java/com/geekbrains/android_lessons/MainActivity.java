package com.geekbrains.android_lessons;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Locale.setDefault(new Locale("ru", "RF"));

    }
}


