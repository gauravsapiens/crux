package com.example.crux;

import android.content.Context;
import android.content.Intent;

import com.example.crux.bean.Category;
import com.example.crux.bean.Product;
import com.example.crux.ui.HomeActivity;
import com.example.crux.ui.ProductDetailActivity;
import com.example.crux.ui.ProductListActivity;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class IntentHelper {

    public static final String CATEGORY = "category";
    public static final String PRODUCT = "product";

    public static Intent getIntentForCategoryList(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    public static Intent getIntentForProductList(Context context, Category category) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra(CATEGORY, category);
        return intent;
    }

    public static Intent getIntentForProductDetail(Context context, Product product) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(PRODUCT, product);
        return intent;
    }

}
