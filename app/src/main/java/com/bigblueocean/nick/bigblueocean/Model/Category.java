package com.bigblueocean.nick.bigblueocean.Model;

import com.bigblueocean.nick.bigblueocean.R;

public class Category {
    private String title;
    private int imageId;
    private String color;
    private String tag;


    public Category(String title, String tag){
        this.title =  title;
        this.imageId = R.drawable.tuna2x;
        this.color = "#ba052b";
        this.tag = tag;
    }

    public Category( String tag){
        this.tag = tag;
        setEverything(tag);
    }

    public int getImage() {
        return imageId;
    }

    public String getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    public String getTag() {
        return tag;
    }

    private void setEverything(String tag){
        switch (tag){
            case "Tuna":
                this.title = "Tuna H&G Wild-Caught";
                this.imageId = R.drawable.tuna2x;
                this.color = "#ba052b";
                break;
            case "Sword":
                this.title = "Sword H&G Wild-Caught";
                this.imageId = R.drawable.sword2x;
                this.color = "#6b7380";
                break;
            case "Mahi":
                this.title = "Mahi H&G Wild-Caught";
                this.imageId = R.drawable.mahi2x;
                this.color = "#78b82c";
                break;
            case "Wahoo":
                this.title = "Wahoo H&G Wild-Caught";
                this.imageId = R.drawable.wahoo2x;
                this.color = "#2e78a1";
                break;
            case "Grouper":
                this.title = "Grouper H&G Wild-Caught";
                this.imageId = R.drawable.grouper2x;
                this.color = "#c24f0a";
                break;
            case "Salmon":
                this.title = "Salmon Farm-Raised";
                this.imageId = R.drawable.sword2x;
                this.color = "#fcb0ed";
                break;
            default:
                this.title = "Other";
                this.imageId = R.drawable.tuna2x;
                this.color = "#ba052b";
                break;
        }
    }

}
