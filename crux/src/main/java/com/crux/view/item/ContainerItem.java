package com.crux.view.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.crux.ListItem;

/**
 * @author gauravarora
 * @since 28/04/16.
 */
public class ContainerItem implements ListItem {

    private View mView;

    public ContainerItem(View view){
        mView = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Context context) {
    }

    @Override
    public Object getUserInfo() {
        return null;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
