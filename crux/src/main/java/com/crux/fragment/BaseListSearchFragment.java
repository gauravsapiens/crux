package com.crux.fragment;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.crux.R;

/**
 * An extension of {@link BaseMutableListFragment} that creates search menu & allows searching. Implement list items using
 * {@link com.crux.ListSearchableItem} to allow searching.
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public abstract class BaseListSearchFragment extends BaseMutableListFragment implements SearchView.OnQueryTextListener {

    private static final String RetainDataKeySearchText = "searchText";

    protected MenuItem mSearchMenuItem;
    private SearchView mSearchView;
    private String mSearchText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search_item_list, menu);
        mSearchMenuItem = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);

        mSearchView.setOnQueryTextListener(this);

        if (!TextUtils.isEmpty(mSearchText) && !mListShown) {
            mSearchView.setIconified(false);
            MenuItemCompat.expandActionView(mSearchMenuItem);
            mSearchView.setFocusable(true);
            mSearchView.requestFocusFromTouch();
            mSearchMenuItem.setVisible(false);
            mSearchView.setQuery(mSearchText, false);
        }

        MenuItemCompat.setOnActionExpandListener(mSearchMenuItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return mListShown;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        mSearchText = null;
                        mSearchView.setQuery(null, false);
                        return true;
                    }
                }
        );

        MenuItem doneMenuItem = menu.findItem(R.id.menu_done);
        if (isDoneButtonEnabled()) {
            doneMenuItem.setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_search) {
            return true;
        } else if (i == R.id.menu_done) {
            onActionBarItemDoneClicked();
            return true;
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (isAdded()) {
            if (mSearchView != null) {
                String searchText = mSearchView.getQuery().toString();
                if (!TextUtils.isEmpty(searchText))
                    outState.putString(RetainDataKeySearchText, searchText);
            }
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onLoadFinished() {
        super.onLoadFinished();

        if (mSearchMenuItem != null) {
            mSearchMenuItem.setVisible(true);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mSearchText = s;
        searchItems(s);
        return false;
    }

    protected void searchItems(String searchText) {
        getAdapter().getFilter().filter(searchText);
    }

    protected void onActionBarItemDoneClicked() {

    }

    protected boolean isDoneButtonEnabled() {
        return true;
    }


}
