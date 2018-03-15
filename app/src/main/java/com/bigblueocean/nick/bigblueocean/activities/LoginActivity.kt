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
    private val regIntent = Intent(this, RegisterActivity::class.java)
    private val forgotIntent = Intent(this, ForgotPasswordActivity::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {
            signIn(login_email_field.text.toString(), login_password_field.text.toString())
        }

        register_button.setOnClickListener { startActivity(Intent(regIntent) ) }

        forgot_password_button.setOnClickListener { startActivity(Intent(forgotIntent)) }
    }

    fun signIn(email: String, password: String){
        loginAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            when (it.isComplete) {
                it.isSuccessful -> println("success")
                !it.isSuccessful -> println("failure")
            }
        }
    }
}