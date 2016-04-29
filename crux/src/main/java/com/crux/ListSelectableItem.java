package com.crux;

/**
 * An extension of {@link ListSearchableItem} that supports selection
 *
 * @author gauravarora
 * @see com.crux.fragment.BaseListSelectionFragment
 * @since 27/04/16.
 */
public interface ListSelectableItem extends ListSearchableItem {

    void setSelected(boolean selected);

    boolean isSelected();

}
