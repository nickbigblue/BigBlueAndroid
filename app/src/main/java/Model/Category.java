package Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import com.bigblueocean.nick.bigblueocean.R;

import java.util.ArrayList;


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
