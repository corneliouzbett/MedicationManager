package com.beliscosolutions.corneliouzbett.medmanager01.views;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

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

    private Button submitButton;


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
        TextInputLayout emailTextInputLayout = findViewById(R.id.text_input_email);
        TextInputLayout passwordTextInputLayout = findViewById(R.id.text_inputLayout_password);

        submitButton = findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

                InputValidation inputValidation = new InputValidation(getApplicationContext());
                boolean check = inputValidation.isInputEditTextMatches(passwordTextInputEditText,confirPpasswordTextInputEditText,
                        passwordTextInputLayout,"does not match");
                if (check &&
                        inputValidation.isInputEditTextFilled(emailAddressTextInputEditText,emailTextInputLayout,"Enter your Email here") &&
                        inputValidation.isInputEditTextFilled(passwordTextInputEditText,passwordTextInputLayout,"Enter your Password here")) {
                    User user = new User();
                    user.setName(nameTextInputEditText.getText().toString().trim());
                    user.setEmailAddress(emailAddressTextInputEditText.getText().toString().trim());
                    user.setPassword(passwordTextInputEditText.getText().toString().trim());
                    long status = databaseHelper.addUser(user);
                    if (status != -1){
                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(loginIntent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"Error occurred ", Toast.LENGTH_LONG);
                    }
                } else
                {
                    Snackbar.make(v, "Registration Failed. Please try again", Snackbar.LENGTH_LONG)
                            .setAction("OK", null).show();
                }
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
