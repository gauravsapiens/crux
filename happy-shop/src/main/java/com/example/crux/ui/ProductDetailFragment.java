package com.example.crux.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crux.fragment.BaseFragment;
import com.crux.util.ToastUtils;
import com.crux.view.base.CruxImageView;
import com.example.crux.IntentHelper;
import com.example.crux.R;
import com.example.crux.bean.CartElement;
import com.example.crux.bean.Product;
import com.example.crux.database.CartDao;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class ProductDetailFragment extends BaseFragment {

    View mRootView;
    private CruxImageView mImageView;
    private TextView mTitleView;
    private TextView mSubtitleView;
    private TextView mDescriptionView;
    private Button mAddCartButton;
    private Button mCheckoutButton;
    private CartDao mCartDao = new CartDao();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_product_detail, null);
        mImageView = (CruxImageView) mRootView.findViewById(R.id.image);
        mTitleView = (TextView) mRootView.findViewById(R.id.title);
        mSubtitleView = (TextView) mRootView.findViewById(R.id.subtitle);
        mDescriptionView = (TextView) mRootView.findViewById(R.id.description);
        mAddCartButton = (Button) mRootView.findViewById(R.id.button_cart);
        mCheckoutButton = (Button) mRootView.findViewById(R.id.button_checkout);

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Product product = getProduct();
        if (product == null) {
            return;
        }

        mImageView.setImageURI(product.getImageUrl());
        mTitleView.setText(product.getName());
        mSubtitleView.setText("S$ " + product.getPrice());
        mDescriptionView.setText(product.getDescription());
        mAddCartButton.setOnClickListener(v -> ProductDetailFragment.this.onAddToCartButtonPressed(product));
    }

    private Product getProduct() {
        return getArguments().getParcelable(IntentHelper.PRODUCT);
    }

    private void onAddToCartButtonPressed(Product product) {
        ToastUtils.showSnackBar(mRootView, "Product added to cart");
        int productCount = 1;
        CartElement cartElement = mCartDao.findById(product.getId());
        if (cartElement != null) {
            productCount = cartElement.getQuantity() + 1;
        }
        cartElement = new CartElement(product.getId(), product, productCount);
        mCartDao.createOrUpdate(cartElement);
    }
}
