package com.bigblueocean.nick.bigblueocean.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigblueocean.nick.bigblueocean.helpers.ServerPost;
import com.bigblueocean.nick.bigblueocean.helpers.SweetAlertHelper;
import com.bigblueocean.nick.bigblueocean.model.User;
import com.bigblueocean.nick.bigblueocean.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends Activity implements View.OnClickListener {

    private FirebaseAuth loginAuthenticator;
    private FirebaseAuth.AuthStateListener loginAuthListener;
    private SweetAlertHelper sweetBuilder = new SweetAlertHelper();
    private EditText emailEdit;
    private EditText passwordEdit;
    private EditText userName;
    private EditText companyName;
    private EditText phoneNum;
    private TextView title;
    private Button loginButton;
    private Button registerButton;
    private Button forgotPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginAuthenticator = FirebaseAuth.getInstance();
        loginAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (isVerified()) {
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
    }

    @Override
    public void onStart() {
        super.onStart();
        loginAuthenticator.addAuthStateListener(loginAuthListener);
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

    public Dialog generateDialog(int id){
        final Dialog input = new Dialog(LoginActivity.this);
        Button positive;
        Button negative = new Button(input.getContext());

        switch(id){
            case R.id.loginButton:
                input.setContentView(R.layout.dialog_login);
                emailEdit = input.findViewById(R.id.emailLogin);
                passwordEdit = input.findViewById(R.id.passwordLogin);
                positive = input.findViewById(R.id.dialog_positive);
                negative = input.findViewById(R.id.dialog_negative);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (loginCredentialsAreComplete()) {
                            signIn(emailEdit.getText().toString(), passwordEdit.getText().toString());
                            input.cancel();
                        }
                    }
                });
                break;

            case R.id.registerButton:
                input.setContentView(R.layout.dialog_register);
                emailEdit = input.findViewById(R.id.emailLogin);
                passwordEdit = input.findViewById(R.id.passwordLogin);
                userName = input.findViewById(R.id.register_person);
                companyName = input.findViewById(R.id.register_company_name);
                phoneNum = input.findViewById(R.id.register_phone);
                positive = input.findViewById(R.id.dialog_positive);
                negative = input.findViewById(R.id.dialog_negative);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (registerCredentialsAreComplete()) {
                            createAccount(emailEdit.getText().toString(), passwordEdit.getText().toString());
                            input.cancel();
                        }
                    }
                });
                break;

            case R.id.forgotPasswordButton:
                input.setContentView(R.layout.dialog_forgot_password);
                emailEdit = input.findViewById(R.id.emailLogin);
                positive = input.findViewById(R.id.dialog_positive);
                negative = input.findViewById(R.id.dialog_negative);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (forgotCredentialsAreComplete()) {
                            forgotPassword(emailEdit.getText().toString());
                            input.cancel();
                        }
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

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.999);
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        input.getWindow().setLayout(width, height);
        return input;
    }

    public void createAccount(String email, String password) {
        final String emailString = email;
        if (!registerCredentialsAreComplete()) { return; }
        else if (!existsInBigBlueDatabase(email)){
            sweetBuilder
                    .createSweetDialog(LoginActivity.this, "Error",
                            "Non-existent Address", R.string.nonexistent_email, "Ok")
                    .show();
        }
        else {
            final SweetAlertDialog creationLoadingDialog = sweetBuilder.createProgressSweetDialog(LoginActivity.this);
            creationLoadingDialog.show();
            loginAuthenticator.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    creationLoadingDialog.dismissWithAnimation();
                    sendVerificationEmail();
                    User currentUser = new User(
                            0,
                            companyName.getText().toString(),
                            userName.getText().toString(),
                            phoneNum.getText().toString(),
                            emailEdit.getText().toString(),
                            loginAuthenticator.getCurrentUser().getUid(),
                            "0",
                            0);
                    ServerPost sp = new ServerPost(loginAuthenticator.getCurrentUser());
                    if (!sp.createUser(currentUser)) {
                        sweetBuilder
                                .createSweetDialog(LoginActivity.this,
                                        "Success",
                                        "Account Created",
                                        getResources().getString(R.string.create_success) + " " + emailString,
                                        "Ok")
                                .show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    creationLoadingDialog.dismissWithAnimation();
                    sweetBuilder
                            .createSweetDialog(LoginActivity.this, "Error",
                                    "Register Failed", e.getMessage(), "Ok")
                            .show();
                }
            });
        }
    }

    public void signIn(String email, String password){
        if (!loginCredentialsAreComplete()){
            return;
        }
        else {
            final SweetAlertDialog loadingDialog = sweetBuilder.createProgressSweetDialog(LoginActivity.this);
            loadingDialog.show();

            loginAuthenticator.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                public void onSuccess(AuthResult authResult) {
                    loadingDialog.dismissWithAnimation();
                    if (!isVerified()) {
                        sweetBuilder
                                .createSweetDialog(LoginActivity.this, "Error",
                                        "Email Verification Failed", R.string.login_verify_failed, "Ok")
                                .show();
                        loginAuthenticator.signOut();
                    }
                    else {
                        ServerPost sp = new ServerPost(loginAuthenticator.getCurrentUser());
                        User currentUser = sp.setUser();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loadingDialog.dismissWithAnimation();
                    sweetBuilder
                            .createSweetDialog(LoginActivity.this, "Error",
                                    "Log In Failed", e.getMessage(), "Ok")
                            .show();
                }
            });
        }
    }

    public void forgotPassword(String email){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                sweetBuilder
                        .createSweetDialog(LoginActivity.this, "Success",
                                "Email Sent", R.string.password_reset_sent, "Ok")
                        .show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                sweetBuilder
                        .createSweetDialog(LoginActivity.this, "Error",
                                "Email Not Sent", e.getMessage(), "Ok")
                        .show();
            }
        });
    }

    private boolean registerCredentialsAreComplete() {
        boolean valid;

        String email = emailEdit.getText().toString();
        if (email.isEmpty()) {
            emailEdit.setError("Required.");
            valid = false;
        }
        else if(!email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")){
            emailEdit.setError("Not a valid address.");
            valid = false;
        }
        else {
            emailEdit.setError(null);
            valid = true;
        }
        String password = passwordEdit.getText().toString();
        if (password.isEmpty()) {
            passwordEdit.setError("Required.");
            valid = false;
        }
        else if(!password.matches("(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,}")){
            passwordEdit.setError("Weak password.");
            valid = false;
        }
        else {
            passwordEdit.setError(null);
            valid = true;
        }

        String company = companyName.getText().toString();
        if (company.isEmpty()) {
            companyName.setError("Required.");
            valid = false;
        }
        else if (companyName.length()<4){
            companyName.setError("Company invalid.");
            valid = false;
        }
        else{
            companyName.setError(null);
            valid = true;
        }
        
        String phone = phoneNum.getText().toString();
        if (phone.isEmpty()) {
            phoneNum.setError("Required.");
            valid = false;
        }
        else if (phone.length()>10){
            phoneNum.setError("Phone Invalid.");
            valid = false;
        }
        else{
            phoneNum.setError(null);
            valid = true;
        }

        return valid;
    }

    private boolean loginCredentialsAreComplete(){
        boolean valid;
        String email = emailEdit.getText().toString();
        if (email.isEmpty()) {
            emailEdit.setError("Required.");
            valid = false;
        } else if(!email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")){
            emailEdit.setError("Not a valid address.");
            valid = false;
        }
        else
            valid = true;

        String password = passwordEdit.getText().toString();
        if (password.isEmpty()) {
            passwordEdit.setError("Required.");
            valid = false;
        }
        else
            valid = true;

        return valid;
    }

    private boolean forgotCredentialsAreComplete(){
        boolean valid = false;
        String email = emailEdit.getText().toString();
        if (email.isEmpty()){
            emailEdit.setError("Required.");
        }
        else
            valid = true;

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
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        sweetBuilder
                                .createSweetDialog(LoginActivity.this, "Success",
                                        "Verify Email", R.string.email_sent, "Ok")
                                .show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        sweetBuilder
                                .createSweetDialog(LoginActivity.this, "Error",
                                        "Verify Email", e.getMessage(), "Ok")
                                .show();
                    }
                });
    }

    private boolean existsInBigBlueDatabase(String email){
        //change to false
        boolean valid = true;
        ///QUERY JASON'S DATABASE FOR THIS EMAIL, IF IT EXISTS SET VALID = TRUE
        ///
        ///
        ///
        ///
        return valid;
    }

}
