package com.geekbrains.android_lessons.adapters;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android_lessons.Constants;
import com.geekbrains.android_lessons.Hours;
import com.geekbrains.android_lessons.R;
import com.geekbrains.android_lessons.SharedPreferencesManager;
import com.geekbrains.android_lessons.fragments.MainFragment;
import com.geekbrains.android_lessons.model.WeatherRequest;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerHorizontalHoursAdapter extends RecyclerView.Adapter<RecyclerHorizontalHoursAdapter.ViewHolder> {

    private final ArrayList<Hours> hours = new ArrayList<>();
    private static WeatherRequest[] list;
    private static final SharedPreferencesManager sPrefs= MainFragment.sPrefs;
    private final int shift=0;

    public RecyclerHorizontalHoursAdapter(WeatherRequest[] list) {
        RecyclerHorizontalHoursAdapter.list =list;
    }

    @NonNull
    @Override
    public RecyclerHorizontalHoursAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hours_weather_item, parent, false);
        return new RecyclerHorizontalHoursAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHorizontalHoursAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return hours.size();
    }

    public void addItems(ArrayList<Hours> arrayList) {
        hours.addAll(arrayList);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout;
        TextView time;
        ImageView iconW;
        TextView degrees;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.listHoursItemLayout);
            time = itemView.findViewById(R.id.timeTextView);
            iconW = itemView.findViewById(R.id.weatherIconTime);
            degrees = itemView.findViewById(R.id.degreesCountTime);
        }

        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void bind(int position) {
            if(position<=list.length) {
                DateFormat df = DateFormat.getDateTimeInstance();
                String updatedOn = df.format(new Date(1000 * list[position].getDate()));
                updatedOn=updatedOn.substring(16, 18);
                if (updatedOn.contains(":"))
                    updatedOn+="00";
                else updatedOn+=":00";

                time.setText(String.format("%s", updatedOn));
                String t=String.format("%.0f", list[position].getMain().getTemp());
                double k=list[position].getMain().getTemp();
                if (t.contains("-")){
                    if (k<273.15&&k>272.15){
                        k=273.15;
                    }
                }
                switch (sPrefs.retrieveInt(Constants.tag_temp, Constants.POSTFIX_KELVIN)) {
                    case 0:

                        degrees.setText(k + "K\u00B0");
                        break;
                    case 1:
                        String parameter=String.valueOf(k);
                        parameter = parameter.replaceAll(",", ".");
                        double value = Double.parseDouble(parameter) - 273.15;
                        degrees.setText(String.format("%.0f", value) + "С\u00B0");
                        break;
                }
                MainFragment.setIcons(list[position],iconW);
            }
        }
    }
}