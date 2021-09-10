package com.corneliouzbett.medmanager.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.corneliouzbett.medmanager.R;
import com.corneliouzbett.medmanager.helpers.sql.DatabaseHelper;
import com.corneliouzbett.medmanager.helpers.model.User;

import java.util.List;

public class UpdateInfoActivity extends AppCompatActivity {

    private DatabaseHelper helper;
    private Button updateProfileButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        updateProfileButton = findViewById(R.id.button_update_profile);

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper = new DatabaseHelper(getApplicationContext());
                List<User> user = helper.getAllUsers();
                for (User user1 : user){
                    Toast.makeText(getApplicationContext(),""+user1.getName(),Toast.LENGTH_LONG);

                }
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
