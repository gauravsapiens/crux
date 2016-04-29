package com.crux;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * @author gauravarora
 * @since 29/04/16.
 */
public class Crux {

    private static Context sContext;

    public static void initialize(Context context) {
        sContext = context;

        Fresco.initialize(context);
        FlowManager.init(context);
    }

    public static Context getContext() {
        return sContext;
    }
}
