package com.bigblueocean.nick.bigblueocean;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class HelpActivity extends AppCompatActivity {
    private TextView HelpContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.help_activity);

        HelpContentView = (TextView) findViewById(R.id.help_title);
        HelpContentView.setTypeface(Helper.impactTypeface(this));

    }



    @Override
    public void onBackPressed() {
        this.finish();
        startActivity(new Intent(this, HomeActivity.class));
    }

}
