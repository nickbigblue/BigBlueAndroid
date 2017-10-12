package Model;

import android.graphics.Color;

/**
 * Created by nick on 10/10/17.
 */

public class Category {
    private String title;
    private String imageName;
    public Color color;

    public Category(String title, String imageName, Color color){
        this.title = title;
        this.imageName = imageName;
        this.color = color;
    }

}
