package com.bigblueocean.nick.bigblueocean.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bigblueocean.nick.bigblueocean.R;

public class NewsActivity extends AppCompatActivity {
    private String newsID;
    private String dataSourceURL = "http://bigblueocean.net/jb2/public/bigblueapp/news";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        if (getIntent().hasExtra("newsID")) {
            newsID = getIntent().getStringExtra("newsID");
        } else {
            newsID = "fail";
            throw new IllegalArgumentException("Activity cannot find  extras: "+newsID);
        }


        TextView title = (TextView) findViewById(R.id.news_page_title);
        title.setText("You passed this news item: "+newsID);
        //Call function to website to get data
    }

    @Override
    public void onBackPressed() {
        NewsActivity.this.finish();
        Intent intent = new Intent(NewsActivity.this,HomeActivity.class);
        intent.putExtra("currentItem","0");
        startActivity(intent);
    }
}
