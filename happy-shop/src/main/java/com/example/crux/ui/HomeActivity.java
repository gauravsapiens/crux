package com.example.crux.ui;

import android.content.Intent;

import com.crux.activity.PagerActivity;
import com.crux.adapter.PagerAdapter;
import com.example.crux.IntentHelper;
import com.example.crux.bean.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class HomeActivity extends PagerActivity implements CategoryListFragment.Callback {

    @Override
    protected void customizeActivity() {
        super.customizeActivity();
        setActivityTitle("");
    }

    @Override
    protected List<PagerAdapter.FragmentInfo> getFragmentInfos() {
        List<PagerAdapter.FragmentInfo> fragmentInfos = new ArrayList<>();
        fragmentInfos.add(new PagerAdapter.FragmentInfo(new CategoryListFragment(), "Category"));
        fragmentInfos.add(new PagerAdapter.FragmentInfo(new CartListFragment(), "Cart"));
        return fragmentInfos;
    }

    @Override
    public void onCategorySelected(Category category) {
        Intent intent = IntentHelper.getIntentForProductList(this, category);
        startActivity(intent);
    }

    @Override
    protected boolean isDisplayHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected boolean isNavDrawerEnabled() {
        return false;
    }

}
