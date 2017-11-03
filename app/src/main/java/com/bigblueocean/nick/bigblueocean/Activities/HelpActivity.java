package com.bigblueocean.nick.bigblueocean.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bigblueocean.nick.bigblueocean.Helpers.FontHelper;
import com.bigblueocean.nick.bigblueocean.R;

public class HelpActivity extends AppCompatActivity {
    private TextView HelpContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.help_activity);

        HelpContentView = (TextView) findViewById(R.id.help_title);
        HelpContentView.setTypeface(FontHelper.antonTypeface(this));

    }



    @Override
    public void onBackPressed() {
        this.finish();
        startActivity(new Intent(this, HomeActivity.class));
    }

}
