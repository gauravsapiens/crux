package com.crux.fragment;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;

import com.crux.ListItem;
import com.crux.R;
import com.crux.util.CollectionUtils;

import java.util.List;

/**
 * An extension of {@link BaseListFragment} with support for pagination & pull-to-refresh
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public abstract class BaseMutableListFragment extends BaseListFragment {

    public enum Mode {
        FIRST_TIME,
        PAGINATED,
        PULL_TO_REFRESH,
        NONE,
    }

    protected Mode mMode = Mode.FIRST_TIME;
    protected int mPageNumber = 1;
    protected boolean mAllItemsLoaded;

    protected boolean mIsLoadingNextPage;

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public final List<ListItem> loadListItemsInBackground() {
        return loadListItems(mMode, mPageNumber);
    }

    protected abstract List<ListItem> loadListItems(Mode mode, Object extras);

    @Override
    public void onLoadFinished(Loader<List<ListItem>> listLoader, List<ListItem> listItems) {
        switch (mMode) {
            case FIRST_TIME:
            case PULL_TO_REFRESH:
            case NONE: {
                mAdapter.setRecyclableItems(listItems);
                mAdapter.notifyDataSetChanged();
            }
            break;
            case PAGINATED: {
                removeFooter();
                if (CollectionUtils.isEmpty(listItems)) {
                    mAllItemsLoaded = true;
                }
                mAdapter.addItems(listItems);
                mAdapter.notifyDataSetChanged();
            }
            break;
        }
        mMode = Mode.NONE;

        onLoadFinished();
    }

    protected void loadNextPage() {
        mMode = Mode.PAGINATED;
        mPageNumber = mPageNumber + 1;
        mIsLoadingNextPage = true;

        addFooter(getLoadingFooterView());
        getItemListLoader().forceLoad();
    }

    protected void onEndReached() {

    }

    protected void onTopReached() {

    }

    protected View getLoadingFooterView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        return inflater.inflate(R.layout.c_view_loading_footer, mRecyclerView, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
    }

}
