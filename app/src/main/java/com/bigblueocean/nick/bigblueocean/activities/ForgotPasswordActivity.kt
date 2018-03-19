package com.bigblueocean.nick.bigblueocean.activities

import android.app.Activity
import android.os.Bundle
import com.bigblueocean.nick.bigblueocean.R
import kotlinx.android.synthetic.main.activity_forgot_password.*
import com.google.firebase.auth.*

/**
 * Created by nick on 3/12/18.
 */

class ForgotPasswordActivity : Activity() {
    private val loginAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        forgot_button.setOnClickListener {
            forgotPassword(email_field.text.toString())
        }

    }

    fun forgotPassword(email: String){
        //loginAuth.sendPasswordResetEmail(email)
    }
}