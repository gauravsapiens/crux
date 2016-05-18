package com.crux.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.crux.DrawerMenu;
import com.crux.util.CollectionUtils;
import com.crux.util.IntentUtils;

import java.util.List;

/**
 * A Presenter for navigation drawer
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public class NavigationPresenter {

    private BaseActivity mBaseActivity;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private List<DrawerMenu> mDrawerMenus;

    public NavigationPresenter(BaseActivity baseActivity, DrawerLayout drawerLayout, NavigationView navigationView, List<DrawerMenu> drawerMenus) {
        this.mBaseActivity = baseActivity;
        this.mDrawerLayout = drawerLayout;
        this.mNavigationView = navigationView;
        this.mDrawerMenus = drawerMenus;
    }

    public void setup() {
        if (CollectionUtils.isEmpty(mDrawerMenus)) {
            return;
        }

        Class clazz = mBaseActivity.getClass();
        final Menu menu = mNavigationView.getMenu();
        for (DrawerMenu drawerMenu : mDrawerMenus) {
            MenuItem menuItem = menu.add(drawerMenu.getImageResourceId(), drawerMenu.getIndex(), drawerMenu.getIndex(), drawerMenu.getTitle());
            menuItem.setIcon(drawerMenu.getImageResourceId());
            menuItem.setCheckable(true);
            if (clazz != null && clazz.equals(drawerMenu.getIntentClass())) {
                menuItem.setChecked(true);
            }
        }

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                return onNavigationButtonSelected(item);
            }
        });
    }

    private boolean onNavigationButtonSelected(MenuItem menuItem) {
        DrawerMenu drawerMenu = getLauncherOption(menuItem.getOrder());
        if (drawerMenu == null) {
            return false;
        }

        launchNodeActivity(drawerMenu.getIntentClass());
        mDrawerLayout.closeDrawers();
        return true;
    }

    private void launchNodeActivity(Class intentClass) {
        Intent intent = new Intent(mBaseActivity, intentClass);
        IntentUtils.makeIntentNode(intent);
        mBaseActivity.startActivity(intent);
    }

    private DrawerMenu getLauncherOption(int index) {
        for (DrawerMenu drawerMenu : mDrawerMenus) {
            if (drawerMenu.getIndex() == index) {
                return drawerMenu;
            }
        }
        return null;
    }
}
