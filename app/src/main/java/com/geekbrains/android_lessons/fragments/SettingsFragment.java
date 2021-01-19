package com.geekbrains.android_lessons.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.geekbrains.android_lessons.R;

import java.util.Objects;


public class SettingsFragment extends Fragment {

    private RadioGroup themeGroup;
    private RadioGroup temperatureGroup;
    private RadioGroup windGroup;
    private RadioGroup pressureGroup;
    private RadioButton theme_Dark, theme_Light, temp_Celsi, temp_Faring, wind_MS, wind_KMH, press_MM, press_GPA;
    private SharedPreferences sPrefs = MainFragment.sPrefs;
    public static final String APP_PREFERENCES = "SETTINGS";
    final String KEY_RADIOBUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle("");
        setHasOptionsMenu(true);
        setRetainInstance(true);
//        sPrefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        findViews(view);
//        LoadPreferences(themeGroup);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.back_line);


        themeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if(theme_Dark.isChecked()) {
                    switch (currentNightMode) {
                        case Configuration.UI_MODE_NIGHT_NO:
                            //todo включение ночной темы
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            Toast.makeText(requireView().getContext(), "Ночная тема включена", Toast.LENGTH_SHORT).show();
                        case Configuration.UI_MODE_NIGHT_YES:
                            // todo ничего не делать
                    }
//                    int checkedIndex = radioGroup.indexOfChild(theme_Dark);
//                    SavePreferences(KEY_RADIOBUTTON_INDEX, checkedIndex);
                }
                if(theme_Light.isChecked()) {
                    switch (currentNightMode) {
                        case Configuration.UI_MODE_NIGHT_NO:
                            //todo ничего не делать
                        case Configuration.UI_MODE_NIGHT_YES:
                            // todo включение светлой темы
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            Toast.makeText(requireView().getContext(), "Дневная тема включена", Toast.LENGTH_SHORT).show();
                    }
//                    int checkedIndex = radioGroup.indexOfChild(theme_Light);
//                    SavePreferences(KEY_RADIOBUTTON_INDEX, checkedIndex);
                }
            }

        });
    }

//    private void SavePreferences(String key, int value) {
//        sPrefs.edit().putInt(key, value).apply();
//    }
//
//    private void LoadPreferences(RadioGroup radioGroup) {
//        int savedRadioIndex = sPrefs.getInt(
//                KEY_RADIOBUTTON_INDEX, 0);
//        RadioButton savedCheckedRadioButton = (RadioButton) radioGroup.getChildAt(savedRadioIndex);
//        savedCheckedRadioButton.setChecked(true);
//    }

    public void checkTheme(RadioButton radioButton, int currentNightMode) {
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_NO:
                    //todo включение ночной темы
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Toast.makeText(requireView().getContext(), "Ночная тема включена", Toast.LENGTH_SHORT).show();
                case Configuration.UI_MODE_NIGHT_YES:
                    // todo включение светлой темы
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    Toast.makeText(requireView().getContext(), "Дневная тема включена", Toast.LENGTH_SHORT).show();
            }
        }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.back, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    public void findViews(View v) {

        themeGroup = v.findViewById(R.id.radioGroupTheme);
        temperatureGroup = v.findViewById(R.id.radioGroupTemp);
        windGroup = v.findViewById(R.id.radioGroupWind);
        pressureGroup = v.findViewById(R.id.radioGroupPressure);

        theme_Dark = v.findViewById(R.id.radiobuttonTheme1);
        theme_Light = v.findViewById(R.id.radiobuttonTheme2);

        temp_Celsi = v.findViewById(R.id.radiobuttonTemp1);
        temp_Faring = v.findViewById(R.id.radiobuttonTemp2);

        wind_MS = v.findViewById(R.id.radiobuttonWind1);
        wind_KMH = v.findViewById(R.id.radiobuttonWind2);

        press_GPA = v.findViewById(R.id.radiobuttonPress1);
        press_MM = v.findViewById(R.id.radiobuttonPress2);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(requireView()).popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
