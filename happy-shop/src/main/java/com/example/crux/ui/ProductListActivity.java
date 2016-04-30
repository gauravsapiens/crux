package com.example.crux.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.crux.activity.SingleStackActivity;
import com.example.crux.IntentHelper;
import com.example.crux.bean.Category;
import com.example.crux.bean.Product;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class ProductListActivity extends SingleStackActivity implements ProductListFragment.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Category category = getBundle().getParcelable(IntentHelper.CATEGORY);
        if (category != null) {
            setActivityTitle(category.getName());
        }
    }

    @Override
    protected Fragment onCreatePane() {
        Fragment fragment = new ProductListFragment();
        attachInputBundle(fragment);
        return fragment;
    }

    @Override
    public void onProductSelected(Product product) {
        Intent intent = IntentHelper.getIntentForProductDetail(this, product);
        startActivity(intent);
    }

}
