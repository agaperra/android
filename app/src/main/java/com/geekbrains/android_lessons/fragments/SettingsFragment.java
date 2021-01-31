package com.geekbrains.android_lessons.fragments;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.geekbrains.android_lessons.R;
import com.geekbrains.android_lessons.Constants;
import com.geekbrains.android_lessons.SharedPreferencesManager;

import java.util.Objects;


public class SettingsFragment extends Fragment {

    private RadioGroup themeGroup;
    private RadioGroup temperatureGroup;
    private RadioGroup windGroup;
    private RadioGroup pressureGroup;
    private RadioButton theme_Dark, theme_Light, temp_Celsi, temp_kelvin, wind_MS, wind_KMH, press_MM, press_GPA;

    public SharedPreferencesManager sPrefs = MainFragment.sPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle("");
        ((AppCompatActivity) requireActivity()).setSupportActionBar(view.findViewById(R.id.toolbar));
        setHasOptionsMenu(true);
        setRetainInstance(true);

        findViews(view);
        setUpRadioButton();
        initListeners();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.back_line);

        //theme group listener
        themeGroup.setOnCheckedChangeListener((RadioGroup radioGroup, int checkedId) -> {
            int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (theme_Dark.isChecked()) {
                if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sPrefs.storeInt(Constants.tag_theme, Constants.THEME_DARK);
                }
            }
            if (theme_Light.isChecked()) {
                if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sPrefs.storeInt(Constants.tag_theme, Constants.THEME_LIGHT);
                }
            }
        });


    }

    public void radioButtonsCheckEnter(String tag, int defValue, RadioButton... radioButtons) {
        int temp = sPrefs.retrieveInt(tag, defValue);
        if (temp == 1) {
            radioButtons[1].setChecked(true);
        } else {
            radioButtons[0].setChecked(true);
        }
    }

    public void setUpRadioButton() {

        radioButtonsCheckEnter(Constants.tag_theme, Constants.THEME_LIGHT, theme_Light, theme_Dark);
        radioButtonsCheckEnter(Constants.tag_temp, Constants.POSTFIX_KELVIN, temp_kelvin, temp_Celsi);
        radioButtonsCheckEnter(Constants.tag_wind, Constants.WINDFORCE_KMH, wind_KMH, wind_MS);
        radioButtonsCheckEnter(Constants.tag_pressure, Constants.PRESSURE_GPA, press_GPA, press_MM);
    }

    public void initListeners() {

        //degrees postfix listener
        temperatureGroup.setOnCheckedChangeListener((RadioGroup radioGroup, int checkedId) -> checkingRadiobutton(temp_kelvin, temp_Celsi, Constants.tag_temp, Constants.POSTFIX_KELVIN, Constants.POSTFIX_CELS));

        //wind force listener
        windGroup.setOnCheckedChangeListener((RadioGroup radioGroup, int checkedId) -> checkingRadiobutton(wind_KMH, wind_MS, Constants.tag_wind, Constants.WINDFORCE_KMH, Constants.WINDFORCE_MS));

        //pressure listener
        pressureGroup.setOnCheckedChangeListener((RadioGroup radioGroup, int checkedId) -> checkingRadiobutton(press_GPA, press_MM, Constants.tag_pressure, Constants.PRESSURE_GPA, Constants.PRESSURE_MM));
    }

    public void checkingRadiobutton(RadioButton radioButton1, RadioButton radioButton2, String tag, int... tags) {
        if (radioButton1.isChecked()) {
            sPrefs.storeInt(tag, tags[0]);
        }
        if (radioButton2.isChecked()) {
            sPrefs.storeInt(tag, tags[1]);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.back, menu);

        MenuItem authorsItem = menu.add(R.string.developers);
        authorsItem.setOnMenuItemClickListener(item -> {
            Navigation.findNavController(requireView()).navigate(R.id.navigateToDevelopersFragment);
            return true;
        });
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
        temp_kelvin = v.findViewById(R.id.radiobuttonTemp2);

        wind_MS = v.findViewById(R.id.radiobuttonWind1);
        wind_KMH = v.findViewById(R.id.radiobuttonWind2);

        press_GPA = v.findViewById(R.id.radiobuttonPress1);
        press_MM = v.findViewById(R.id.radiobuttonPress2);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home || item.getItemId() == R.id.home) {
            Navigation.findNavController(requireView()).popBackStack();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
