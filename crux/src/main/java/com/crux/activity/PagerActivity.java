package com.crux.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.crux.R;
import com.crux.adapter.PagerAdapter;
import com.crux.util.CollectionUtils;
import com.crux.view.base.CruxImageView;

import java.util.List;

/**
 * An activity that takes care of rendering {@link android.support.v4.view.ViewPager ViewPager}
 *
 * @author gauravarora
 * @see {@link #getFragmentInfos}
 * @since 27/04/16.
 */
public abstract class PagerActivity extends DrawerActivity {

    protected CruxImageView mToolbarImage;
    protected TabLayout mTabLayout;
    protected ViewPager mViewPager;
    protected PagerAdapter mAdapter;
    protected List<PagerAdapter.FragmentInfo> mFragmentInfos;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.c_activity_pager);

        setupViewPager();
        customizeActivity();
    }

    protected void customizeActivity(){
    }

    @Override
    protected int getAppBarLayout() {
        return R.layout.c_app_bar_tabbed;
    }

    private void setupViewPager() {
        mToolbarImage = (CruxImageView) findViewById(R.id.backdrop);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mFragmentInfos = getFragmentInfos();
        if (CollectionUtils.isEmpty(mFragmentInfos)) {
            return;
        }

        mAdapter = new PagerAdapter(getSupportFragmentManager(), mFragmentInfos);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    protected abstract List<PagerAdapter.FragmentInfo> getFragmentInfos();


}
