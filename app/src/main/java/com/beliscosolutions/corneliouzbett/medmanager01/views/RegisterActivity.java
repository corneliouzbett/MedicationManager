package com.beliscosolutions.corneliouzbett.medmanager01.views;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.beliscosolutions.corneliouzbett.medmanager01.R;
import com.beliscosolutions.corneliouzbett.medmanager01.helpers.InputValidation;
import com.beliscosolutions.corneliouzbett.medmanager01.helpers.sql.DatabaseHelper;
import com.beliscosolutions.corneliouzbett.medmanager01.helpers.model.User;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText nameTextInputEditText;
    private TextInputEditText emailAddressTextInputEditText;
    private TextInputEditText passwordTextInputEditText;
    private TextInputEditText confirPpasswordTextInputEditText;
    private TextInputLayout passwordTextInputLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        nameTextInputEditText = findViewById(R.id.text_input_editText_name);
        emailAddressTextInputEditText = findViewById(R.id.text_input_editText_email);
        passwordTextInputEditText = findViewById(R.id.text_input_editText_password);
        confirPpasswordTextInputEditText = findViewById(R.id.text_input_editText_confirmpassword);
        passwordTextInputLayout = findViewById(R.id.text_inputLayout_password);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        //sign up with firebase email and password then save them in sqlite
        InputValidation inputValidation = new InputValidation(getApplicationContext());
        boolean check = inputValidation.isInputEditTextMatches(passwordTextInputEditText,confirPpasswordTextInputEditText,
                passwordTextInputLayout,"does not match");
        if (check) {
            User user = new User();
            user.setName(nameTextInputEditText.getText().toString().trim());
            user.setEmailAddress(emailAddressTextInputEditText.getText().toString().trim());
            user.setPassword(passwordTextInputEditText.getText().toString().trim());
            long status = databaseHelper.addUser(user);
            if (status != -1){
                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(),"Error occurred ", Toast.LENGTH_LONG);
            }
        } else
            {
                //do nothing
            }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
