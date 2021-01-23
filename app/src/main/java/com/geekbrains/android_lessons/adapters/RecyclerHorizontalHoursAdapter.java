package com.geekbrains.android_lessons.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.geekbrains.android_lessons.R;
import java.util.List;

public class RecyclerHorizontalHoursAdapter extends RecyclerView.Adapter<RecyclerHorizontalHoursAdapter.ViewHolder> {
    private final List<String> data;

    public RecyclerHorizontalHoursAdapter(List<String> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public RecyclerHorizontalHoursAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hours_weather_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHorizontalHoursAdapter.ViewHolder holder, int position) {
        setItemText(holder, data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    @SuppressLint("SetTextI18n")
    private void setItemText(@NonNull ViewHolder holder, String text) {
        holder.time.setText(text);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
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