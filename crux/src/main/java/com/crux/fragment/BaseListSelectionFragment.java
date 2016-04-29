package com.crux.fragment;

import android.os.Bundle;

import com.crux.ListItem;
import com.crux.ListSelectableItem;
import com.crux.util.CollectionUtils;
import com.crux.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.crux.R;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public abstract class BaseListSelectionFragment extends BaseListSearchFragment{

    public interface Callbacks {
        public void onItemSelected(BaseListSelectionFragment fragment, List<Object> selectedValues);
    }

    private boolean isMultiSelectionAllowed = false;
    private boolean isZeroSelectionAllowed = false;
    private ListSelectableItem lastItemSelected;
    private Set<ListSelectableItem> selectedItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedItems = new HashSet<>();
    }

    @Override
    public void onItemClicked(ListItem recyclableItem, long position) {
        if (recyclableItem instanceof ListSelectableItem) {
            onItemSelected((ListSelectableItem) recyclableItem);
            getAdapter().notifyDataSetChanged();
        } else {
            onUnselectableItemClicked(recyclableItem);
        }
    }

    protected void onItemSelected(ListSelectableItem selectableItem) {
        boolean selected = selectableItem.isSelected();

        if (isMultiSelectionAllowed()) {
            if (selected) {
                if (selectedItems.contains(selectableItem)) {
                    selectedItems.remove(selectableItem);
                }
                selectableItem.setSelected(false);
            } else {
                selectableItem.setSelected(true);
                selectedItems.add(selectableItem);
            }
        } else {
            if (selected) {
                if (selectedItems.contains(selectableItem)) {
                    selectedItems.remove(selectableItem);
                }
                selectableItem.setSelected(false);
                lastItemSelected = null;
            } else {
                selectableItem.setSelected(true);
                selectedItems.add(selectableItem);

                if (lastItemSelected != null) {
                    lastItemSelected.setSelected(false);
                    selectedItems.remove(lastItemSelected);
                }

                lastItemSelected = selectableItem;
            }
        }
    }

    @Override
    protected void onActionBarItemDoneClicked() {
        super.onActionBarItemDoneClicked();

        Set<ListSelectableItem> selectedItems = getSelectedItems();
        int errResId = validate(selectedItems);
        if (errResId > 0) {
            ToastUtils.showError(getActivity(), errResId);
        } else {
            notifyListener();
        }
    }

    private void notifyListener() {
        Object callbacks = getCallbacks(Callbacks.class);
        if (callbacks != null) {
            ((Callbacks) callbacks).onItemSelected(this, getSelectedValues());
        }
    }

    private List<Object> getSelectedValues() {
        if (CollectionUtils.isEmpty(selectedItems)) {
            return null;
        }

        List<Object> objects = new ArrayList<>();
        for (ListSelectableItem item : selectedItems) {
            objects.add(item.getUserInfo());
        }
        return objects;
    }

    protected int validate(Set<ListSelectableItem> selectedItems) {
        if (!isZeroSelectionAllowed() && selectedItems.size() == 0)
            return R.string.error_empty_selection_not_allowed;
        return -1;
    }

    protected void onUnselectableItemClicked(ListItem clickedItem) {

    }

    protected Set<ListSelectableItem> getSelectedItems() {
        return selectedItems;
    }

    protected boolean isMultiSelectionAllowed() {
        return isMultiSelectionAllowed;
    }

    protected boolean isZeroSelectionAllowed() {
        return isZeroSelectionAllowed;
    }

    protected void setMultiSelectionAllowed(boolean multiSelectionAllowed) {
        isMultiSelectionAllowed = multiSelectionAllowed;
    }

    protected void setZeroSelectionAllowed(boolean zeroSelectionAllowed) {
        isZeroSelectionAllowed = zeroSelectionAllowed;
    }

    protected void unSelectAll() {
        if (!isZeroSelectionAllowed()) {
            return;
        }

        for (ListSelectableItem selectedItem : selectedItems) {
            selectedItem.setSelected(false);
        }

        selectedItems.clear();
    }

}
