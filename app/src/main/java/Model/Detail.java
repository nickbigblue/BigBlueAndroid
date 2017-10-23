package Model;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.bigblueocean.nick.bigblueocean.R;

/**
 * Created by nick on 10/10/17.
 */

public class Detail {
    private String title;
    private String imageName;
    private String itemDesc;
    private int color;
    private Context context;

    public Detail(String title, String imageName, String itemDesc, int color){
        this.title = title;
        this.imageName = imageName;
        this.itemDesc = itemDesc;
        this.color = color;
    }

    public Detail[] details = {
            new Detail("Tuna H&G Wild-Caught","tuna.png",
            "Big Blue Tuna is caught from a wide variety of orgins around the world.  Please select your specfic needs for your Tuna today.", ContextCompat.getColor(context, R.color.tuna)),

            new Detail("Sword H&G Wild-Caught","sword.png",
            "Big Blue Sword is caught from a wide variety of orgins around the world.  Please select your specfic needs for your Sword today.", ContextCompat.getColor(context,R.color.sword)),

            new Detail("Mahi H&G Wild-Caught","mahi.png",
            "Big Blue Mahi is caught from a wide variety of orgins around the world.  Please select your specfic needs for your Mahi today.", ContextCompat.getColor(context,R.color.mahi)),

            new Detail("Waho H&G Wild-Caught","wahoo.png",
            "Big Blue Wahoo is caught from a wide variety of orgins around the world.  Please select your specfic needs for your Wahoo today.", ContextCompat.getColor(context,R.color.wahoo)),

            new Detail("Grouper H&G Wild-Caught","grouper.png",
            "Big Blue Grouper is caught from a wide variety of orgins around the world.  Please select your specfic needs for your Grouper today.", ContextCompat.getColor(context,R.color.grouper)),

            new Detail("Salmon H&G Wild-Caught","salmon.png",
            "Big Blue Salmon is caught from a wide variety of orgins around the world.  Please select your specfic needs for your Salmon today.", ContextCompat.getColor(context,R.color.salmon))
    };
}
