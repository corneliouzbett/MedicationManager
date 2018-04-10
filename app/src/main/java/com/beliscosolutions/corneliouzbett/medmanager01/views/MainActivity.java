package com.beliscosolutions.corneliouzbett.medmanager01.views;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.beliscosolutions.corneliouzbett.medmanager01.R;
import com.beliscosolutions.corneliouzbett.medmanager01.adapters.MedicationRecyclerAdapter;
import com.beliscosolutions.corneliouzbett.medmanager01.adapters.SimpleDividerItemDecoration;
import com.beliscosolutions.corneliouzbett.medmanager01.helpers.sql.DatabaseHelper;
import com.beliscosolutions.corneliouzbett.medmanager01.model.Medication;
import com.beliscosolutions.corneliouzbett.medmanager01.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private RecyclerView medicationRecyclerView;
    private MedicationRecyclerAdapter medicationRecyclerAdapter;
    private ArrayList<Medication> medicationArrayList;
    private DatabaseHelper databaseHelper;

    private TextView displayNameTextView;
    private TextView accountEmailTextView;
    private ImageView photourlImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar =  findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent medCreationIntent = new Intent(MainActivity.this,MedicationCreationActivity.class);
                medCreationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(medCreationIntent);

                /*Snackbar.make(view, "Working on it", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        medicationRecyclerView = findViewById(R.id.recyclerview_medication);
        medicationArrayList = new ArrayList<>();
        medicationRecyclerAdapter = new MedicationRecyclerAdapter(medicationArrayList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        medicationRecyclerView.setLayoutManager(mLayoutManager);
        medicationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        medicationRecyclerView.setHasFixedSize(true);
        medicationRecyclerView.addItemDecoration( new SimpleDividerItemDecoration(getApplicationContext()));
        medicationRecyclerView.setAdapter(medicationRecyclerAdapter);
        databaseHelper = new DatabaseHelper(this);

        getDataFromSQLite();

        Bundle bundle = getIntent().getExtras();

        displayNameTextView = findViewById(R.id.tv_display_name);
        accountEmailTextView = findViewById(R.id.tv_account_email);
        photourlImageView = findViewById(R.id.imageview_photourl);


/*        displayNameTextView.setText(bundle.getString("display_name"));
        accountEmailTextView.setText(bundle.getString("email"));

        Picasso.with(this)
                .load(bundle.getString("photo_url"))
                .centerCrop()
                .into(photourlImageView);*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

        } else if (id == R.id.action_settings){

        } else if (id == R.id.action_category){

            Intent categotyIntent = new Intent(MainActivity.this,CategorizeByMonth.class);
            categotyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(categotyIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<Medication> newList = new ArrayList <>();
        for (Medication medication : medicationArrayList){
            String name = medication.getName().toLowerCase();
            if (name.contains(newText)){
                newList.add(medication);
            }
        }
        medicationRecyclerAdapter.setFilter(newList);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_settings) {

        }  else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * This method is to fetch all medication records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                medicationArrayList.clear();
                medicationArrayList.addAll(databaseHelper.getAllMedications());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                medicationRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}
