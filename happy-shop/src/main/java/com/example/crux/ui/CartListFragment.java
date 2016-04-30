package com.example.crux.ui;

import com.crux.ListItem;
import com.crux.fragment.BaseMutableListFragment;
import com.crux.util.CollectionUtils;
import com.example.crux.bean.CartElement;
import com.example.crux.database.CartDao;
import com.example.crux.view.item.CartItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author gauravarora
 * @since 29/04/16.
 */
public class CartListFragment extends BaseMutableListFragment {

    @Override
    protected List<ListItem> loadListItems(Mode mode, Object extras) {
        Collection<CartElement> cartElements = getData();
        if (CollectionUtils.isEmpty(cartElements)) {
            return CollectionUtils.newArrayList();
        }

        List<ListItem> items = new ArrayList<>();
        for (CartElement cartElement : cartElements) {
            items.add(new CartItem(cartElement));
        }
        return items;
    }

    @Override
    public void onItemClicked(ListItem item, long position) {

    }

    protected Collection<CartElement> getData() {
        return new CartDao().findAll();
    }

}
