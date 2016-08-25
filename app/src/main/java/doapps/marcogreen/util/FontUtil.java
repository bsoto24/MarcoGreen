package doapps.marcogreen.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Bryam Soto on 18/08/2016.
 */
public class FontUtil {

    public static Typeface getRobotoThin(Context context){
        return Typeface.createFromAsset(context.getAssets(), "font/Roboto-Thin.ttf");
    }
    public static Typeface getRobotoRegular(Context context){
        return Typeface.createFromAsset(context.getAssets(), "font/Roboto-Regular.ttf");
    }
    public static Typeface getRobotoBold(Context context){
        return Typeface.createFromAsset(context.getAssets(), "font/Roboto-Bold.ttf");
    }
}
