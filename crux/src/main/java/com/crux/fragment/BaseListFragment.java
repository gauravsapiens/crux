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
import android.widget.FrameLayout;

import com.crux.ItemListLoader;
import com.crux.ListItem;
import com.crux.OnItemClickListener;
import com.crux.OnItemLongClickListener;
import com.crux.R;
import com.crux.adapter.ListAdapter;
import com.crux.util.CollectionUtils;
import com.crux.view.base.CruxImageButton;
import com.crux.view.item.ContainerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment that takes care of rendering {@link RecyclerView} using {@link ListItem}
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public abstract class BaseListFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<List<ListItem>>, ItemListLoader.ItemLoaderCallbacks<ListItem>, OnItemClickListener,OnItemLongClickListener {

    public static final int ORIENTATION_HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int ORIENTATION_VERTICAL = LinearLayoutManager.VERTICAL;

    private static final int ITEM_LIST_LOADER_ID = 10;

    protected ViewGroup mRootView;

    //Static views
    protected FrameLayout mStaticHeader;
    protected FrameLayout mStaticFooter;

    //Progress view
    private FrameLayout mProgressContainer;

    //empty views
    private FrameLayout mEmptyContainer;

    //error views
    private FrameLayout mErrorContainer;
    private CruxImageButton mRetryButton;

    //Recycler view
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ListAdapter mAdapter;

    protected ViewMode mViewMode;

    public enum ViewMode {
        NORMAL, LOADING, EMPTY, ERROR
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = (ViewGroup) inflater.inflate(R.layout.c_fragment_list, container, false);

        //static header & footer
        mStaticHeader = (FrameLayout) mRootView.findViewById(R.id.static_header);
        mStaticFooter = (FrameLayout) mRootView.findViewById(R.id.static_footer);

        //Progress layout
        mProgressContainer = (FrameLayout) mRootView.findViewById(R.id.progress_container);
        mProgressContainer.addView(getProgressLayoutId());

        //empty layout
        mEmptyContainer = (FrameLayout) mRootView.findViewById(R.id.empty_view_container);
        mEmptyContainer.addView(getEmptyLayoutId());

        //error layout
        mErrorContainer = (FrameLayout) mRootView.findViewById(R.id.error_view_container);
        mErrorContainer.addView(getErrorLayoutId());
        mRetryButton = (CruxImageButton) mErrorContainer.findViewById(R.id.retry_button);
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRetryButtonClicked();
            }
        });

        //Recycler view
        ViewStub recyclerViewStub = (ViewStub) mRootView.findViewById(R.id.recycler_view_stub);
        recyclerViewStub.setLayoutResource(getRecyclerLayout());
        mRecyclerView = (RecyclerView) recyclerViewStub.inflate();

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mLayoutManager = getLayoutManager();
        mRecyclerView.setLayoutManager(mLayoutManager);

        setViewMode(ViewMode.LOADING);
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
        mAdapter.setOnItemLongClickListener(this);
        mRecyclerView.setAdapter(decorateAdapter(mAdapter));
    }

    private void initializeLoader() {
        getLoaderManager().initLoader(ITEM_LIST_LOADER_ID, null, this);
    }

    protected int getRecyclerLayout() {
        return R.layout.c_view_recycler;
    }

    protected RecyclerView.Adapter decorateAdapter(ListAdapter adapter) {
        return adapter;
    }

    @Override
    public Loader<List<ListItem>> onCreateLoader(int i, Bundle bundle) {
        ItemListLoader loader = new ItemListLoader(getActivity());
        loader.setItemLoader(this);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<ListItem>> listLoader, List<ListItem> listItems) {
        mAdapter.setRecyclableItems(listItems);
        mRecyclerView.getAdapter().notifyDataSetChanged();

        onLoadFinished();
    }

    public void onRecyclableItemClicked(final ListItem item, final int position) {
        onItemClicked(item, position);
    }

    @Override
    public void onRecyclableItemLongClicked(ListItem item, int position) {
        onItemLongClicked(item, position);
    }

    @Override
    public void onLoaderReset(Loader<List<ListItem>> listLoader) {

    }

    protected void onLoadFinished() {

    }

    public abstract void onItemClicked(ListItem item, long position);
    public void onItemLongClicked(ListItem item, int position) {
    }

    protected ItemListLoader getItemListLoader() {
        final Loader<List<ListItem>> loader = getLoaderManager().getLoader(ITEM_LIST_LOADER_ID);
        if (loader != null && loader instanceof ItemListLoader)
            return ((ItemListLoader) loader);
        return null;
    }

    protected void setViewMode(ViewMode viewMode) {
        mViewMode = viewMode;

        int recyclerViewVisibility = viewMode == ViewMode.NORMAL ? View.VISIBLE : View.GONE;
        int progressBarVisibility = viewMode == ViewMode.LOADING ? View.VISIBLE : View.GONE;
        int emptyViewVisibility = viewMode == ViewMode.EMPTY ? View.VISIBLE : View.GONE;
        int errorViewVisibility = viewMode == ViewMode.ERROR ? View.VISIBLE : View.GONE;

        mRecyclerView.setVisibility(recyclerViewVisibility);
        mProgressContainer.setVisibility(progressBarVisibility);
        mEmptyContainer.setVisibility(emptyViewVisibility);
        mErrorContainer.setVisibility(errorViewVisibility);
    }

    protected void addItem(ListItem item, int position) {
        addItem(item, position, false);
    }

    protected void addItem(ListItem item, int position, boolean scroll) {
        if (CollectionUtils.isEmpty(mAdapter.getItems())) {
            List<ListItem> items = new ArrayList<>();
            items.add(item);
            mAdapter.setRecyclableItems(items);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.addItem(item, position);
            mAdapter.notifyItemInserted(position);
            if (scroll) {
                mRecyclerView.smoothScrollToPosition(position);
            }
        }
    }

    protected void addItems(List<ListItem> items, int position) {
        addItems(items, position, false);
    }

    protected void addItems(List<ListItem> items, int position, boolean scroll) {
        if (CollectionUtils.isEmpty(mAdapter.getItems())) {
            List<ListItem> newItems = new ArrayList<>();
            items.addAll(newItems);
            mAdapter.setRecyclableItems(newItems);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.addItems(position, items);
            mAdapter.notifyItemInserted(position);
            if (scroll) {
                mRecyclerView.smoothScrollToPosition(position);
            }
        }
    }

    protected void removeItem(int position) {
        if (CollectionUtils.isEmpty(mAdapter.getItems())) {
            return;
        }
        mAdapter.removeItem(position);
        mAdapter.notifyItemRemoved(position);
    }

    protected void setItems(List<ListItem> items) {
        mAdapter.setRecyclableItems(items);
        mAdapter.notifyDataSetChanged();
    }

    protected void addHeader(View view) {
        mAdapter.addItem(new ContainerItem(view), 0);
        mAdapter.notifyDataSetChanged();
    }

    protected void removeHeader() {
        mAdapter.removeItem(0);
        mAdapter.notifyDataSetChanged();
    }

    protected void addFooter(View view) {
        int lastIndex = mAdapter.getItemCount();
        mAdapter.addItem(new ContainerItem(view), lastIndex);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(lastIndex);
    }

    protected void removeFooter() {
        int lastIndex = mAdapter.getItemCount() - 1;
        mAdapter.removeItem(lastIndex);
        mAdapter.notifyDataSetChanged();
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

    protected View getEmptyLayoutId() {
        return LayoutInflater.from(getContext()).inflate(R.layout.c_view_empty_list, null, true);
    }

    protected View getErrorLayoutId() {
        return LayoutInflater.from(getContext()).inflate(R.layout.c_view_error_list, null, true);
    }

    protected View getProgressLayoutId() {
        return LayoutInflater.from(getContext()).inflate(R.layout.c_view_progress_list, null, true);
    }

    protected void onRetryButtonClicked() {

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRetryButton.setOnClickListener(null);
    }

}
