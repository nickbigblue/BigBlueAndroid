package com.bigblueocean.nick.bigblueocean;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.lang.reflect.Array;


public class LoginActivity extends Activity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String email;
    private String password;
    private EditText emailEdit;
    private EditText passwordEdit;
    private TextView title;
    private TextView subtitle;
    private FloatingActionButton loginButton;
    private FloatingActionButton registerButton;
    private ImageView background;
    private ImageView logo;
    private ImageView backdrop;
    private Intent LoginToHomeIntent;
    private final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginToHomeIntent = new Intent(this, HomeActivity.class);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(LoginToHomeIntent);
        }

        setContentView(R.layout.login_activity);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        //setupViews
        emailEdit = (EditText)findViewById(R.id.emailLogin);
        passwordEdit = (EditText)findViewById(R.id.passwordLogin);
        title = (TextView)findViewById(R.id.loginTitle);
        subtitle = (TextView)findViewById(R.id.loginSubtitle);
        loginButton = (FloatingActionButton)findViewById(R.id.loginButton);
        registerButton = (FloatingActionButton)findViewById(R.id.registerButton);
        background = (ImageView)findViewById(R.id.backgroundImage);
        logo = (ImageView)findViewById(R.id.logoImage);
        backdrop = (ImageView)findViewById(R.id.backdropImage);

        //setupButtons
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onResume(){
        super.onResume();
        emailEdit.setText(null);
        passwordEdit.setText(null);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void createAccount(String email, String password){
        if (!credentialsAreComplete()){
            return;
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, R.string.create_failed,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, R.string.create_success,
                                        Toast.LENGTH_SHORT).show();
                                sendVerificationEmail();

                            }
                        }
                    });
        }
    }

    public void signIn(String email, String password){
        if (!credentialsAreComplete()){
            return;
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if (!isVerified()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, R.string.login_verify_failed,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                fetchFirebaseUser();
                                startActivity(LoginToHomeIntent);
                            }
                        }
                    });
        }
    }

    public void fetchFirebaseUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        user.getToken(true)
//                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
//                                           public void onComplete(@NonNull Task<GetTokenResult> task) {
//                                               if (task.isSuccessful()) {
//                                                   String idToken = task.getResult().getToken();
//                                                   // Send token to your backend via HTTPS
//                                                   // ...
//                                               } else {
//                                                   // Handle error -> task.getException();
//                                               }
//                                           }
//                                       });
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            //user.getToken() instead.
            String uid = user.getUid();
        }
    }

    @Override
    public void onClick(View v) {
        int clickedItem = v.getId();
        switch (clickedItem) {
            case R.id.loginButton:
                //startActivity(LoginToHomeIntent);
                signIn(emailEdit.getText().toString(), passwordEdit.getText().toString());
                break;
            case R.id.registerButton:
                createAccount(emailEdit.getText().toString(), passwordEdit.getText().toString());
                break;
            default:
                break;
            }
    }

    private boolean credentialsAreComplete() {
        boolean valid = true;
            String email = emailEdit.getText().toString();
            if (email.isEmpty()) {
                emailEdit.setError("Required.");
                valid = false;
            } else {
                emailEdit.setError(null);
            }
            String password = passwordEdit.getText().toString();
            if (password.isEmpty()) {
                passwordEdit.setError("Required.");
                valid = false;
            } else {
                passwordEdit.setError(null);
            }
        return valid;
    }


    private boolean isVerified(){
        boolean status = false;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.isEmailVerified()){
            status = true;
        }

        return status;
    }

    private void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(LoginActivity.this, R.string.email_sent,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, R.string.email_error,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
