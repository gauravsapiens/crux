package com.example.crux;

import android.app.Application;

import com.crux.Crux;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Crux.initialize(this);
    }
}
