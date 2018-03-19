package com.bigblueocean.nick.bigblueocean.activities

import android.app.Activity
import android.os.Bundle
import com.bigblueocean.nick.bigblueocean.R
import kotlinx.android.synthetic.main.activity_register.*
import com.google.firebase.auth.*

/**
 * Created by nick on 3/12/18.
 */

class RegisterActivity : Activity() {
    private val loginAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_button.setOnClickListener {
            val email = register_email.text.toString()
            val password = register_password.text.toString()
            val name = register_individual_name.text.toString()
            val company = register_company_name.text.toString()
            val phone = register_phone.text.toString()

            register(email, password, name, company, phone)
        }

    }

    fun register(email: String, password: String, name: String, company: String, phone: String){
        loginAuth.createUserWithEmailAndPassword(email, password)
        //JSONPost(email, password, name, company, phone);
    }

    fun JSONPost() {

    }
}