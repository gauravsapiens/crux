package com.crux.view.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crux.ListItem;
import com.crux.R;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class TextItem implements ListItem {

    private String mText;
    private Object mUserInfo;

    public TextItem(String text) {
        mText = text;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.c_item_simple_text, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Context context) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.textView.setText(mText);
    }

    public void setUserInfo(Object userInfo) {
        mUserInfo = userInfo;
    }

    @Override
    public Object getUserInfo() {
        return mUserInfo;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}