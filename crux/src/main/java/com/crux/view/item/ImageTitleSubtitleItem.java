package com.crux.view.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crux.ListItem;
import com.crux.view.base.CruxImageView;
import com.crux.R;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class ImageTitleSubtitleItem implements ListItem{

    private String mImageUrl;
    private int mImageResourceId;
    private String mTitle;
    private String mSubtitle;
    private Object mUserInfo;

    public ImageTitleSubtitleItem(String title, String subtitle, String imageUrl){
        this.mTitle = title;
        this.mSubtitle = subtitle;
        this.mImageUrl = imageUrl;
    }

    public ImageTitleSubtitleItem(String title, String subtitle, int imageResourceId){
        this.mTitle = title;
        this.mSubtitle = subtitle;
        this.mImageResourceId = imageResourceId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.item_image_title_subtitle, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Context context) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.titleView.setText(mTitle);
        viewHolder.subtitleView.setText(mSubtitle);
        viewHolder.imageView.setImageURI(mImageResourceId, mImageUrl);
    }

    public void setUserInfo(Object userInfo){
        this.mUserInfo = userInfo;
    }

    @Override
    public Object getUserInfo() {
        return mUserInfo;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleView;
        TextView subtitleView;
        CruxImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.title);
            subtitleView = (TextView) itemView.findViewById(R.id.subtitle);
            imageView = (CruxImageView) itemView.findViewById(R.id.image);
        }
    }
}
