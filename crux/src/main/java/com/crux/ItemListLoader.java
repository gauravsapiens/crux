package com.crux;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Implementation of {@link AsyncTaskLoader} that takes care of loading list of items using {@link ItemLoaderCallbacks}
 */
public class ItemListLoader<T> extends AsyncTaskLoader<List<T>> {

    private ItemLoaderCallbacks<T> mItemLoader;
    private List<T> mCurrentItemList;

    private boolean mIsLoading;

    public interface ItemLoaderCallbacks<T> {
        List<T> loadListItemsInBackground();

        List<T> deliverListItems(List<T> loadedTableItems);
    }

    public ItemListLoader(Context context) {
        super(context);
        resetItemListLoader();
    }

    /**
     * This is where the bulk of our work is done. This function is called in a
     * background thread and should generate a new set of data to be published
     * by the loader.
     */
    @Override
    public List<T> loadInBackground() {
        mIsLoading = true;

        List<T> items = null;
        if (mItemLoader != null)
            items = mItemLoader.loadListItemsInBackground();

        return items;
    }

    /**
     * Called when there is new data to deliver to the client. The super class
     * will take care of delivering it; the implementation here just adds a
     * little more logic.
     */
    @Override
    public void deliverResult(List<T> items) {
        mIsLoading = false;

        if (isReset()) {
            // An async query came in while the loader is stopped. We
            // don't need the result.
            if (items != null)
                onReleaseResources(items);
        }

        List<T> newItems = items;
        if (mItemLoader != null)
            newItems = mItemLoader.deliverListItems(items);

        List<T> oldItems = newItems;
        mCurrentItemList = newItems;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(newItems);
        }

        // release the resources associated with 'oldItems' if needed; now
        // that the new result is delivered we know that it is no longer in
        // use.
        if (oldItems != null)
            onReleaseResources(oldItems);
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
        if (mCurrentItemList != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mCurrentItemList);
        }

        if (takeContentChanged() || mCurrentItemList == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        mIsLoading = false;

        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(List<T> items) {
        super.onCanceled(items);

        // release the resources associated with items if needed.
        onReleaseResources(items);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // release the resources associated with items if needed.
        if (mCurrentItemList != null) {
            onReleaseResources(mCurrentItemList);
            mCurrentItemList = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated with an
     * actively loaded data set.
     */
    protected void onReleaseResources(List<T> items) {

    }

    public void setItemLoader(ItemLoaderCallbacks itemLoader) {
        this.mItemLoader = itemLoader;
    }

    public boolean isLoading() {
        return mIsLoading;
    }

    public void resetItemListLoader() {
        mIsLoading = true;
        mCurrentItemList = null;
    }

}
