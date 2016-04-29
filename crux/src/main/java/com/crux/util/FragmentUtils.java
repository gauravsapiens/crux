package com.crux.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.crux.activity.BaseActivity;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class FragmentUtils {

    public static Fragment replaceFragment(BaseActivity baseActivity, int containerId, Class fragmentClass,
                                           Bundle args, String fragmentTag) {
        return replaceFragment(baseActivity, containerId, fragmentClass, args, fragmentTag, false);
    }

    public static Fragment replaceFragment(BaseActivity baseActivity, int containerId, Class fragmentClass,
                                           Bundle args, String fragmentTag, boolean animate) {
        return replaceFragment(baseActivity, containerId, fragmentClass, args, fragmentTag, animate, true);
    }

    public static Fragment replaceFragment(BaseActivity baseActivity, int containerId, Class fragmentClass,
                                           Bundle args, String fragmentTag, boolean animate, boolean addToBackStack) {
        return replaceFragment(baseActivity, containerId, fragmentClass, args, fragmentTag, animate, addToBackStack, null);
    }

    public static Fragment replaceFragment(BaseActivity baseActivity, int containerId, Class fragmentClass,
                                           Bundle args, String fragmentTag, boolean animate, boolean addToBackStack, Fragment targetFragment) {
        Fragment fragment = Fragment.instantiate(baseActivity, fragmentClass.getName());
        if (args != null) {
            fragment.setArguments(args);
        }

        if (fragment != null) {
            fragment.setTargetFragment(targetFragment, 0);
        }

        return replaceFragment(baseActivity, fragment, containerId, fragmentTag, addToBackStack);
    }

    public static Fragment replaceFragment(BaseActivity baseActivity, Fragment fragment, int containerId) {
        return replaceFragment(baseActivity, fragment, containerId, null, true);
    }

    public static Fragment replaceFragment(BaseActivity baseActivity, Fragment fragment, int containerId, String fragmentTag, boolean addToBackStack) {
        FragmentManager fragmentManager = baseActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, fragment, fragmentTag);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
        return fragment;
    }

    public static boolean isFragmentVisible(Fragment fragment) {
        return fragment.isAdded() && fragment.isVisible();

    }

    public static void removeFragment(BaseActivity baseActivity, String fragmentTag) {
        FragmentManager fragmentManager = baseActivity.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        fragmentManager.beginTransaction().remove(fragment).commit();
    }

}
