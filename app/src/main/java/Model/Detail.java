package Model;

import android.graphics.Color;

/**
 * Created by nick on 10/10/17.
 */

public class Detail {
    private String title;
    private String imageName;
    private String itemDesc;
    private Color color;

    public Detail(String title, String imageName, String itemDesc, Color color){
        this.title = title;
        this.imageName = imageName;
        this.itemDesc = itemDesc;
        this.color = color;
    }
}
