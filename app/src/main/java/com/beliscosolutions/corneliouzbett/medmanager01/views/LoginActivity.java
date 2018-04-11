package com.beliscosolutions.corneliouzbett.medmanager01.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.beliscosolutions.corneliouzbett.medmanager01.R;
import com.beliscosolutions.corneliouzbett.medmanager01.helpers.sql.DatabaseHelper;
import com.beliscosolutions.corneliouzbett.medmanager01.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1 ;
    private SignInButton signinwithGoogleButton;


    final String TAG = "LoginActivity :";
    private FirebaseAuth mAuth;
    private static GoogleSignInClient mGoogleSignInClient;
    public static String displayName;
    public static String email;
    public static Uri photouri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        signinwithGoogleButton = findViewById(R.id.sign_in_button);

        signinwithGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account == null){
            // continues to the sign in
        } else {

            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            User user = new User();
            user.setEmailAddress(account.getEmail());
            user.setName(account.getDisplayName());
            user.setPassword(account.getPhotoUrl().toString());
            databaseHelper.addUser(user);
            Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainIntent.putExtra("display_name",account.getDisplayName());
            Toast.makeText(getApplicationContext(),""+account.getDisplayName(),Toast.LENGTH_LONG).show();
            mainIntent.putExtra("email",account.getEmail());
            mainIntent.putExtra("photo_url",account.getPhotoUrl());
            displayName = account.getDisplayName();
            email = account.getEmail();
            photouri = account.getPhotoUrl();
            startActivity(mainIntent);
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            User user = new User();
            user.setEmailAddress(account.getEmail());
            user.setName(account.getDisplayName());
            user.setPassword(account.getPhotoUrl().toString());
            databaseHelper.addUser(user);

            Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainIntent.putExtra("display_name",account.getDisplayName());
            mainIntent.putExtra("email",account.getEmail());
            mainIntent.putExtra("photo_url",account.getPhotoUrl());
            startActivity(mainIntent);
            finish();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(getApplicationContext(),"Authentication Failed... Try Again",
                    Toast.LENGTH_LONG).show();
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public static void signOut(Context context) {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener((Activity) context, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // sign out completed succesfull
                        Log.i("MainActivity :","Signing Out is completed successfully");
                    }
                });
    }
}
