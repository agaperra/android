package com.geekbrains.android_lessons.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.geekbrains.android_lessons.Constants;
import com.geekbrains.android_lessons.R;
import com.geekbrains.android_lessons.SharedPreferencesManager;
import com.geekbrains.android_lessons.adapters.RecyclerWeekDayAdapter;
import com.geekbrains.android_lessons.getWeather;
import com.geekbrains.android_lessons.interfaces.DateClick;
import com.geekbrains.android_lessons.model.AllList;
import com.geekbrains.android_lessons.model.WeatherRequest;
import com.google.android.material.snackbar.Snackbar;

import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

public class MainFragment extends Fragment implements DateClick {

    private TextView degreesCountView;
    private TextView windForceParameterView;
    private TextView humidityParameterView;
    private TextView pressureParameterView;
    private TextView cityNameView;
    private TextView typeWeather;
    private TextView timeView;
    private TextView feelsLike;
    private ImageView background;
    public static SharedPreferencesManager sPrefs;
    private final String url = Constants.url;
    private String message = "";
    private RecyclerView recyclerViewDays;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static WeatherRequest list;


    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @SuppressLint("StaticFieldLeak")
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error_", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void findViews(View v) {

        degreesCountView = v.findViewById(R.id.degreesCountView);
        windForceParameterView = v.findViewById(R.id.windForceParameterView);
        humidityParameterView = v.findViewById(R.id.humidityParameterView);
        pressureParameterView = v.findViewById(R.id.pressureParameterView);
        cityNameView = v.findViewById(R.id.cityNameView);
        typeWeather = v.findViewById(R.id.weatherTypeView);
        timeView = v.findViewById(R.id.updateTime);
        recyclerViewDays = v.findViewById(R.id.recyclerWeekday);
        feelsLike=v.findViewById(R.id.feelsLike);
        background=v.findViewById(R.id.backgroundView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_main, container, false);
        return root;
    }

    public void error(){
        Snackbar snackbar = Snackbar.make(requireView(), getString(R.string.error), Snackbar.LENGTH_LONG).
                setAction(getString(R.string.update), ignored -> {
                    getWeather.getWeather( this);
                    getWeather.getWeatherForecast(this);
//                    setData(Constants.urlWeatherStatic +
//                            URLEncoder.encode(getCityName())+Constants.final_url);
                });
        @SuppressLint("InflateParams")
        View customSnackView = getLayoutInflater().inflate(R.layout.rounded, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();

        snackbarLayout.setPadding(20, 20, 20, 20);
        snackbarLayout.addView(customSnackView, 0);
        snackbar.show();
    }

    @SuppressLint("DefaultLocale")
    public void displayWeather(WeatherRequest list) {
        cityNameView.setText(list.getName());
        sPrefs.storeString(Constants.tag_cityName, list.getName());

        String status = String.valueOf(list.getWeather()[0].getDescription());
        String weatherStatus = status.substring(0, 1).toUpperCase() + status.substring(1);
        typeWeather.setText(weatherStatus);
        sPrefs.storeString(Constants.PREF_TYPE, weatherStatus);

        degreesCountView.setText(String.format("%.0f", list.getMain().getTemp()));
        sPrefs.storeString(Constants.PREF_DEGREES, String.format("%.0f", list.getMain().getTemp()));

        pressureParameterView.setText(String.format("%d", list.getMain().getPressure()));
        sPrefs.storeString(Constants.PREF_PRESS, String.format("%d", list.getMain().getPressure()));

        humidityParameterView.setText(String.format("%d", list.getMain().getHumidity()));
        sPrefs.storeString(Constants.PREF_HUMID, String.format("%d", list.getMain().getHumidity()));

        windForceParameterView.setText(String.format("%.0f", list.getWind().getSpeed()));
        sPrefs.storeString(Constants.PREF_WIND, String.format("%.0f", list.getWind().getSpeed()));

        DateFormat df = DateFormat.getDateTimeInstance();
        String updatedOn = df.format(new Date(1000 * list.getDate()));
        timeView.setText(String.format("%s", updatedOn));
        sPrefs.storeString(Constants.tag_time, String.format("%s", updatedOn));

        String  icon = list.getWeather()[0].getIcon();

        int t = sPrefs.retrieveInt(Constants.tag_theme, Constants.THEME_LIGHT);
        setBackgroundMode(icon);
        switch (t) {
            case 0:
                if (icon.contains("d")){
                icon=icon.replaceAll("d", "n");
                }
                break;
            case 1:
                if (icon.contains("n")){
                    icon=icon.replaceAll("n", "d");
                }
                break;
        }
        String iconUrl = "http://openweathermap.org/img/wn/" + icon + "@4x.png";
        new DownloadImageTask((ImageView) requireView().findViewById(R.id.weather_icon)).execute(iconUrl);
        //Picasso.with(requireView().getContext()).load(iconUrl).into(weatherIcon);
        sPrefs.storeString(Constants.PREF_ICON, icon);

        feelsLike.setText(String.format("%.0f", list.getMain().getFeels_like()));
        sPrefs.storeString(Constants.PREF_FEEL, String.format("%.0f", list.getMain().getFeels_like()));

        updateAllParameters();
    }

    public void setBackgroundMode(String icon){
        String tmp="01";
        String mode="d";
        if (icon.contains("d")){
            mode="d";
            tmp=icon.replaceAll("d","");
        }else if (icon.contains("n")){
            mode="n";
            tmp=icon.replaceAll("n","");
        }
        switch (tmp){
            case "01":
                check(mode, R.drawable.icon_01d, R.drawable.icon_01n);
                break;
            case "02":
                check(mode, R.drawable.icon_02d, R.drawable.icon_02n);
                break;
            case "03":
                check(mode, R.drawable.icon_03d, R.drawable.icon_03d);
                break;
            case "04":
                check(mode, R.drawable.icon_04d, R.drawable.icon_04d);
                break;
            case "09":
                check(mode, R.drawable.icon_09d, R.drawable.icon_09n);
                break;
            case "10":
                check(mode, R.drawable.icon_10d, R.drawable.icon_10n);
                break;
            case "11":
                check(mode, R.drawable.icon_11d, R.drawable.icon_11n);
                break;
            case "13":
                check(mode, R.drawable.icon_13d, R.drawable.icon_13n);
                break;
            case "50":
                check(mode, R.drawable.icon_50d, R.drawable.icon_50n);
                break;
        }


    }

    public void check(String mode,int... tags){
        if (mode.equals("d")){
            background.setImageResource(tags[0]);
        }
        else if (mode.equals("n")){
            background.setImageResource(tags[1]);
        }
    }

    public void updateAllParameters(){
        updateValue(getCityName(),
                getValue(Constants.PREF_DEGREES),
                getValue(Constants.PREF_WIND),
                getValue(Constants.PREF_HUMID),
                getValue(Constants.PREF_PRESS),
                getValue(Constants.PREF_TYPE),
                getValue(Constants.tag_time),
                getValue(Constants.PREF_FEEL),
                getValue(Constants.PREF_ICON));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle("");
        ((AppCompatActivity) requireActivity()).setSupportActionBar(view.findViewById(R.id.toolbar));
        setHasOptionsMenu(true);
        setRetainInstance(true);
        sPrefs = new SharedPreferencesManager(requireContext());
        findViews(view);

        getWeather.getWeather(this);
        getWeather.getWeatherForecast(this);
        Intent intent = requireActivity().getIntent();
        if (intent.hasExtra(Constants.tag_cityName)) {
            message = intent.getStringExtra(Constants.tag_cityName);
            if (!message.equals("")) {
                sPrefs.storeString(Constants.tag_cityName, message);
                cityNameView.setText(message);

            }
        }
        updateAllParameters();

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_menu);

        mSwipeRefreshLayout = view.findViewById(R.id.refresh);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {

            mSwipeRefreshLayout.setRefreshing(true);
            // ждем 3 секунды и прячем прогресс
            mSwipeRefreshLayout.postOnAnimationDelayed(() -> {
                getWeather.getWeather(this);
                getWeather.getWeatherForecast(this);
                updateAllParameters();
                mSwipeRefreshLayout.setRefreshing(false);

            }, 1000);});

        int t = sPrefs.retrieveInt(Constants.tag_theme, Constants.THEME_LIGHT);
        switch (t) {
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }


