package com.beliscosolutions.corneliouzbett.medmanager01.views;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beliscosolutions.corneliouzbett.medmanager01.R;
import com.beliscosolutions.corneliouzbett.medmanager01.helpers.sql.DatabaseHelper;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    private SignInButton signinwithGoogleButton;
    public static String name;
    public static String email;
    public static Uri profile;

    private TextView registerTextView;

    final String TAG = "LoginActivity :";
    private FirebaseAuth mAuth;
    private static GoogleSignInClient mGoogleSignInClient;
    public static FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        registerTextView = findViewById(R.id.tv_register);
        registerTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task <GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task <GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null){

                DatabaseHelper databaseHelper = new DatabaseHelper(this);
                com.beliscosolutions.corneliouzbett.medmanager01.helpers.model.User userProfile =
                        new com.beliscosolutions.corneliouzbett.medmanager01.helpers.model.User();
                userProfile.setEmailAddress(account.getEmail());
                userProfile.setName(account.getDisplayName());
                databaseHelper.addUser(userProfile);

                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(getApplicationContext(), "Authentication Failed... Try Again",
                    Toast.LENGTH_LONG).show();
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            currentUser = mAuth.getCurrentUser();
                            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

}
