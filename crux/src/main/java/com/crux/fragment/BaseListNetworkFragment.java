package com.crux.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.crux.Callback;
import com.crux.ListItem;
import com.crux.R;
import com.crux.util.CollectionUtils;
import com.crux.view.item.ContainerItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A list fragment that takes care of loading data from network
 *
 * @author gauravarora
 * @since 14/08/17.
 */

public abstract class BaseListNetworkFragment<Response, ListData> extends BaseMutableListFragment {

    protected List<ListData> mData = new ArrayList<>();

    protected Response mLastTopPaginationResponse;
    protected Response mLastBottomPaginationResponse;
    private int pageTop = 0;
    private int pageBottom = 0;

    private Map<RequestType, RequestCallback> mCurrentRequests = new HashMap<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData(new RequestCallback(RequestType.NEW), RequestType.NEW, 0);
    }

    @Override
    public List<ListItem> loadListItemsInBackground() {
        List<ListItem> items = new ArrayList<>();
        if (CollectionUtils.isEmpty(mData)) {
            return items;
        }
        return toListItems(new ArrayList<>(mData));
    }

    @Override
    protected void onTopReached() {
        if (!isTopPaginationEnabled() || mCurrentRequests.containsKey(RequestType.PAGINATION_TOP) || !canPaginateTop()) {
            return;
        }
        addHeader(getLoadingFooterView());
        loadData(RequestType.PAGINATION_TOP, ++pageTop);
    }

    @Override
    protected void onEndReached() {
        if (!isBottomPaginationEnabled() || mCurrentRequests.containsKey(RequestType.PAGINATION_BOTTOM) || !canPaginateBottom()) {
            return;
        }
        addFooter(getLoadingFooterView());
        loadData(RequestType.PAGINATION_BOTTOM, ++pageBottom);
    }

    @Override
    protected View getLoadingFooterView() {
        LayoutInflater inflater = LayoutInflater.from(this.getActivity());
        return inflater.inflate(R.layout.c_loading_view, this.mRecyclerView, false);
    }

    protected boolean isTopPaginationEnabled() {
        return false;
    }

    protected boolean isBottomPaginationEnabled() {
        return true;
    }

    protected boolean canPaginateTop() {
        return false;
    }

    protected boolean canPaginateBottom() {
        return false;
    }

    protected void refreshData() {
        if (mCurrentRequests.containsKey(RequestType.NEW)) {
            return;
        }
        loadData(RequestType.NEW, 0);
    }

    protected void clearData() {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.clear();
    }

    protected List<ListData> getData() {
        return mData;
    }

    protected void onEmptyData() {

    }

    protected void onRequestFailure(Exception ex) {

    }

    protected abstract void loadData(Callback<Response> callback, RequestType requestType, int page);

    protected abstract List<ListData> transform(Response response);

    protected abstract List<ListItem> toListItems(List<ListData> data);

    protected void removeTopPaginationLoader() {
        ListItem item = mAdapter.getItem(0);
        if (item == null) {
            return;
        }

        if (item instanceof ContainerItem) {
            removeHeader();
        }
    }

    protected void removeBottomPaginationLoader() {
        ListItem item = mAdapter.getItem(mAdapter.getItemCount() - 1);
        if (item == null) {
            return;
        }

        if (item instanceof ContainerItem) {
            removeFooter();
        }
    }

    protected void appendItems(List<ListData> data) {
        mData = data;

        List<ListItem> newItems = toListItems(data);
        setItems(newItems);
    }

    protected void appendItemsAtTop(List<ListData> data) {
        mData.addAll(0, data);

        List<ListItem> newItems = toListItems(data);
        addItems(newItems, 0);
    }

    protected void appendItemAtBottom(List<ListData> data) {
        mData.addAll(mData.size(), data);

        List<ListItem> newItems = toListItems(data);
        addItems(newItems, mAdapter.getItemCount());
    }

    private void loadData(RequestType requestType, int page) {
        RequestCallback callback = new RequestCallback(requestType);
        mCurrentRequests.put(requestType, callback);

        loadData(callback, requestType, page);
    }

    private void handleEmptyData() {
        setViewMode(ViewMode.EMPTY);
        onEmptyData();
    }

    public enum RequestType {
        NEW, PAGINATION_BOTTOM, PAGINATION_TOP
    }

    private class RequestCallback implements Callback<Response> {

        private RequestType mRequestType;

        RequestCallback(RequestType requestType) {
            this.mRequestType = requestType;
        }

        @Override
        public void onSuccess(Response response) {
            if (!isAdded()) {
                return;
            }

            //Remove callback
            mCurrentRequests.remove(mRequestType);

            //Handle empty data
            if ((response == null || CollectionUtils.isEmpty(transform(response))) && mRequestType == RequestType.NEW) {
                handleEmptyData();
                return;
            }

            //Update view mode to normal
            setViewMode(ViewMode.NORMAL);

            //Update data & items
            List<ListData> responseList = transform(response);
            switch (mRequestType) {
                case NEW:
                    mLastBottomPaginationResponse = response;
                    appendItems(responseList);
                    onLoadFinished();
                    break;

                case PAGINATION_TOP:
                    mLastTopPaginationResponse = response;
                    removeTopPaginationLoader();
                    if (!CollectionUtils.isEmpty(responseList)) {
                        appendItemsAtTop(responseList);
                    }
                    break;

                case PAGINATION_BOTTOM:
                    mLastBottomPaginationResponse = response;
                    removeBottomPaginationLoader();
                    if (!CollectionUtils.isEmpty(responseList)) {
                        appendItemAtBottom(responseList);
                    }
                    break;
            }
        }

        @Override
        public void onFailure(Exception cause) {
            if (!isAdded()) {
                return;
            }
            mCurrentRequests.remove(mRequestType);

            switch (mRequestType) {
                case NEW:
                    setViewMode(ViewMode.ERROR);
                    break;
                case PAGINATION_TOP:
                    removeTopPaginationLoader();
                    break;
                case PAGINATION_BOTTOM:
                    removeBottomPaginationLoader();
                    break;
            }

            onRequestFailure(cause);
        }

    }

}
