package com.crux;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author gauravarora
 * @since 29/04/16.
 */
public class Crux {

    private static Context sContext;
    private static Configuration sConfiguration;

    public static void initialize(Context context) {
        initialize(context, new Configuration());
    }

    public static void initialize(Context context, Configuration configuration) {
        sContext = context;
        sConfiguration = configuration;

        //initialize ActiveAndroid
        if (configuration.isDatabaseEnabled()) {
            com.activeandroid.Configuration aaConfig = new com.activeandroid.Configuration.Builder(context)
                    .setDatabaseName(configuration.getDatabaseName())
                    .setDatabaseVersion(configuration.getDatabaseVersion())
                    .setModelClasses(configuration.getTableClasses())
                    .setTypeSerializers(configuration.getTypeSerializers())
                    .create();
            ActiveAndroid.initialize(aaConfig);
        }

        //intialize Fresco
        Fresco.initialize(context);
    }

    public static Context getContext() {
        return sContext;
    }

    public static Configuration getConfiguration() {
        return sConfiguration;
    }
}
