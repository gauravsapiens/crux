package com.crux.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter{

    private List<FragmentInfo> mFragmentInfos;

    public PagerAdapter(FragmentManager fm, List<FragmentInfo> fragmentInfos) {
        super(fm);
        mFragmentInfos = fragmentInfos;
    }

    @Override
    public Fragment getItem(int position) {
        FragmentInfo fragmentInfo = getInfo(position);
        if (fragmentInfo == null) {
            return null;
        }
        return fragmentInfo.fragment;
    }

    @Override
    public int getCount() {
        return mFragmentInfos.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        FragmentInfo fragmentInfo = getInfo(position);
        if (fragmentInfo == null) {
            return null;
        }
        return fragmentInfo.title;
    }

    public Fragment getFragmentAtPosition(int position) {
        if (mFragments == null) {
            return null;
        }
        return mFragments.get(position);
    }

    public void setFragmentInfos(List<FragmentInfo> fragmentInfos){
        this.mFragmentInfos = fragmentInfos;
    }

    private FragmentInfo getInfo(int position) {
        return mFragmentInfos.get(position);
    }

    public static class FragmentInfo {
        Fragment fragment;
        String title;

        public FragmentInfo(Fragment fragment, String title) {
            this.fragment = fragment;
            this.title = title;
        }
    }

}
