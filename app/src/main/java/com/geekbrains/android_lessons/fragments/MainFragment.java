package com.geekbrains.android_lessons.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android_lessons.R;
import com.geekbrains.android_lessons.WeekDay;
import com.geekbrains.android_lessons.adapters.RecyclerHorizontalHoursAdapter;
import com.geekbrains.android_lessons.adapters.RecyclerWeekDayAdapter;
import com.geekbrains.android_lessons.interfaces.DateClick;
import com.geekbrains.android_lessons.interfaces.HoursClick;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment implements HoursClick, DateClick {

    private static final String PREF_DEGREES = "PREF_DEGREES";
    private TextView degreesCountView;

    private static final String PREF_WIND = "PREF_WIND";
    private TextView windForceParameterView;
    private TextView windForceView;

    private static final String PREF_HUMID = "PREF_HUMID";
    private TextView humidityParameterView;

    private static final String PREF_PRESS = "PREF_PRESS";
    private TextView pressureParameterView;

    private TextView cityNameView;

    public static SharedPreferences sPrefs;

    private final String url = "https://yandex.ru/search/?text=";
    private String message = "";

    private static final int REQUEST_CODE = 1;
    private static final int RESULT_OK = -1;
    public static final String ACCESS_MESSAGE = "Location";

    private RecyclerView recyclerViewHours;
    private RecyclerView recyclerViewDays;
    private List<String> hours;
    private List<String> weekday;
    private RecyclerWeekDayAdapter adapterWeek;
    private RecyclerHorizontalHoursAdapter adapter;


    private void findViews(View v) {

        degreesCountView = v.findViewById(R.id.degreesCountView);
        windForceParameterView = v.findViewById(R.id.windForceParameterView);
        humidityParameterView = v.findViewById(R.id.humidityParameterView);
        pressureParameterView = v.findViewById(R.id.pressureParameterView);
        ImageView searching = v.findViewById(R.id.search_in_internet);
        cityNameView = v.findViewById(R.id.cityNameView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle("");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        initViews(view);
        setupRecyclerView();

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_menu);
        sPrefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        findViews(view);

        //для проверки работоспособности при перевороте экрана
        updateValue(getValue(PREF_DEGREES),
                getValue(PREF_WIND),
                getValue(PREF_HUMID),
                getValue(PREF_PRESS));

//при нажатии на текстовое поле "влажность" - увеличение всех вью на 1
        view.findViewById(R.id.windForceView).setOnClickListener(v -> {
            updateValue(String.valueOf(Integer.parseInt(getValue(PREF_DEGREES)) + 1),
                    String.valueOf(Integer.parseInt(getValue(PREF_WIND)) + 1),
                    String.valueOf(Integer.parseInt(getValue(PREF_HUMID)) + 1),
                    String.valueOf(Integer.parseInt(getValue(PREF_PRESS)) + 1));
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
        //GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 3);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        hours= Arrays.asList(getResources().getStringArray(R.array.hours_in_a_day));
        adapter = new RecyclerHorizontalHoursAdapter(hours, this);

//        weekday= Arrays.asList(getResources().getStringArray(R.array.weekday));
//        adapterWeek = new RecyclerWeekDayAdapter(weekday, this);
        adapterWeek=new RecyclerWeekDayAdapter();
        adapterWeek.addItems(WeekDay.getDays(8, requireActivity()));

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
