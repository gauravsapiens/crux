package com.crux;

/**
 * A class that defines menu items used in drawer. Used in {@link com.crux.activity.DrawerActivity}
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public class DrawerMenu {

    private int index;
    private int groupIndex;
    private String title;
    private int imageResourceId;
    private Class intentClass;

    public DrawerMenu(int index, int groupIndex, String title, int imageResourceId, Class intentClass) {
        this.index = index;
        this.groupIndex = groupIndex;
        this.title = title;
        this.imageResourceId = imageResourceId;
        this.intentClass = intentClass;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public Class getIntentClass() {
        return intentClass;
    }

    public void setIntentClass(Class intentClass) {
        this.intentClass = intentClass;
    }
}
