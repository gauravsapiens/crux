package com.crux;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author gauravarora
 * @since 29/04/16.
 */
public class Crux {

    private static Context sContext;

    public static void initialize(Context context) {
        sContext = context;

        //initialize ActiveAndroid
        Configuration configuration = new Configuration.Builder(context).setDatabaseName("crux").setDatabaseVersion(1).create();
        ActiveAndroid.initialize(configuration);

        //intialize Fresco
        Fresco.initialize(context);
    }

    public static Context getContext() {
        return sContext;
    }
}
