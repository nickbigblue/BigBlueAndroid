package com.bigblueocean.nick.bigblueocean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
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
