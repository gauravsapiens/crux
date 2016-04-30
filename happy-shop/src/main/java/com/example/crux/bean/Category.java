package com.example.crux.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author gauravarora
 * @since 29/04/16.
 */
public class Category implements Parcelable {

    private String id;
    private String name;
    private int imageResource;

    public Category(String id, String name, int imageResource) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
    }

    private Category(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.imageResource = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(imageResource);
    }
}
