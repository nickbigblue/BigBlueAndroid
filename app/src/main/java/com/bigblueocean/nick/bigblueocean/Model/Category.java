package com.bigblueocean.nick.bigblueocean.Model;

import android.graphics.Bitmap;


public class Category {
    private String title;
    private int imageId;
    private int color;
    private String tag;


    public Category(String title, int image, int color, String tag){
        this.title = title;
        this.imageId = image;
        this.color = color;
        this.tag = tag;
    }

    public int getImage() {
        return imageId;
    }

    public int getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    public String getTag() {
        return tag;
    }
}
