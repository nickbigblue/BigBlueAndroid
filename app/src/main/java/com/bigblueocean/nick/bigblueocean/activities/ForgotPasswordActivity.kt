package com.bigblueocean.nick.bigblueocean.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.bigblueocean.nick.bigblueocean.R
import com.bigblueocean.nick.bigblueocean.helpers.SweetAlertHelper
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.*
import java.util.logging.ErrorManager

/**
 * Created by nick on 3/12/18.
 */
class ForgotPasswordActivity : Activity() {
    private val loginAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {
            signIn(login_email_field.text.toString(), login_password_field.text.toString())
        }

        register_button.setOnClickListener { startActivity(Intent() ) }

        forgot_password_button.setOnClickListener { startActivity(Intent()) }
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