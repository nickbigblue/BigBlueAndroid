package com.bigblueocean.nick.bigblueocean.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.bigblueocean.nick.bigblueocean.R
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.*

/**
* Created by nick on 3/12/18.
*/

class LoginActivity : Activity() {

    private val loginAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val regIntent = Intent(this, RegisterActivity::class.java)
        val forgotIntent = Intent(this, ForgotPasswordActivity::class.java)

        login_button.setOnClickListener {
            if (loginCredentialsAreComplete()) {
                signIn(login_email_field.text.toString(), login_password_field.text.toString())
            } else{
                login_email_field.error = "Invalid!"
                login_password_field.error = "Invalid!"
                displayErrorMessage("Invalid credentials")
            }
        }

        register_button.setOnClickListener { startActivity(Intent(regIntent) ) }

        forgot_password_button.setOnClickListener { startActivity(Intent(forgotIntent)) }
    }

    private fun signIn(email: String, password: String){
            loginAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                when (it.isComplete) {
                    it.isSuccessful -> if (checksVerification(email, password)) {
                        val homeIntent = Intent(this, HomeActivity::class.java)
                        startActivity(homeIntent)
                    }
                    !it.isSuccessful -> if (it.exception != null) {
                        val message = it.exception?.localizedMessage
                        if (message != null) {
                            displayErrorMessage(message)
                        }
                    }
                }
            }
    }

    private fun loginCredentialsAreComplete(): Boolean {
        val email = login_email_field.text.toString()
        val pass = login_password_field.text.toString()

        if (email.isNotEmpty() and pass.isNotEmpty()){
            if(email.contains('@') and email.contains('.')){
                return true
            }
        }

        return false
    }

    private fun existsInBigBlueDatabase(email: String, password: String): Boolean {
        val serializationArray = arrayOf(email, password)
        print(serializationArray)
        return true
    }

    private fun checksVerification(email: String, password: String): Boolean {
        val currentUser = loginAuth.currentUser
        if (currentUser != null) {
            if (existsInBigBlueDatabase(email, password) and currentUser.isEmailVerified){
                return true
            }
        } else {
            displayErrorMessage("Failed to verify user.")
        }
        return false
    }

    private fun displayErrorMessage(message: String) {
        print(message)
    }
}