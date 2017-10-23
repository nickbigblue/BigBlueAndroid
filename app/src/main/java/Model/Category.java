package Model;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;


import com.bigblueocean.nick.bigblueocean.R;


public class Category {
    private String title;
    private String imageName;
    public int color;
    public Context context;

    public Category(String title, String imageName, int color){
        this.title = title;
        this.imageName = imageName;
        this.color = color;
    }

    public Category[] categories = {
            new Category("Tuna H&G Wild-Caught","tuna.png", ContextCompat.getColor(context,R.color.tuna)),
            new Category("Sword H&G Wild-Caught","sword.png", ContextCompat.getColor(context,R.color.sword)),
            new Category("Mahi H&G Wild-Caught","mahi.png", ContextCompat.getColor(context,R.color.mahi)),
            new Category("Waho H&G Wild-Caught","wahoo.png", ContextCompat.getColor(context,R.color.wahoo)),
            new Category("Grouper H&G Wild-Caught","grouper.png", ContextCompat.getColor(context,R.color.grouper)),
            new Category("Salmon H&G Wild-Caught","salmon.png", ContextCompat.getColor(context,R.color.salmon))
    };
}
