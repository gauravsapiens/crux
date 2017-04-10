package com.crux.view.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crux.ListItem;
import com.crux.R;
import com.crux.util.ViewUtils;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class TitleSubtitleItem implements ListItem {

    private String mTitle;
    private String mSubtitle;

    public TitleSubtitleItem(String title, String subtitle) {
        mTitle = title;
        mSubtitle = subtitle;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.c_item_title_subtitle, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Context context) {
        ViewHolder viewHolder = (ViewHolder) holder;

        ViewUtils.setText(viewHolder.titleTextView, mTitle, View.GONE);
        ViewUtils.setText(viewHolder.subtitleTextView, mSubtitle, View.GONE);
    }

    @Override
    public Object getUserInfo() {
        return null;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView subtitleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            subtitleTextView = (TextView) itemView.findViewById(R.id.subtitle);
        }
    }

}
