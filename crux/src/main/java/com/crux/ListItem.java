package com.crux;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public interface ListItem {

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Context context);

    Object getUserInfo();

}
