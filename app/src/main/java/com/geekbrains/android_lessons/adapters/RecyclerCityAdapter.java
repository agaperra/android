package com.geekbrains.android_lessons.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android_lessons.R;
import com.geekbrains.android_lessons.fragments.SearchFragment;
import com.geekbrains.android_lessons.interfaces.CityClick;

import java.util.ArrayList;
import java.util.List;


public class RecyclerCityAdapter extends RecyclerView.Adapter<RecyclerCityAdapter.ViewHolder> {
    private List<String> data;
    private CityClick onItemClickCallback;

    public RecyclerCityAdapter(List<String> data, CityClick onItemClickCallback) {
        this.data = data;
        this.onItemClickCallback=onItemClickCallback;
    }

    private void setOnClickForItem(@NonNull ViewHolder holder, String text) {
        holder.nextCityItem.setOnClickListener(v -> {
            if(onItemClickCallback != null) {
                onItemClickCallback.onItemClicked(text);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setItemText(holder, data.get(position));
        setOnClickForItem(holder, data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }



    private void setItemText(@NonNull ViewHolder holder, String text) {
        holder.textView.setText(text);
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
        ImageView nextCityItem;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.listItemLayout);
            textView = itemView.findViewById(R.id.itemTextView);
            nextCityItem=itemView.findViewById(R.id.nextCityItem);
        }
    }
}
