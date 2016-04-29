package com.crux;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public interface ListSelectableItem extends ListSearchableItem{

    void setSelected(boolean selected);

    boolean isSelected();

}
