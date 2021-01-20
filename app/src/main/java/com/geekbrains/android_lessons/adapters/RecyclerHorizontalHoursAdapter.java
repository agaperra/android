package com.geekbrains.android_lessons.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android_lessons.R;
import com.geekbrains.android_lessons.interfaces.CityClick;
import com.geekbrains.android_lessons.interfaces.HoursClick;

import java.util.List;

public class RecyclerHorizontalHoursAdapter extends RecyclerView.Adapter<RecyclerHorizontalHoursAdapter.ViewHolder> {
    private List<String> data;
    private HoursClick onItemClickCallback;

    public RecyclerHorizontalHoursAdapter(List<String> data, HoursClick onItemClickCallback) {
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
    public RecyclerHorizontalHoursAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hours_weather_item, parent, false);
        return new RecyclerHorizontalHoursAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHorizontalHoursAdapter.ViewHolder holder, int position) {
        setItemText(holder, data.get(position));
        //setOnClickForItem(holder, data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    private void setItemText(@NonNull RecyclerHorizontalHoursAdapter.ViewHolder holder, String text) {
        holder.time.setText(text);
    }

//    private void setOnClickForItem(@NonNull ViewHolder holder, String text) {
//        holder.layout.setOnClickListener(v -> {
//            if (onItemClickCallback != null) {
//                onItemClickCallback.onItemClicked(text);
//            }
//        });
//    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout;
        TextView time;
        TextView icon;
        TextView degrees;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.listHoursItemLayout);
            time = itemView.findViewById(R.id.timeTextView);
            icon = itemView.findViewById(R.id.weatherIconTime);
            degrees = itemView.findViewById(R.id.degreesCountTime);
        }
    }
}