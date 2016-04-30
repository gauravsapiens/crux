package com.example.crux.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.crux.activity.SingleStackActivity;
import com.example.crux.R;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class ProductDetailActivity extends SingleStackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTitle(R.string.product_detail);
    }

    @Override
    protected Fragment onCreatePane() {
        Fragment fragment = new ProductDetailFragment();
        attachInputBundle(fragment);
        return fragment;
    }

}
