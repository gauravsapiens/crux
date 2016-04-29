package com.crux.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.crux.R;
import com.crux.util.FragmentUtils;

/**
 * An activity that helps in pushing & popping {@link Fragment}. Single stack since it always has one active fragment
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public abstract class SingleStackActivity extends DrawerActivity {

    public static final String SINGLE_PANE_FRAGMENT_TAG = "single_pane";
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_single_stack);

        if (savedInstanceState == null) {
            fragment = onCreatePane();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_frame, fragment, SINGLE_PANE_FRAGMENT_TAG).commit();
        } else {
            fragment = getSupportFragmentManager().findFragmentByTag(SINGLE_PANE_FRAGMENT_TAG);
        }
    }

    protected abstract Fragment onCreatePane();

    public Fragment getFragment() {
        return fragment;
    }

    protected void pushToStack(Class fragmentClazz, Bundle args, String fragmentTag) {
        pushToStack(fragmentClazz, args, fragmentTag, false, null);
    }

    protected void pushToStack(Class fragmentClazz, Bundle args, String fragmentTag, boolean animate, Fragment targetFragment) {
        fragment = FragmentUtils.replaceFragment(this, R.id.content_frame, fragmentClazz, args, fragmentTag, animate, true, targetFragment);
    }

    protected boolean popFromStack() {
        return getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onBackPressed() {
        if (popFromStack()) {
            return;
        }
        super.onBackPressed();
    }


}
