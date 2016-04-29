package com.crux.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewStub;

import com.crux.R;

/**
 * An activity that serves as base for all other activities. Extends from {@link AppCompatActivity}. Provides support for {@link CoordinatorLayout}
 * Also, splits main view into toolbar & content-view, providing greater flexibility for customization.
 * <p>
 * Toolbar can be enabled/disabled using {@link #isToolbarEnabled()}. Also, it can be customized using {@link #getAppBarLayout()}
 * To add contentView call, {@link #addContentView(int)}
 * <p>
 * Also, if you want to replace the parent layout, like in the case of ViewPagerActivity, simply override {@link #getParentLayout()}. Take
 * care of using app_bar, toolbar, app_bar_view_stub, content_bar_view_stub as standard naming conventions
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public class BaseActivity extends AppCompatActivity {

    protected CoordinatorLayout mCoordinatorLayout;
    private ViewStub mAppBarViewStub;
    private ViewStub mContentViewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getParentLayout());

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mAppBarViewStub = (ViewStub) findViewById(R.id.app_bar_view_stub);
        mContentViewStub = (ViewStub) findViewById(R.id.content_view_stub);

        initAppbar();
    }

    protected void addContentView(int layoutResourceId) {
        mContentViewStub.setLayoutResource(layoutResourceId);
        mContentViewStub.inflate();
    }

    protected int getAppBarLayout() {
        return R.layout.app_bar;
    }

    protected int getParentLayout() {
        return R.layout.activity_base;
    }

    protected boolean isToolbarEnabled() {
        return true;
    }

    public void setActivityTitle(int resourceId) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(resourceId);
        }
    }

    public void setActivityTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected void attachInputBundle(Fragment fragment) {
        Bundle bundle = getBundle();
        fragment.setArguments(bundle);
    }

    protected Bundle getBundle() {
        return getIntent().getExtras();
    }

    protected boolean isDisplayHomeAsUpEnabled() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onActionBarHomeIconClicked();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void initAppbar() {
        if (!isToolbarEnabled()) {
            return;
        }

        if (mAppBarViewStub != null) {
            mAppBarViewStub.setLayoutResource(getAppBarLayout());
            mAppBarViewStub.inflate();
        }

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        if (appBarLayout == null) {
            return;
        }

        Toolbar toolbar = (Toolbar) appBarLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled());
            supportActionBar.setHomeButtonEnabled(true);
        }
    }

    private void onActionBarHomeIconClicked() {
        if (isDisplayHomeAsUpEnabled())
            onBackPressed();
        else
            this.finish();
    }

}
