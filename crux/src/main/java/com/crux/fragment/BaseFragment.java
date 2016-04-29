package com.crux.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.crux.util.ClassUtils;
import com.facebook.common.internal.Preconditions;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class BaseFragment extends Fragment {

    public Object getCallbacks(Class clazz) {
        Preconditions.checkNotNull(clazz, "Class cannot be null");

        Fragment fragment = getParentFragment();
        if (fragment != null && ClassUtils.isSubclass(fragment.getClass(), clazz)) {
            return fragment;
        }

        Activity activity = getActivity();
        if (activity != null && ClassUtils.isSubclass(activity.getClass(), clazz)) {
            return activity;
        }

        return null;
    }

}
