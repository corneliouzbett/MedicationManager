package com.beliscosolutions.corneliouzbett.medmanager01.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.beliscosolutions.corneliouzbett.medmanager01.R;
import com.beliscosolutions.corneliouzbett.medmanager01.adapters.CategorizeByMonthRecyclerAdapter;

public class CategorizeByMonth extends AppCompatActivity {

    private RecyclerView monthRecyclerView;
    private CategorizeByMonthRecyclerAdapter categorizeByMonthRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorize_by_month);

        getSupportActionBar().setTitle("Categorize By Month");

        String [] data = {"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE",
        "JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};

        monthRecyclerView = findViewById(R.id.rv_month_categories);
        monthRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        categorizeByMonthRecyclerAdapter = new CategorizeByMonthRecyclerAdapter(data,this);

        monthRecyclerView.setAdapter(categorizeByMonthRecyclerAdapter);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
