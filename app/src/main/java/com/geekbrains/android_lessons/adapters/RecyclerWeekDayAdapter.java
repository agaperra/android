package com.geekbrains.android_lessons.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android_lessons.Constants;
import com.geekbrains.android_lessons.R;
import com.geekbrains.android_lessons.SharedPreferencesManager;
import com.geekbrains.android_lessons.fragments.MainFragment;
import com.geekbrains.android_lessons.model.AllList;
import com.geekbrains.android_lessons.model.WeatherRequest;

import java.util.ArrayList;


public class RecyclerWeekDayAdapter extends RecyclerView.Adapter<RecyclerWeekDayAdapter.ViewHolder> {
    private final ArrayList<AllList> days = new ArrayList<>();
    private static final SharedPreferencesManager sPrefs= MainFragment.sPrefs;
    private int shift=0;
    private static WeatherRequest[] list;

    public RecyclerWeekDayAdapter(WeatherRequest[] list) {
        RecyclerWeekDayAdapter.list =list;
    }


    @NonNull
    @Override
    public RecyclerWeekDayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weekday_item, parent, false);
        return new RecyclerWeekDayAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerWeekDayAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return days.size();
    }

    public void addItems(ArrayList<AllList> arrayList) {
        days.addAll(arrayList);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        CardView layout;
        TextView date, weekDay,  degrees;
        ImageView weatherIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.ItemLayoutWeekDay);
            date = itemView.findViewById(R.id.dateTextView);
            weekDay = itemView.findViewById(R.id.weatherDayView);
            weatherIcon = itemView.findViewById(R.id.weatherIconWeekDay);
            degrees = itemView.findViewById(R.id.degreesWeekDay);
        }



        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void bind(int position) {
            AllList day = days.get(position);
            weekDay.setText(day.getDayOfWeek());
            date.setText(day.getDay());


            if(shift<=list.length) {
                switch (sPrefs.retrieveInt(Constants.tag_temp, Constants.POSTFIX_KELVIN)) {
                    case 0:
                        degrees.setText(String.format("%.1f", list[shift].getMain().getTemp()) + "K\u00B0");
                        break;
                    case 1:
                        String parameter = String.valueOf(list[shift].getMain().getTemp());
                        parameter = parameter.replaceAll(",", ".");
                        double value = Double.parseDouble(parameter) - 273.15;
                        degrees.setText(String.format("%.1f", value) + "С\u00B0");
                        break;
                }
                String icon = list[shift].getWeather()[0].getIcon();
                new MainFragment.DownloadImageTask(weatherIcon).execute("http://openweathermap.org/img/wn/" + icon + "@4x.png");
                int t = sPrefs.retrieveInt(Constants.tag_theme, Constants.THEME_LIGHT);
                if (t==1) {
                    weatherIcon.setColorFilter(Color.WHITE);
                }

                shift += 7;
            }
        }

    }

}