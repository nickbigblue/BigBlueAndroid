package com.bigblueocean.nick.bigblueocean.Model;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.bigblueocean.nick.bigblueocean.R;

/**
 * Created by nick on 10/10/17.
 */

public class Detail {
    private String title;
    private String imageName;
    private int color;
    private Context context;

    public Detail(String title, String imageName, int color){
        this.title = title;
        this.imageName = imageName;
        this.color = color;
    }

    public Detail[] details = {
            new Detail("Tuna H&G Wild-Caught","tuna.png",
            ContextCompat.getColor(context, R.color.tuna)),

            new Detail("Sword H&G Wild-Caught","sword.png",
            ContextCompat.getColor(context,R.color.sword)),

            new Detail("Mahi H&G Wild-Caught","mahi.png",
            ContextCompat.getColor(context,R.color.mahi)),

            new Detail("Waho H&G Wild-Caught","wahoo.png",
            ContextCompat.getColor(context,R.color.wahoo)),

            new Detail("Grouper H&G Wild-Caught","grouper.png",
            ContextCompat.getColor(context,R.color.grouper)),

            new Detail("Salmon H&G Wild-Caught","salmon.png",
            ContextCompat.getColor(context,R.color.salmon))
    };
}
