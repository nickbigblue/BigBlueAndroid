package com.bigblueocean.nick.bigblueocean.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.bigblueocean.nick.bigblueocean.R
import com.bigblueocean.nick.bigblueocean.helpers.SweetAlertHelper
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_register.*
import com.google.firebase.auth.*
import java.util.logging.ErrorManager

/**
 * Created by nick on 3/12/18.
 */
class RegisterActivity : Activity() {
    private val loginAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //register_button.setOnClickListener { } //register(email, password, name, company, phone) }

    }

    fun register(email: String, password: String, name: String, company: String, phone: String){
        loginAuth.createUserWithEmailAndPassword(email, password)
        //JSONPost(email, password, name, company, phone);
    }
}