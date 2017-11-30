package com.bigblueocean.nick.bigblueocean.helpers;
import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by nick on 10/19/17.
 */

public class FontHelper {

    public static Typeface antonTypeface(Context context){
        return Typeface.createFromAsset(context.getAssets(), "font/anton-regular.ttf");
    }

}
