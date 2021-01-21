package com.geekbrains.android_lessons.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.geekbrains.android_lessons.Constants;
import com.geekbrains.android_lessons.R;
import com.geekbrains.android_lessons.SharedPreferencesManager;

import java.util.Objects;


public class SettingsFragment extends Fragment {

    private RadioGroup themeGroup;
    private RadioGroup temperatureGroup;
    private RadioGroup windGroup;
    private RadioGroup pressureGroup;
    private RadioButton theme_Dark, theme_Light, temp_Celsi, temp_Faring, wind_MS, wind_KMH, press_MM, press_GPA;

    public SharedPreferencesManager sPrefs = MainFragment.sPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle("");
        setHasOptionsMenu(true);
        setRetainInstance(true);

        findViews(view);
        setUpRadioButtons();
        initListeners();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.back_line);


    }

    public void setUpRadioButtons() {
        sPrefs=new SharedPreferencesManager(requireContext());
        int t = sPrefs.retrieveInt("theme", Constants.THEME_LIGHT);
        switch (t){
            case 0:
                theme_Light.setChecked(true);
                break;
            case 1:
                theme_Dark.setChecked(true);
                break;
        }
        t = sPrefs.retrieveInt("temperature", Constants.POSTFIX_CELS);
        switch (t){
            case 0:
                temp_Celsi.setChecked(true);
                break;
            case 1:
                temp_Faring.setChecked(true);
                break;
        }

        t = sPrefs.retrieveInt("wind_force", Constants.WINDFORCE_MS);
        switch (t){
            case 0:
                wind_KMH.setChecked(true);
                break;
            case 1:
                wind_MS.setChecked(true);
                break;
        }

        t = sPrefs.retrieveInt("pressure", Constants.PRESSURE_MM);
        switch (t){
            case 0:
                press_GPA.setChecked(true);
                break;
            case 1:
                press_MM.setChecked(true);
                break;
        }
    }

    public void initListeners() {

        //theme group listener
        themeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (theme_Dark.isChecked()) {
                    switch (currentNightMode) {
                        case Configuration.UI_MODE_NIGHT_NO:
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            Toast.makeText(requireView().getContext(), "Ночная тема включена", Toast.LENGTH_SHORT).show();
                            new SharedPreferencesManager(requireContext()).storeInt("theme", Constants.THEME_DARK);
                            break;
                        case Configuration.UI_MODE_NIGHT_YES:
                            break;
                    }
                }
                if (theme_Light.isChecked()) {
                    switch (currentNightMode) {
                        case Configuration.UI_MODE_NIGHT_NO:
                            break;
                        case Configuration.UI_MODE_NIGHT_YES:
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            Toast.makeText(requireView().getContext(), "Дневная тема включена", Toast.LENGTH_SHORT).show();
                            new SharedPreferencesManager(requireContext()).storeInt("theme", Constants.THEME_LIGHT);
                            break;
                    }
                }
            }
        });


        //degrees postfix listener
        temperatureGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (temp_Celsi.isChecked()) {
                    new SharedPreferencesManager(requireContext()).storeInt("temperature", Constants.POSTFIX_CELS);
                }
                if (temp_Faring.isChecked()) {
                    new SharedPreferencesManager(requireContext()).storeInt("temperature", Constants.POSTFIX_FARING);
                }
            }
        });

        //wind force listener
        windGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (wind_KMH.isChecked()) {
                    new SharedPreferencesManager(requireContext()).storeInt("wind_force", Constants.WINDFORCE_KMH);
                }
                if (wind_MS.isChecked()) {
                    new SharedPreferencesManager(requireContext()).storeInt("wind_force", Constants.WINDFORCE_MS);
                }
            }
        });


        //pressure listener
        pressureGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (press_GPA.isChecked()) {
                    new SharedPreferencesManager(requireContext()).storeInt("pressure", Constants.PRESSURE_GPA);
                }
                if (press_MM.isChecked()) {
                    new SharedPreferencesManager(requireContext()).storeInt("pressure", Constants.PRESSURE_MM);
                }
            }
        });
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
