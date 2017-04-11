package com.crux.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.crux.R;
import com.crux.util.FragmentUtils;

import java.util.Stack;

/**
 * An activity that helps in pushing & popping {@link Fragment}. Single stack since it always has one active fragment
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public abstract class SingleStackActivity extends DrawerActivity {

    public static final String SINGLE_PANE_FRAGMENT_TAG = "single_pane";

    private Stack<String> mFragmentStack = new Stack<>();
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.c_activity_single_stack);

        if (savedInstanceState == null) {
            mFragment = onCreatePane();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_frame, mFragment, SINGLE_PANE_FRAGMENT_TAG).commit();
        } else {
            mFragment = getSupportFragmentManager().findFragmentByTag(SINGLE_PANE_FRAGMENT_TAG);
        }
        mFragmentStack.push(SINGLE_PANE_FRAGMENT_TAG);
    }

    protected abstract Fragment onCreatePane();

    public Fragment getFragment() {
        return mFragment;
    }

    protected void pushToStack(Class fragmentClazz, Bundle args, String fragmentTag) {
        pushToStack(fragmentClazz, args, fragmentTag, false, null);
    }

    protected void pushToStack(Class fragmentClazz, Bundle args, String fragmentTag, boolean animate, Fragment targetFragment) {
        mFragment = FragmentUtils.replaceFragment(this, R.id.content_frame, fragmentClazz, args, fragmentTag, animate, true, targetFragment);
        mFragmentStack.push(fragmentTag);
    }

    protected boolean popFromStack() {
        boolean isDone = getSupportFragmentManager().popBackStackImmediate();
        if (!mFragmentStack.isEmpty()) {
            mFragmentStack.pop();
            mFragment = getSupportFragmentManager().findFragmentByTag(mFragmentStack.peek());
        }
        return isDone;
    }

    @Override
    public void onBackPressed() {
        if (popFromStack()) {
            return;
        }
        super.onBackPressed();
    }


}
