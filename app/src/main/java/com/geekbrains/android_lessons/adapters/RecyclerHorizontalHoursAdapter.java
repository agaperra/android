package com.geekbrains.android_lessons.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.geekbrains.android_lessons.Hours;
import com.geekbrains.android_lessons.R;
import java.util.ArrayList;

public class RecyclerHorizontalHoursAdapter extends RecyclerView.Adapter<RecyclerHorizontalHoursAdapter.ViewHolder> {

    private final ArrayList<Hours> hours = new ArrayList<>();

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
        TextView icon;
        TextView degrees;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.listHoursItemLayout);
            time = itemView.findViewById(R.id.timeTextView);
            icon = itemView.findViewById(R.id.weatherIconTime);
            degrees = itemView.findViewById(R.id.degreesCountTime);
        }

        public void bind(int position) {
            Hours hour = hours.get(position);

            time.setText(hour.getHourInADay());
        }
    }
}