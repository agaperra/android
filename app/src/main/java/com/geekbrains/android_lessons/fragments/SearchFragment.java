package com.geekbrains.android_lessons.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android_lessons.MainActivity;
import com.geekbrains.android_lessons.R;
import com.geekbrains.android_lessons.adapters.RecyclerCityAdapter;
import com.geekbrains.android_lessons.interfaces.CityClick;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SearchFragment extends Fragment implements CityClick {
    private String data;
    private final int searchActivityRequestCode = 1234;
    private RecyclerView recyclerView;
    private List<String> topCities;
    private RecyclerCityAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle("");
        setHasOptionsMenu(true);
        setRetainInstance(true);
        initViews(view);
        setupRecyclerView();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setHomeAsUpIndicator(R.drawable.back_line);


    }
    private void initViews(View v) {
        recyclerView = v.findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView() {
        /*LinearLayoutManager layoutManager = new LinearLayoutManager(
                getBaseContext(), LinearLayoutManager.HORIZONTAL, true
        );*/
        //GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        topCities = Arrays.asList(getResources().getStringArray(R.array.topCities));
        adapter = new RecyclerCityAdapter(topCities, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.search, menu);
        SearchView actionSearch = (SearchView) menu.findItem(R.id.action_search).getActionView();
        super.onCreateOptionsMenu(menu, inflater);
        actionSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!data.trim().equals("")) {
                    Intent intentCity = new Intent(requireView().getContext(), MainActivity.class);
                    intentCity.putExtra("cityName", data);
                    startActivityForResult(intentCity, searchActivityRequestCode);
                    return true;
                }
               return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                data = newText;
                return true;
            }
        });

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem actionSearch = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) actionSearch.getActionView();

        int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
        ImageView v = mSearchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.ic_search);

        int searchExit = getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView k = mSearchView.findViewById(searchExit);
        k.setImageResource(R.drawable.exit);

        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) mSearchView.findViewById(id);
        textView.setTextColor(Color.rgb(195,61,61));
        super.onPrepareOptionsMenu(menu);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//
//            Intent intentCity = new Intent(requireView().getContext(), MainFragment.class);
//            intentCity.putExtra("cityName", data);
//            startActivityForResult(intentCity, searchActivityRequestCode);

        //пробовала передать город в главный фрагмент - пока не получилось
        Toast.makeText(requireView().getContext(), R.string.error_one, Toast.LENGTH_SHORT).show();
        //Navigation.findNavController(requireView()).popBackStack();
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onItemClicked(String itemText) {
        Intent intentCity = new Intent(getView().getContext(), MainActivity.class);
        intentCity.putExtra("cityName", itemText);
        startActivityForResult(intentCity, searchActivityRequestCode);
    }

}
