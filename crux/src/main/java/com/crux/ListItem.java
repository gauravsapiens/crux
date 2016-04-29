package com.crux;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Defines a list item used for constructing {@link RecyclerView} in {@link com.crux.fragment.BaseListFragment}
 *
 * @author gauravarora
 * @see com.crux.adapter.ListAdapter
 * @since 27/04/16.
 */
public interface ListItem {

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Context context);

    Object getUserInfo();

}
