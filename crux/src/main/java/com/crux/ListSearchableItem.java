package com.crux;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public interface ListSearchableItem extends ListItem{

    boolean matches(String searchText);

}
