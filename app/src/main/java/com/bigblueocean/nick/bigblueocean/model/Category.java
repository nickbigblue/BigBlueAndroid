package com.bigblueocean.nick.bigblueocean.model;

import com.bigblueocean.nick.bigblueocean.R;

/**
 * Created by nick on 11/27/17.
 */

public enum Category {
    TUNA ("Tuna H&G Wild-Caught", R.drawable.tuna, "#ba052b", "TUNA"),
    SWORD   ("Sword H&G Wild-Caught", R.drawable.sword, "#6b7380", "SWORD"),
    MAHI   ("Mahi H&G Wild-Caught", R.drawable.mahi, "#78b82c", "MAHI"),
    WAHOO    ("Wahoo H&G Wild-Caught", R.drawable.wahoo, "#2e78a1", "WAHOO"),
    GROUPER ("Grouper H&G Wild-Caught", R.drawable.grouper, "#c24f0a", "GROUPER"),
    SALMON  ("Salmon H&G Wild-Caught", R.drawable.tuna, "#fcb0ed", "SALMON"),
    OTHER  ("Other", R.drawable.tuna, "#8e699e", "OTHER");

    private final String title;
    private final int imageId;
    private final String color;
    private final String tag;

    Category(String title, int imageId, String color, String tag){
        this.title = title;
        this.imageId = imageId;
        this.color = color;
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public String getColor() {
        return color;
    }

    public String getTag() {
        return tag;
    }
}
