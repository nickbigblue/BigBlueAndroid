package com.bigblueocean.nick.bigblueocean.Model;

import android.graphics.Bitmap;


public class Category {
    private String title;
    private Bitmap image;
    private int color;


    public Category(String title, Bitmap image, int color){
        this.title = title;
        this.image = image;
        this.color = color;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }
}
