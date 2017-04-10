package com.crux.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;

import com.crux.Crux;

import java.io.InputStream;

/**
 * Utils for android resources
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public class ResourceUtils {

    public static String getString(int resourceId) {
        return getApplicationContext().getResources().getString(resourceId);
    }

    public static int getColor(int resourceId) {
        return getApplicationContext().getResources().getColor(resourceId);
    }

    public static ColorStateList getColorStateList(int resourceId) {
        return getApplicationContext().getResources().getColorStateList(resourceId);
    }

    public static int getInteger(int resourceId) {
        return getApplicationContext().getResources().getInteger(resourceId);
    }

    public static int getDimension(int resourceId) {
        return (int) getApplicationContext().getResources().getDimension(resourceId);
    }

    public static Drawable getDrawable(int resourceId) {
        return getApplicationContext().getResources().getDrawable(resourceId);
    }

    public static Drawable getLayeredDrawable(int resource1, int resource2) {
        Drawable d1 = ResourceUtils.getDrawable(resource1);
        Drawable d2 = ResourceUtils.getDrawable(resource2);

        return new LayerDrawable(new Drawable[]{d1, d2});
    }

    public static int getResourceId(String resourceId, String type) {
        Context context = getApplicationContext();
        return context.getResources().getIdentifier(resourceId, type, context.getPackageName());
    }

    public static boolean doesAssetExists(String path) {
        boolean assetExists = false;
        try {
            InputStream stream = getApplicationContext().getAssets().open(path);
            stream.close();
            assetExists = true;
        } catch (Exception e) {
            Log.d("ResourceUtils", "Asset Not Found");
        }
        return assetExists;
    }

    public static String getFontFileAbsolutePath(String fontFileName){
        String fontPath = "fonts/" + fontFileName + ".ttf";
        return fontPath;
    }

    private static Context getApplicationContext() {
        return Crux.getContext();
    }

}
