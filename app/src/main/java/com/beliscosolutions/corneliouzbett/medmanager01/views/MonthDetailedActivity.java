package com.beliscosolutions.corneliouzbett.medmanager01.views;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.beliscosolutions.corneliouzbett.medmanager01.R;
import com.beliscosolutions.corneliouzbett.medmanager01.adapters.MedicationRecyclerAdapter;
import com.beliscosolutions.corneliouzbett.medmanager01.adapters.SimpleDividerItemDecoration;
import com.beliscosolutions.corneliouzbett.medmanager01.helpers.sql.DatabaseHelper;
import com.beliscosolutions.corneliouzbett.medmanager01.model.Medication;
import com.beliscosolutions.corneliouzbett.medmanager01.utils.GetMonthByNumber;

import java.util.ArrayList;

public class MonthDetailedActivity extends AppCompatActivity {

    private RecyclerView medicationRecyclerView;
    private MedicationRecyclerAdapter medicationRecyclerAdapter;
    private ArrayList<Medication> medicationMonthArrayList;
    private DatabaseHelper databaseHelper;
    int month;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_detailed);

        Bundle bundle = getIntent().getExtras();
        month = bundle.getInt("month");
        getSupportActionBar().setTitle(GetMonthByNumber.get(month + 1));

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        medicationRecyclerView = findViewById(R.id.rv_month_detailed);
        medicationMonthArrayList = new ArrayList<>();
        medicationRecyclerAdapter = new MedicationRecyclerAdapter(medicationMonthArrayList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        medicationRecyclerView.setLayoutManager(mLayoutManager);
        medicationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        medicationRecyclerView.setHasFixedSize(true);
        medicationRecyclerView.addItemDecoration( new SimpleDividerItemDecoration(getApplicationContext()));
        medicationRecyclerView.setAdapter(medicationRecyclerAdapter);
        databaseHelper = new DatabaseHelper(this);
        getDataFromSQLite(month);
    }

    /**
     * This method is to fetch all medication records from SQLite
     */
    private void getDataFromSQLite(int month) {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                medicationMonthArrayList.clear();
                medicationMonthArrayList.addAll(databaseHelper.getAllMedicationsByMonth(month));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                medicationRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
