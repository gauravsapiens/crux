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
import com.example.crux.bean.CartElement;
import com.example.crux.bean.Product;

/**
 * @author gauravarora
 * @since 29/04/16.
 */
public class CartItem implements ListItem {

    private CartElement mCartElement;

    public CartItem(CartElement product) {
        mCartElement = product;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Context context) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Product product = mCartElement.getProduct();
        if (product == null) {
            return;
        }

        float amount = product.getPrice() * mCartElement.getQuantity();
        viewHolder.imageView.setImageURI(product.getImageUrl());
        viewHolder.titleView.setText(product.getName());
        viewHolder.quantityView.setText("Quantity: " + mCartElement.getQuantity() + "");
        viewHolder.priceView.setText("S$ " + amount);
    }

    @Override
    public Object getUserInfo() {
        return mCartElement;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        CruxImageView imageView;
        TextView titleView;
        TextView priceView;
        TextView quantityView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (CruxImageView) itemView.findViewById(R.id.image);
            titleView = (TextView) itemView.findViewById(R.id.title);
            priceView = (TextView) itemView.findViewById(R.id.price);
            quantityView = (TextView) itemView.findViewById(R.id.quantity);
        }
    }

}
