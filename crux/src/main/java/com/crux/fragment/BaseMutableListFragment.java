package com.crux.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.crux.R;

/**
 * An extension of {@link BaseListFragment} with support for pagination & pull-to-refresh
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public abstract class BaseMutableListFragment extends BaseListFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    protected void onEndReached() {

    }

    protected void onTopReached() {

    }

    protected View getLoadingFooterView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        return inflater.inflate(R.layout.c_view_loading_footer, mRecyclerView, false);
    }

    private OnScrollListener mOnScrollListener = new OnScrollListener() {
        @Override
        public void onScrolledToBottom() {
            onEndReached();
        }

        @Override
        public void onScrolledToTop() {
            onTopReached();
        }
    };

}
