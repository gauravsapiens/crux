package com.example.crux;

import android.os.Bundle;
import android.os.Handler;

import com.crux.activity.BaseActivity;

public class LauncherActivity extends BaseActivity {

    private static final int SPLASH_DURATION_IN_MILLISECONDS = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(this::launchCategoryListActivity, SPLASH_DURATION_IN_MILLISECONDS);
    }

    private void launchCategoryListActivity() {
        startActivity(IntentHelper.getIntentForCategoryList(this));
    }

    @Override
    protected boolean isToolbarEnabled() {
        return false;
    }

}