//по нажатию на название города - открытие поискового запроса в яндексе
        view.findViewById(R.id.search_in_internet).setOnClickListener(v -> {
            if (!message.trim().equals("")) {
                Snackbar snackbar = Snackbar.make(requireView(), getString(R.string.open_url), Snackbar.LENGTH_LONG).
                        setAction(getString(R.string.yes), ignored -> {
                            Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
                            openURL.setData(Uri.parse(url + message));
                            startActivity(openURL);
                        });
                snackbar.setTextColor(Color.WHITE);
                snackbar.show();
            }
        });
    }

    public void setupRecyclerView(WeatherRequest[] list) {

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
        );

        RecyclerWeekDayAdapter adapterWeek = new RecyclerWeekDayAdapter(list);
        adapterWeek.addItems(AllList.getDays(5, requireActivity()));

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

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public static void checkSharedPreferences(String keyContainer, String tag, String parameter, TextView textView, int defaultConst, double multi, double shift, String... tags) {
        sPrefs.getEditor().putString(keyContainer, parameter).apply();
        switch (sPrefs.retrieveInt(tag, defaultConst)) {
            case 0:
                textView.setText(parameter + " " + tags[0]);
                break;
            case 1:
                parameter = parameter.replaceAll(",", ".");
                double value = Double.parseDouble(parameter) * multi + shift;
                textView.setText(String.format("%.0f", value) + " " + tags[1]);
                break;

        }
    }

    @SuppressLint("SetTextI18n")
    private void updateValue(String... parameters) {

        sPrefs.getEditor().putString(Constants.tag_cityName, parameters[0]).apply();
        cityNameView.setText(parameters[0]);

        checkSharedPreferences(Constants.PREF_DEGREES,
                Constants.tag_temp,
                parameters[1],
                degreesCountView,
                Constants.POSTFIX_KELVIN,
                1,
                -273.15,
                getString(R.string.kelvin),
                getString(R.string.cels));

        checkSharedPreferences(Constants.PREF_WIND,
                Constants.tag_wind,
                parameters[2],
                windForceParameterView,
                Constants.WINDFORCE_KMH,
                0.27, 0,
                getString(R.string.km_h),
                getString(R.string.m_s));

        sPrefs.getEditor().putString(Constants.PREF_HUMID, parameters[3]).apply();
        humidityParameterView.setText(parameters[3] + " %");


        checkSharedPreferences(Constants.PREF_PRESS,
                Constants.tag_pressure,
                parameters[4],
                pressureParameterView,
                Constants.PRESSURE_GPA,
                0.75,
                0,
                getString(R.string.gPa),
                "\n"+getString(R.string.mm_of_m_c));
        sPrefs.getEditor().putString(Constants.PREF_TYPE, parameters[5]).apply();
        typeWeather.setText(parameters[5]);

        sPrefs.getEditor().putString(Constants.tag_time, parameters[6]).apply();
        timeView.setText(parameters[6]);

        checkSharedPreferences(Constants.PREF_FEEL, Constants.tag_temp, parameters[7], feelsLike, Constants.POSTFIX_KELVIN, 1, -273.15, getString(R.string.kelvin), getString(R.string.cels));


        int t = sPrefs.retrieveInt(Constants.tag_theme, Constants.THEME_LIGHT);
        setBackgroundMode(parameters[8]);
        switch (t) {
            case 0:
                if (parameters[8].contains("d")){
                    parameters[8]=parameters[8].replaceAll("d", "n");
                }
                break;
            case 1:
                if (parameters[8].contains("n")){
                    parameters[8]=parameters[8].replaceAll("n", "d");
                }
                break;
        }
        String iconUrl = "http://openweathermap.org/img/wn/" + parameters[8] + "@4x.png";
        new DownloadImageTask((ImageView) requireView().findViewById(R.id.weather_icon)).execute(iconUrl);
        //Picasso.with(requireView().getContext()).load(iconUrl).into(weatherIcon);
        sPrefs.getEditor().putString(Constants.PREF_ICON,parameters[8]).apply();





    }

    private String getValue(String key) {
        return sPrefs.retrieveString(key, "0");
    }

    public String getCityName() {
        return sPrefs.retrieveString(Constants.tag_cityName, getString(R.string.moscow));
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
    public void onItemClicked(String itemText) { }

}
