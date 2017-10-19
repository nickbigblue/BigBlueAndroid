package com.bigblueocean.nick.bigblueocean;
import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by nick on 10/19/17.
 */

public class Helper {

    public static Typeface impactTypeface(Context context){
        return Typeface.createFromAsset(context.getAssets(), "font/impact.ttf");
    }

}
