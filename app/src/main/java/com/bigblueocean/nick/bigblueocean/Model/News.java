package com.bigblueocean.nick.bigblueocean.Model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nick on 10/10/17.
 */

public class News {
    private String title;
    private String content;
    private int newsID;
    private String imageURL;

    public News(String title, String content, String imageName, int newsID){
        this.title = title;
        this.content = content;
        this.imageURL = imageName;
        this.newsID = newsID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getImage() {
        return imageURL;
    }

    public void setImage(String image) {
        this.imageURL = image;
    }

}
