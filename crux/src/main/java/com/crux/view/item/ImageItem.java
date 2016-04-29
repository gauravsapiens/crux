package com.crux.view.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crux.ListItem;
import com.crux.R;
import com.crux.view.base.CruxImageView;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class ImageItem implements ListItem {

    private int mImageResourceId;
    private String mImageUrl;
    private Object mUserInfo;

    public ImageItem(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public ImageItem(int imageResourceId) {
        mImageResourceId = imageResourceId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.item_simple_image, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Context context) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.imageView.setImageURI(mImageResourceId, mImageUrl);
    }

    public void setUserInfo(Object userInfo) {
        mUserInfo = userInfo;
    }

    @Override
    public Object getUserInfo() {
        return mUserInfo;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        CruxImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (CruxImageView) itemView;
        }
    }
}