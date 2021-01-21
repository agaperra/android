package com.geekbrains.android_lessons.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android_lessons.Constants;
import com.geekbrains.android_lessons.R;
import com.geekbrains.android_lessons.SharedPreferencesManager;
import com.geekbrains.android_lessons.WeekDay;
import com.geekbrains.android_lessons.adapters.RecyclerHorizontalHoursAdapter;
import com.geekbrains.android_lessons.adapters.RecyclerWeekDayAdapter;
import com.geekbrains.android_lessons.interfaces.DateClick;
import com.geekbrains.android_lessons.interfaces.HoursClick;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment implements HoursClick, DateClick {

    private TextView degreesCountView;
    private TextView windForceParameterView;
    private TextView humidityParameterView;
    private TextView pressureParameterView;

    private TextView cityNameView;

    @SuppressLint("StaticFieldLeak")
    public static SharedPreferencesManager sPrefs;

    private final String url = "https://yandex.ru/search/?text=";
    private String message = "";

    private RecyclerView recyclerViewHours;
    private RecyclerView recyclerViewDays;



    private void findViews(View v) {

        degreesCountView = v.findViewById(R.id.degreesCountView);
        windForceParameterView = v.findViewById(R.id.windForceParameterView);
        humidityParameterView = v.findViewById(R.id.humidityParameterView);
        pressureParameterView = v.findViewById(R.id.pressureParameterView);
        cityNameView = v.findViewById(R.id.cityNameView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle("");
        setHasOptionsMenu(true);
        setRetainInstance(true);

        initViews(view);
        setupRecyclerView();

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_menu);
        sPrefs = new SharedPreferencesManager(requireContext());
        findViews(view);


        int t = sPrefs.retrieveInt("theme", Constants.THEME_LIGHT);
        switch (t) {
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }


        //для проверки работоспособности при перевороте экрана
        updateValue(getValue(Constants.PREF_DEGREES),
                getValue(Constants.PREF_WIND),
                getValue(Constants.PREF_HUMID),
                getValue(Constants.PREF_PRESS));

//при нажатии на текстовое поле "влажность" - увеличение всех вью на 1
        view.findViewById(R.id.windForceView).setOnClickListener(v -> {
            updateValue(String.valueOf(Integer.parseInt(getValue(Constants.PREF_DEGREES)) + 1),
                    String.valueOf(Integer.parseInt(getValue(Constants.PREF_WIND)) + 1),
                    String.valueOf(Integer.parseInt(getValue(Constants.PREF_HUMID)) + 1),
                    String.valueOf(Integer.parseInt(getValue(Constants.PREF_PRESS)) + 1));
        });


        Intent intent = requireActivity().getIntent();
        if (intent.hasExtra("cityName")) {
            message = intent.getStringExtra("cityName");
            if (!message.equals("")) {
                cityNameView.setText(message);
            }
        }

//по нажатию на название города - открытие поискового запроса в яндексе
        view.findViewById(R.id.search_in_internet).setOnClickListener(v -> {
            if (!message.trim().equals("")) {
                Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
                openURL.setData(Uri.parse(url + message));
                startActivity(openURL);
            }
        });
    }


    private void initViews(View v) {

        recyclerViewHours = v.findViewById(R.id.hoursRecycler);
        recyclerViewDays = v.findViewById(R.id.recyclerWeekday);
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
        );
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
        );

        List<String> hours = Arrays.asList(getResources().getStringArray(R.array.hours_in_a_day));
        RecyclerHorizontalHoursAdapter adapter = new RecyclerHorizontalHoursAdapter(hours, this);

        RecyclerWeekDayAdapter adapterWeek = new RecyclerWeekDayAdapter();
        adapterWeek.addItems(WeekDay.getDays(7, requireActivity()));

        recyclerViewHours.setLayoutManager(layoutManager1);
        recyclerViewHours.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),
                LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(
                ContextCompat.getDrawable(requireContext(), R.drawable.decorator_line)));
        recyclerViewDays.addItemDecoration(itemDecoration);
        recyclerViewDays.setLayoutManager(layoutManager2);
        recyclerViewDays.setAdapter(adapterWeek);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == Constants.REQUEST_CODE) {
            if (resultCode == Constants.RESULT_OK) {
                String location = Objects.requireNonNull(data).getStringExtra(Constants.ACCESS_MESSAGE);
                cityNameView.setText(location);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @SuppressLint("SetTextI18n")
    public void checkSharedPreferences(String keyContainer, String tag, String parameter, TextView textView, int tag1, int tag2, int defaultConst){
        sPrefs.getEditor().putString(keyContainer, parameter).apply();
        switch (sPrefs.retrieveInt(tag, defaultConst)) {
            case 0:
                textView.setText(parameter+" "+getString(tag1));
                break;
            case 1:
                textView.setText(parameter+" "+getString(tag2));
                break;
        }
    }


    @SuppressLint("SetTextI18n")
    private void updateValue(String degrees, String wind, String humidity, String pressure) {

        checkSharedPreferences(Constants.PREF_DEGREES,
                "temperature",
                degrees,
                degreesCountView,
                R.string.cels,
                R.string.faringate,
                Constants.POSTFIX_CELS);

        checkSharedPreferences(Constants.PREF_WIND,
                "wind_force",
                wind,
                windForceParameterView,
                R.string.km_h,
                R.string.m_s,
                Constants.WINDFORCE_MS);

        sPrefs.getEditor().putString(Constants.PREF_HUMID, humidity).apply();
        humidityParameterView.setText(humidity+" %");


        checkSharedPreferences(Constants.PREF_PRESS,
                "pressure",
                pressure,
                pressureParameterView,
                R.string.gPa,
                R.string.mm_of_m_c,
                Constants.PRESSURE_GPA);


    }

    private String getValue(String key) {
        return sPrefs.retrieveString(key, "0");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        MenuItem menuItem = menu.add(R.string.option_fragment_name);
        menuItem.setIcon(R.drawable.ic_settings);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Navigation.findNavController(requireView()).navigate(R.id.navigateToSettingsFragment);
                break;
            case android.R.id.home:
                Navigation.findNavController(requireView()).navigate(R.id.navigateToSearchFragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(String itemText) {

    }

}
