package com.crux.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.crux.LauncherOption;
import com.crux.util.CollectionUtils;
import com.crux.util.IntentUtils;

import java.util.List;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class NavigationPresenter {

    private BaseActivity mBaseActivity;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private List<LauncherOption> mLauncherOptions;

    public NavigationPresenter(BaseActivity baseActivity, DrawerLayout drawerLayout, NavigationView navigationView, List<LauncherOption> launcherOptions) {
        this.mBaseActivity = baseActivity;
        this.mDrawerLayout = drawerLayout;
        this.mNavigationView = navigationView;
        this.mLauncherOptions = launcherOptions;
    }

    public void setup() {
        if(CollectionUtils.isEmpty(mLauncherOptions)){
            return;
        }

        Class clazz = mBaseActivity.getClass();
        Menu menu = mNavigationView.getMenu();
        for (LauncherOption launcherOption : mLauncherOptions) {
            MenuItem menuItem = menu.add(launcherOption.getImageResourceId(), launcherOption.getIndex(), launcherOption.getIndex(), launcherOption.getTitle());
            menuItem.setIcon(launcherOption.getImageResourceId());
            menuItem.setCheckable(true);
            if (clazz != null && clazz.equals(launcherOption.getIntentClass())) {
                menuItem.setChecked(true);
            }
        }

        mNavigationView.setNavigationItemSelectedListener(this::onNavigationButtonSelected);
    }

    private boolean onNavigationButtonSelected(MenuItem menuItem) {
        LauncherOption launcherOption = getLauncherOption(menuItem.getOrder());
        if(launcherOption == null){
            return false;
        }

        launchNodeActivity(launcherOption.getIntentClass());
        mDrawerLayout.closeDrawers();
        return true;
    }

    private void launchNodeActivity(Class intentClass) {
        Intent intent = new Intent(mBaseActivity, intentClass);
        IntentUtils.makeIntentNode(intent);
        mBaseActivity.startActivity(intent);
    }

    private LauncherOption getLauncherOption(int index){
        for (LauncherOption launcherOption: mLauncherOptions){
            if(launcherOption.getIndex() == index){
                return launcherOption;
            }
        }
        return null;
    }
}
