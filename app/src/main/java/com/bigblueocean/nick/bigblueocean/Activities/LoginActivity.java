package com.bigblueocean.nick.bigblueocean.Activities;

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
import android.widget.Toast;

import com.bigblueocean.nick.bigblueocean.Helpers.FontHelper;
import com.bigblueocean.nick.bigblueocean.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;


public class LoginActivity extends Activity implements View.OnClickListener {

    private FirebaseAuth loginAuthenticator;
    private FirebaseAuth.AuthStateListener loginAuthListener;
    private EditText emailEdit;
    private EditText passwordEdit;
    private EditText companyName;
    private EditText phoneNum;
    private TextView title;
    private Button loginButton;
    private Button registerButton;
    private Button forgotPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
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
        title.setTypeface(FontHelper.antonTypeface(this));
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

    public Dialog generateDialog(int id){
        final Dialog input = new Dialog(LoginActivity.this);
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
                        if (loginCredentialsAreComplete()) {
                            signIn(emailEdit.getText().toString(), passwordEdit.getText().toString());
                            input.cancel();
                        }
                    }
                });
                break;

            case R.id.registerButton:
                input.setContentView(R.layout.register_dialog);
                emailEdit = input.findViewById(R.id.emailLogin);
                passwordEdit = input.findViewById(R.id.passwordLogin);
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
                input.setContentView(R.layout.forgot_password_dialog);
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
        if (!registerCredentialsAreComplete()){
            return;
        }
        else if (!existsInBigBlueDatabase(email)){
            SweetAlertDialog existsEmailErrDialog =
            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
            existsEmailErrDialog.setTitleText("Non-existent Email Address");
            existsEmailErrDialog.setContentText(getResources().getString(R.string.nonexistent_email));
            existsEmailErrDialog.setConfirmText("OK");
            existsEmailErrDialog.show();
        }
        else {
            final SweetAlertDialog creationLoadingDialog =
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            creationLoadingDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
            creationLoadingDialog.setTitleText("Loading...");
            creationLoadingDialog.setCancelable(false);
            creationLoadingDialog.show();
            loginAuthenticator.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    creationLoadingDialog.dismissWithAnimation();
                    sendVerificationEmail();
                    SweetAlertDialog loginSuccessErrDialog =
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    loginSuccessErrDialog.setTitleText("Account Created");
                    loginSuccessErrDialog.setContentText(getResources().getString(R.string.create_success)+" "+emailString);
                    loginSuccessErrDialog.setConfirmText("OK");
                    loginSuccessErrDialog.show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    creationLoadingDialog.dismissWithAnimation();
                    SweetAlertDialog loginSuccessErrDialog =
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                    loginSuccessErrDialog.setTitleText("Log In Failed");
                    loginSuccessErrDialog.setContentText(e.getMessage());
                    loginSuccessErrDialog.setConfirmText("OK");
                    loginSuccessErrDialog.show();
                }
            });
        }
    }

    public void signIn(String email, String password){
        if (!loginCredentialsAreComplete()){
            return;
        }
        else {
            final SweetAlertDialog loginLoadingDialog =
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            loginLoadingDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
            loginLoadingDialog.setTitleText("Loading...");
            loginLoadingDialog.setCancelable(false);
            loginLoadingDialog.show();

            loginAuthenticator.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                public void onSuccess(AuthResult authResult) {
                    if (!isVerified()) {
                        loginLoadingDialog.dismissWithAnimation();
                        SweetAlertDialog loginVerifiedErrDialog =
                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                        loginVerifiedErrDialog.setTitleText("Email Verification Failed");
                        loginVerifiedErrDialog.setContentText(getResources().getString(R.string.login_verify_failed));
                        loginVerifiedErrDialog.setConfirmText("OK");
                        loginVerifiedErrDialog.show();
                        loginAuthenticator.signOut();
                    }
                    else {
                        loginLoadingDialog.dismissWithAnimation();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loginLoadingDialog.dismissWithAnimation();
                    SweetAlertDialog loginSuccessErrDialog =
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                    loginSuccessErrDialog.setTitleText("Log In Failed");
                    loginSuccessErrDialog.setContentText(e.getMessage());
                    loginSuccessErrDialog.setConfirmText("OK");
                    loginSuccessErrDialog.show();
                }
            });
        }
    }

    public void forgotPassword(String email){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                SweetAlertDialog forgotPassEmailSentDialog =
                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                forgotPassEmailSentDialog.setTitleText("Email Sent");
                forgotPassEmailSentDialog.setContentText(getResources().getString(R.string.password_reset_sent));
                forgotPassEmailSentDialog.setConfirmText("OK");
                forgotPassEmailSentDialog.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                SweetAlertDialog forgotPassEmailErrDialog =
                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                forgotPassEmailErrDialog.setTitleText("Email Not Sent");
                forgotPassEmailErrDialog.setContentText(e.getMessage());
                forgotPassEmailErrDialog.setConfirmText("OK");
                forgotPassEmailErrDialog.show();
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
            Toast.makeText(LoginActivity.this,
                    "Password must contain: " +
                            "\n  (6) or more total characters." +
                            "\n  (1) or more digits." +
                            "\n  (0) occurrences of spaces.", Toast.LENGTH_LONG).show();
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
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            SweetAlertDialog verifyConfirmDialog =
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                            verifyConfirmDialog.setTitleText("Verify Email");
                            verifyConfirmDialog.setContentText(getResources().getString(R.string.email_sent));
                            verifyConfirmDialog.setConfirmText("OK");
                            verifyConfirmDialog.show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        SweetAlertDialog verifyConfirmDialog =
                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                        verifyConfirmDialog.setTitleText("Verify Email");
                        verifyConfirmDialog.setContentText(e.getMessage());
                        verifyConfirmDialog.setConfirmText("OK");
                        verifyConfirmDialog.show();
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
