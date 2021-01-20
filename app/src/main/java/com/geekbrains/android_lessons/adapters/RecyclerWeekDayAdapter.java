package com.geekbrains.android_lessons.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android_lessons.R;
import com.geekbrains.android_lessons.fragments.MainFragment;
import com.geekbrains.android_lessons.interfaces.DateClick;

import java.util.List;

public class RecyclerWeekDayAdapter extends RecyclerView.Adapter<RecyclerWeekDayAdapter.ViewHolder> {
    private List<String> data;
    private DateClick onItemClickCallback;

    public RecyclerWeekDayAdapter(List<String> data, DateClick onItemClickCallback) {
        this.data = data;
        this.onItemClickCallback = onItemClickCallback;
    }

//    private void setOnClickForItem(@NonNull RecyclerCityAdapter.ViewHolder holder, String text) {
//        holder.nextCityItem.setOnClickListener(v -> {
//            if(onItemClickCallback != null) {
//                onItemClickCallback.onItemClicked(text);
//            }
//        });
//    }

    @NonNull
    @Override
    public RecyclerWeekDayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weekday_item, parent, false);
        return new RecyclerWeekDayAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerWeekDayAdapter.ViewHolder holder, int position) {
        setItemText(holder, data.get(position));
        //setOnClickForItem(holder, data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    private void setItemText(@NonNull RecyclerWeekDayAdapter.ViewHolder holder, String text) {
        holder.weekDay.setText(text);
    }

//    private void setOnClickForItem(@NonNull ViewHolder holder, String text) {
//        holder.layout.setOnClickListener(v -> {
//            if (onItemClickCallback != null) {
//                onItemClickCallback.onItemClicked(text);
//            }
//        });
//    }

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
    }
}