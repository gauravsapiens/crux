package com.crux.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.crux.ItemListLoader;
import com.crux.ListItem;
import com.crux.OnItemClickListener;
import com.crux.R;
import com.crux.adapter.ListAdapter;
import com.crux.view.item.ContainerItem;

import java.util.List;

/**
 * A fragment that takes care of rendering {@link RecyclerView} using {@link ListItem}
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public abstract class BaseListFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<List<ListItem>>, ItemListLoader.ItemLoaderCallbacks<ListItem>, OnItemClickListener {

    public static final int ORIENTATION_HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int ORIENTATION_VERTICAL = LinearLayoutManager.VERTICAL;

    private static final String RetainDataKeyIsFetchingData = "isFetchingData";
    private static final int ITEM_LIST_LOADER_ID = 10;

    protected ViewGroup mRootView;
    private View mListContainer;
    private View mProgressContainer;
    private ViewStub mEmptyViewStub;
    private View mEmptyView;
    protected boolean mListShown;

    protected FrameLayout mStaticHeader;
    protected FrameLayout mStaticFooter;

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ListAdapter mAdapter;

    protected ListItem mHeaderItem;
    protected ListItem mFooterItem;

    protected boolean mIsFetchingData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mIsFetchingData = savedInstanceState != null && savedInstanceState.getBoolean(RetainDataKeyIsFetchingData, false);

        mRootView = (ViewGroup) inflater.inflate(R.layout.c_fragment_list, container, false);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
        mListContainer = mRootView.findViewById(R.id.list_container);
        mProgressContainer = mRootView.findViewById(R.id.progress_container);

        //empty layout
        mEmptyViewStub = (ViewStub) mRootView.findViewById(R.id.empty_view_stub);
        mEmptyViewStub.setLayoutResource(getEmptyLayoutId());
        mEmptyView = mEmptyViewStub.inflate();

        TextView emptyTextView = (TextView) mRootView.findViewById(R.id.empty_text);
        emptyTextView.setText(getEmptyText());

        //static header & footer
        mStaticHeader = (FrameLayout) mRootView.findViewById(R.id.static_header);
        mStaticFooter = (FrameLayout) mRootView.findViewById(R.id.static_footer);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mLayoutManager = getLayoutManager();
        mRecyclerView.setLayoutManager(mLayoutManager);

        setListShown(false);
        customizeView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = getRecyclableAdapter();

        initializeAdapter();
        initializeLoader();
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity(), getListOrientation(), false);
    }

    private void initializeAdapter() {
        if (mAdapter == null) {
            return;
        }

        mAdapter.setOnItemClickListener(this);
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (mAdapter.getItemCount() == 0) {
                    showEmptyView();
                } else {
                    hideEmptyView();
                }
            }
        });
        mRecyclerView.setAdapter(decorateAdapter(mAdapter));
    }

    private void initializeLoader() {
        getLoaderManager().initLoader(ITEM_LIST_LOADER_ID, null, this);
    }

    protected RecyclerView.Adapter decorateAdapter(ListAdapter adapter) {
        return adapter;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(RetainDataKeyIsFetchingData, mIsFetchingData);
    }

    @Override
    public Loader<List<ListItem>> onCreateLoader(int i, Bundle bundle) {
        ItemListLoader loader = new ItemListLoader(getActivity());
        loader.setItemLoader(this);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<ListItem>> listLoader, List<ListItem> listItems) {
        if (mHeaderItem != null) {
            listItems.add(0, mHeaderItem);
        }

        if (mFooterItem != null) {
            int lastIndex = mAdapter.getItemCount();
            listItems.add(lastIndex, mFooterItem);
        }

        mAdapter.setRecyclableItems(listItems);
        mRecyclerView.getAdapter().notifyDataSetChanged();

        onLoadFinished();
    }

    public void onRecyclableItemClicked(final ListItem item, final int position) {
        onItemClicked(item, position);
    }

    @Override
    public void onLoaderReset(Loader<List<ListItem>> listLoader) {

    }

    protected void onLoadFinished() {

    }

    public abstract void onItemClicked(ListItem item, long position);

    protected ItemListLoader getItemListLoader() {
        final Loader<List<ListItem>> loader = getLoaderManager().getLoader(ITEM_LIST_LOADER_ID);
        if (loader != null && loader instanceof ItemListLoader)
            return ((ItemListLoader) loader);
        return null;
    }

    protected void setListShown(boolean shown) {
        setListShown(shown, false);
    }

    private void setListShown(boolean shown, boolean animate) {
        mListShown = shown;
        if (shown) {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                        android.R.anim.fade_out));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                        android.R.anim.fade_in));
            } else {
                mProgressContainer.clearAnimation();
                mListContainer.clearAnimation();
            }
            hideLoader();
        } else {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                        android.R.anim.fade_in));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                        android.R.anim.fade_out));
            } else {
                mProgressContainer.clearAnimation();
                mListContainer.clearAnimation();
            }
            showLoader();
        }
    }

    protected void showLoader() {
        mProgressContainer.setVisibility(View.VISIBLE);
        mListContainer.setVisibility(View.GONE);
    }


    protected void hideLoader() {
        mProgressContainer.setVisibility(View.GONE);
        mListContainer.setVisibility(View.VISIBLE);
    }

    protected void addHeader(View view) {
        if (view == null) {
            return;
        }
        mHeaderItem = new ContainerItem(view);
        if (mAdapter != null) {
            mAdapter.addItem(mHeaderItem, 0);
            mAdapter.notifyDataSetChanged();
        }
    }

    protected void removeHeader() {
        mHeaderItem = null;
        if (mAdapter != null) {
            mAdapter.removeItem(0);
            mAdapter.notifyDataSetChanged();
        }
    }

    protected void addFooter(View view) {
        if (view == null) {
            return;
        }
        mFooterItem = new ContainerItem(view);
        if (mAdapter != null) {
            int lastIndex = mAdapter.getItemCount();
            mAdapter.addItem(mFooterItem, lastIndex);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(lastIndex);
        }
    }

    protected void removeFooter() {
        mFooterItem = null;
        if (mAdapter != null) {
            int lastIndex = mAdapter.getItemCount() - 1;
            mAdapter.removeItem(lastIndex);
            mAdapter.notifyDataSetChanged();
        }
    }

    protected void addStaticHeader(@LayoutRes int layoutId) {
        View view = LayoutInflater.from(getContext()).inflate(layoutId, mRootView, true);
        mStaticHeader.addView(view);
    }

    protected void addStaticHeader(View headerView) {
        mStaticHeader.addView(headerView);
    }

    protected void addStaticFooter(@LayoutRes int layoutId) {
        View view = LayoutInflater.from(getContext()).inflate(layoutId, mRootView, true);
        mStaticFooter.addView(view);
    }

    protected void addStaticFooter(View footerView) {
        mStaticFooter.addView(footerView);
    }

    protected void customizeView() {
    }

    protected int getEmptyLayoutId() {
        return R.layout.c_view_empty_list;
    }

    protected String getEmptyText() {
        return "No Results";
    }

    protected void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
    }

    protected void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
    }

    public void refreshLoader() {
        refreshLoader(false);
    }

    public void refreshLoader(boolean forceRefresh) {
        if (forceRefresh) {
            getLoaderManager().restartLoader(ITEM_LIST_LOADER_ID, null, this);
        }

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    protected int getListOrientation() {
        return ORIENTATION_VERTICAL;
    }

    protected ListAdapter getRecyclableAdapter() {
        return new ListAdapter(getActivity());
    }

    @Override
    public List<ListItem> deliverListItems(List<ListItem> loadedTableItems) {
        return loadedTableItems;
    }

    public ListAdapter getAdapter() {
        return mAdapter;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

}
