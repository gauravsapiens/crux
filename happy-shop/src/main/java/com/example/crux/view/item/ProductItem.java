package com.example.crux.view.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crux.ListItem;
import com.crux.view.base.CruxImageView;
import com.example.crux.R;
import com.example.crux.bean.Product;

/**
 * @author gauravarora
 * @since 29/04/16.
 */
public class ProductItem implements ListItem {

    private Product mProduct;

    public ProductItem(Product product) {
        mProduct = product;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Context context) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.titleView.setText(mProduct.getName());
        viewHolder.subtitleView.setText("S$ " + mProduct.getPrice());
        viewHolder.imageView.setImageURI(mProduct.getImageUrl());
    }

    @Override
    public Object getUserInfo() {
        return mProduct;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleView;
        TextView subtitleView;
        CruxImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(com.crux.R.id.title);
            subtitleView = (TextView) itemView.findViewById(com.crux.R.id.subtitle);
            imageView = (CruxImageView) itemView.findViewById(com.crux.R.id.image);
        }
    }

}
