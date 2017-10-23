package Model;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;


import com.bigblueocean.nick.bigblueocean.ChatFragment;
import com.bigblueocean.nick.bigblueocean.R;

import java.util.ArrayList;


public class Category {
    public String title;
    public Bitmap image;
    public Color color;
    public Context context = ;


    public Category(String title, Bitmap image, Color color){
        this.title = title;
        this.image = image;
        this.color = color;
    }

    public Category(){

    }

    public ArrayList<Category> categories() {
            ArrayList<Category> CAL= new ArrayList<Category>();
            CAL.add(new Category("Tuna H&G Wild-Caught",BitmapFactory.decodeResource(context.getResources(), R.drawable.tuna), Color.valueOf(R.color.tuna)));
            CAL.add(new Category("Sword H&G Wild-Caught",BitmapFactory.decodeResource(context.getResources(), R.drawable.sword), Color.valueOf(R.color.sword)));
            CAL.add(new Category("Mahi H&G Wild-Caught",BitmapFactory.decodeResource(context.getResources(), R.drawable.mahi), Color.valueOf(R.color.mahi)));
            CAL.add(new Category("Wahoo H&G Wild-Caught",BitmapFactory.decodeResource(context.getResources(), R.drawable.wahoo), Color.valueOf(R.color.wahoo)));
            CAL.add(new Category("Grouper H&G Wild-Caught",BitmapFactory.decodeResource(context.getResources(), R.drawable.grouper), Color.valueOf(R.color.grouper)));
            CAL.add(new Category("Salmon H&G Wild-Caught",BitmapFactory.decodeResource(context.getResources(), R.drawable.tuna), Color.valueOf(R.color.salmon)));
        return CAL;
    }
}
