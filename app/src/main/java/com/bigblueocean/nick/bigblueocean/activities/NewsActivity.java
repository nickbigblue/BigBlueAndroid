package com.bigblueocean.nick.bigblueocean.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigblueocean.nick.bigblueocean.model.News;
import com.bigblueocean.nick.bigblueocean.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        String newsID;
        News newsPage;

        if (getIntent().hasExtra("newsID")) {
            newsID = getIntent().getStringExtra("newsID");
        } else {
            newsID = "fail";
            throw new IllegalArgumentException("Activity cannot find  extras: "+newsID);
        }

        if (getIntent().hasExtra("selectedNews")){
            String json = getIntent().getExtras().getString("selectedNews");
            newsPage = new Gson().fromJson(json, News.class);
        } else {
            newsPage = new News("Default News","Default Content", R.drawable.newsgenericimage, 0);
//            throw new IllegalArgumentException("Activity cannot find extras: 'selectedNews'");
        }

        ImageView banner = (ImageView) findViewById(R.id.news_page_image);
        if (!(newsPage.getImage() == null))
            Picasso.with(this).load(newsPage.getImage()).into(banner);
        else
            banner.setImageResource(newsPage.getImageID());

        TextView title = (TextView) findViewById(R.id.news_page_title);
        title.setText(newsPage.getTitle());

        TextView body = (TextView) findViewById(R.id.news_page_body);
        body.setText(newsPage.getContent());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            body.setText(Html.fromHtml(newsPage.getContent(),Html.FROM_HTML_MODE_LEGACY));
        } else {
            body.setText(Html.fromHtml(newsPage.getContent()));
        }
    }


    @Override
    public void onBackPressed() {
        NewsActivity.this.finish();
        Intent intent = new Intent(NewsActivity.this,HomeActivity.class);
        intent.putExtra("currentItem","0");
        startActivity(intent);
    }


}
