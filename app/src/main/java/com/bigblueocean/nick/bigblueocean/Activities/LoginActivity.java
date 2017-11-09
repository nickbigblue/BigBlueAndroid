package com.bigblueocean.nick.bigblueocean.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigblueocean.nick.bigblueocean.Helpers.FontHelper;
import com.bigblueocean.nick.bigblueocean.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends Activity implements View.OnClickListener {

    private FirebaseAuth loginAuthenticator;
    private FirebaseAuth.AuthStateListener loginAuthListener;
    private EditText emailEdit;
    private EditText passwordEdit;
    private TextView title;
    private Button loginButton;
    private Button registerButton;
    private Button forgotPasswordButton;
    private LinearLayout backdrop;


    //METHODS FOR ACTIVITY LIFE CYCLE//////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        loginAuthenticator = FirebaseAuth.getInstance();
        loginAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
            }
        };

        //setupViews
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        title = findViewById(R.id.loginTitle);

        //setupButtons
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        forgotPasswordButton.setOnClickListener(this);
        title.setTypeface(FontHelper.antonTypeface(this));


        backdrop = findViewById(R.id.sub_container);
        backdrop.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        loginAuthenticator.addAuthStateListener(loginAuthListener);

    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (loginAuthListener != null) {
            loginAuthenticator.removeAuthStateListener(loginAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        int clickedItem = v.getId();
        if (hasWindowFocus()){
            switch (clickedItem) {
                case R.id.loginButton:
                    generateDialog(R.id.loginButton).show();
                    break;
                case R.id.registerButton:
                    generateDialog(R.id.registerButton).show();
                    break;
                case R.id.forgotPasswordButton:
                    generateDialog(R.id.forgotPasswordButton).show();
                    break;
                default:
                    break;
            }
        }
        else {
            return;
        }
    }

    //METHODS FOR GENERATING LOGIN INPUT DIALOGS

    public Dialog generateDialog(int id){
        final Dialog input = new Dialog(LoginActivity.this);
        int width = 0;
        int height = 0;
        Button positive;
        Button negative = new Button(input.getContext());

        switch(id){
            case R.id.loginButton:
                input.setContentView(R.layout.login_dialog);
                emailEdit = input.findViewById(R.id.emailLogin);
                passwordEdit = input.findViewById(R.id.passwordLogin);
                positive = input.findViewById(R.id.dialog_positive);
                negative = input.findViewById(R.id.dialog_negative);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signIn(emailEdit.getText().toString(), passwordEdit.getText().toString());
                        input.cancel();
                    }
                });
                break;

            case R.id.registerButton:
                input.setContentView(R.layout.register_dialog);
                emailEdit = input.findViewById(R.id.emailLogin);
                passwordEdit = input.findViewById(R.id.passwordLogin);
                EditText companyName = input.findViewById(R.id.register_company_name);
                EditText phoneNum = input.findViewById(R.id.register_phone);
                positive = input.findViewById(R.id.dialog_positive);
                negative = input.findViewById(R.id.dialog_negative);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createAccount(emailEdit.getText().toString(), passwordEdit.getText().toString());
                        input.cancel();
                    }
                });
                break;

            case R.id.forgotPasswordButton:
                input.setContentView(R.layout.forgot_password_dialog);
                emailEdit = input.findViewById(R.id.emailLogin);
                positive = input.findViewById(R.id.dialog_positive);
                negative = input.findViewById(R.id.dialog_negative);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        forgotPassword(emailEdit.getText().toString());
                        input.cancel();
                    }
                });
                break;
        }
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.cancel();
            }
        });

        width = (int)(getResources().getDisplayMetrics().widthPixels*0.999);
        height = LinearLayout.LayoutParams.WRAP_CONTENT;
        input.getWindow().setLayout(width, height);
        return input;
    }

    //METHODS FOR FIREBASE CONNECTIONS//////////////////////////////

    public void createAccount(String email, String password){
        final String emailString = email;
        if (!credentialsAreComplete()){
            return;
        }
        else if (!existsInBigBlueDatabase(email)){
            Toast.makeText(LoginActivity.this, R.string.nonexistent_email,
                    Toast.LENGTH_LONG).show();
            return;
        }
        else {
            loginAuthenticator.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, R.string.create_failed,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, R.string.create_success+emailString,
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
            loginAuthenticator.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if (!isVerified()) {
                                Toast.makeText(LoginActivity.this, R.string.login_verify_failed,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                        }
                    });
        }
    }

    public void forgotPassword(String email){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email);
    }

    private boolean credentialsAreComplete() {
        boolean valid = false;

        String email = emailEdit.getText().toString();
        if (email.isEmpty()) {
            emailEdit.setError("Required.");
        }
        else if(!email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")){
            emailEdit.setError("Not a valid address.");
        }
        else {
            emailEdit.setError(null);
            valid = true;
        }
        String password = passwordEdit.getText().toString();
        if (password.isEmpty()) {
            passwordEdit.setError("Required.");
        }
        else if(!password.matches("(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,}")){
            passwordEdit.setError("Weak password.");
            Toast.makeText(LoginActivity.this,
                    "Password must contain: " +
                            "\n  (6) or more total characters." +
                            "\n  (1) or more digits." +
                            "\n  (0) occurrences of spaces.", Toast.LENGTH_LONG).show();
        }
        else {
            passwordEdit.setError(null);
            valid = true;
        }
        return valid;
    }

    private boolean isVerified(){
        boolean status = false;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.isEmailVerified()){
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

    private boolean existsInBigBlueDatabase(String email){
        boolean valid = false;
        ///QUERY JASON'S DATABASE FOR THIS EMAIL, IF IT EXISTS SET VALID = TRUE
        ///
        ///
        ///
        ///
        return valid;
    }

}
