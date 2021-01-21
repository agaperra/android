package com.geekbrains.android_lessons.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android_lessons.R;
import com.geekbrains.android_lessons.WeekDay;
import com.geekbrains.android_lessons.fragments.MainFragment;
import com.geekbrains.android_lessons.interfaces.DateClick;

import java.util.ArrayList;
import java.util.List;

public class RecyclerWeekDayAdapter extends RecyclerView.Adapter<RecyclerWeekDayAdapter.ViewHolder> {
    private final ArrayList<WeekDay> days = new ArrayList<>();

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

    public void addItems(ArrayList<WeekDay> arrayList) {
        days.addAll(arrayList);
    }

    public ArrayList<WeekDay> getItems() {
        return days;
    }

    public void clearItems() {
        days.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView layout;
        TextView date, weekDay, weatherIcon, degrees;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.ItemLayoutWeekDay);
            date = itemView.findViewById(R.id.dateTextView);
            weekDay = itemView.findViewById(R.id.weatherDayView);
            weatherIcon = itemView.findViewById(R.id.weatherIconWeekDay);
            degrees = itemView.findViewById(R.id.degreesWeekDay);
        }

        public void bind(int position) {
            WeekDay day = days.get(position);

            weekDay.setText(day.getDayOfWeek());
        }
    }
}