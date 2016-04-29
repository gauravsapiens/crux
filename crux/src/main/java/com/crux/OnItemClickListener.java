package com.crux;

/**
 * Callback that is invoked when a {@link ListItem} is clicked
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public interface OnItemClickListener {

    void onRecyclableItemClicked(ListItem item, int position);

}
