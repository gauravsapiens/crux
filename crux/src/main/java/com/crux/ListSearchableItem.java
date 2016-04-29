package com.crux;

/**
 * An extension of {@link ListItem} that supports searching
 *
 * @author gauravarora
 * @see com.crux.fragment.BaseListSearchFragment
 * @since 27/04/16.
 */
public interface ListSearchableItem extends ListItem {

    boolean matches(String searchText);

}
