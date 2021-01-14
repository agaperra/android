package com.geekbrains.android_lessons;



import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;


public class SearchActivity extends AppCompatActivity {

    private String data;
    private final int searchActivityRequestCode = 1234;
    private TextView city1;
    private TextView city2;
    private TextView city3;
    private TextView city4;
    private TextView city5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        findViews();
        //заглушки, пока не получается сделать RecyclerView
        city1.setOnClickListener(v->{
            Intent intentCity = new Intent(SearchActivity.this, MainActivity.class);
            intentCity.putExtra("cityName", city1.getText());
            startActivityForResult(intentCity, searchActivityRequestCode);
        });

        city2.setOnClickListener(v->{
            Intent intentCity = new Intent(SearchActivity.this, MainActivity.class);
            intentCity.putExtra("cityName", city2.getText());
            startActivityForResult(intentCity, searchActivityRequestCode);
        });

        city3.setOnClickListener(v->{
            Intent intentCity = new Intent(SearchActivity.this, MainActivity.class);
            intentCity.putExtra("cityName", city3.getText());
            startActivityForResult(intentCity, searchActivityRequestCode);
        });

        city4.setOnClickListener(v->{
            Intent intentCity = new Intent(SearchActivity.this, MainActivity.class);
            intentCity.putExtra("cityName", city4.getText());
            startActivityForResult(intentCity, searchActivityRequestCode);
        });

        city5.setOnClickListener(v->{
            Intent intentCity = new Intent(SearchActivity.this, MainActivity.class);
            intentCity.putExtra("cityName", city5.getText());
            startActivityForResult(intentCity, searchActivityRequestCode);
        });
    }

    private void findViews() {
        city1=findViewById(R.id.City1);
        city2=findViewById(R.id.City2);
        city3=findViewById(R.id.City3);
        city4=findViewById(R.id.City4);
        city5=findViewById(R.id.City5);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        setTitle("");
        SearchView actionSearch = (SearchView) menu.findItem(R.id.action_search).getActionView();

        actionSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!data.trim().equals("")) {
                    Intent intentCity = new Intent(SearchActivity.this, MainActivity.class);
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


        return true;

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}