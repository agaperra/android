package com.geekbrains.android_lessons.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.geekbrains.android_lessons.MainActivity;
import com.geekbrains.android_lessons.R;

import java.util.Objects;

public class SearchFragment extends Fragment {
    private String data;
    private final int searchActivityRequestCode = 1234;
    private TextView city1;
    private TextView city2;
    private TextView city3;
    private TextView city4;
    private TextView city5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle("");
        setHasOptionsMenu(true);
        setRetainInstance(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.back_line);
        findViews(view);
        //заглушки, пока не получается сделать RecyclerView
        city1.setOnClickListener(v -> {
            Intent intentCity = new Intent(view.getContext(), MainActivity.class);
            intentCity.putExtra("cityName", city1.getText());
            startActivityForResult(intentCity, searchActivityRequestCode);
        });

        city2.setOnClickListener(v -> {
            Intent intentCity = new Intent(view.getContext(), MainActivity.class);
            intentCity.putExtra("cityName", city2.getText());
            startActivityForResult(intentCity, searchActivityRequestCode);
        });

        city3.setOnClickListener(v -> {
            Intent intentCity = new Intent(view.getContext(), MainActivity.class);
            intentCity.putExtra("cityName", city3.getText());
            startActivityForResult(intentCity, searchActivityRequestCode);
        });

        city4.setOnClickListener(v -> {
            Intent intentCity = new Intent(view.getContext(), MainActivity.class);
            intentCity.putExtra("cityName", city4.getText());
            startActivityForResult(intentCity, searchActivityRequestCode);
        });

        city5.setOnClickListener(v -> {
            Intent intentCity = new Intent(view.getContext(), MainActivity.class);
            intentCity.putExtra("cityName", city5.getText());
            startActivityForResult(intentCity, searchActivityRequestCode);
        });

    }

    private void findViews(View v) {
        city1 = v.findViewById(R.id.City1);
        city2 = v.findViewById(R.id.City2);
        city3 = v.findViewById(R.id.City3);
        city4 = v.findViewById(R.id.City4);
        city5 = v.findViewById(R.id.City5);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu, inflater);
        SearchView actionSearch = (SearchView) menu.findItem(R.id.action_search).getActionView();
        actionSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                if (!data.trim().equals("")) {
//                    Intent intentCity = new Intent(requireView().getContext(), MainActivity.class);
//                    intentCity.putExtra("cityName", data);
//                    startActivityForResult(intentCity, searchActivityRequestCode);
//                    return true;
//                }
               return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                data = newText;
                return true;
            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//
//            Intent intentCity = new Intent(requireView().getContext(), MainFragment.class);
//            intentCity.putExtra("cityName", data);
//            startActivityForResult(intentCity, searchActivityRequestCode);
        Toast.makeText(requireView().getContext(), R.string.error_one, Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireView()).popBackStack();
        return super.onOptionsItemSelected(item);

    }

}
