package com.crux.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import com.crux.ListItem;
import com.crux.ListSearchableItem;
import com.crux.OnItemClickListener;
import com.crux.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An adapter for rendering {@link ListItem} in {@link RecyclerView}
 *
 * @author gauravarora
 * @see com.crux.fragment.BaseListFragment
 * @since 27/04/16.
 */
public class ListAdapter extends RecyclerView.Adapter {

    private OnItemClickListener mOnItemClickListener;
    private List<ListItem> mOriginalItems;
    private List<ListItem> mItems;
    private Map<Class, Integer> itemClassVsType;
    private Map<Integer, ListItem> typeVsItem;
    private Map<Integer, Class> positionVsClassType;
    private Context mContext;
    private Filter mFilter;

    public ListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ListItem item = typeVsItem.get(viewType);
        return item.onCreateViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ListItem item = getItem(i);
        if (item == null) {
            return;
        }
        item.onBindViewHolder(viewHolder, i, mContext);
        viewHolder.itemView.setOnClickListener(getOnClickListener(item, i));
    }

    @Override
    public int getItemViewType(int position) {
        Class clazz = positionVsClassType.get(position);
        return itemClassVsType.get(clazz);
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Class getItemClass(int position) {
        return positionVsClassType.get(position);
    }

    public ListItem getItem(int position) {
        if (mItems != null && position >= 0 && position < mItems.size()) {
            return mItems.get(position);
        }
        return null;
    }

    public void setRecyclableItems(List<ListItem> recyclableItems) {
        this.mItems = recyclableItems;
        this.mOriginalItems = recyclableItems;
        initializeItemsWithType();
    }

    public void addItem(ListItem item, int position) {
        mItems.add(position, item);
        initializeItemsWithType();
    }

    public void removeItem(int position) {
        mItems.remove(position);
        initializeItemsWithType();
    }

    public void addItems(List<ListItem> items) {
        mItems.addAll(items);
        initializeItemsWithType();
    }

    public void setOnItemClickListener(OnItemClickListener onRecyclableItemListener) {
        mOnItemClickListener = onRecyclableItemListener;
    }

    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ItemFilter();
        }
        return mFilter;
    }

    private void initializeItemsWithType() {
        itemClassVsType = new HashMap<>();
        typeVsItem = new HashMap<>();
        positionVsClassType = new HashMap<>();

        if (CollectionUtils.isEmpty(mItems)) {
            return;
        }

        int itemIndex = 0;
        int typeIndex = 1;
        for (ListItem item : mItems) {
            if (!itemClassVsType.containsKey(item.getClass())) {
                itemClassVsType.put(item.getClass(), typeIndex);
                typeVsItem.put(typeIndex++, item);
            }
            positionVsClassType.put(itemIndex++, item.getClass());
        }
    }

    private View.OnClickListener getOnClickListener(ListItem itemz, int position) {
        return v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onRecyclableItemClicked(itemz, position);
            }
        };
    }

    private List<ListItem> getFilteredItems(List<ListItem> items, String searchText) {
        if (CollectionUtils.isEmpty(items)) {
            return null;
        }

        List<ListItem> filteredItems = new ArrayList<>();
        for (ListItem item : items) {
            if (item instanceof ListSearchableItem) {
                boolean matches = ((ListSearchableItem) item).matches(searchText);
                if (matches) {
                    filteredItems.add(item);
                }
            }
        }

        return filteredItems;
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ListItem> items = new ArrayList<>(mOriginalItems);

            if (constraint == null || constraint.length() == 0) {
                results.values = items;
                results.count = items.size();
                return results;
            }

            String searchText = constraint.toString();
            List<ListItem> filteredItems = getFilteredItems(items, searchText);

            results.values = filteredItems;
            results.count = filteredItems.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mItems = (List<ListItem>) filterResults.values;
            notifyDataSetChanged();
        }
    }


}
