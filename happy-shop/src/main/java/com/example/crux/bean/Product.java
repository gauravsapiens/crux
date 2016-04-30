package com.example.crux.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class Product implements Parcelable {

    private String id;
    private String name;
    private String description;
    private String category;
    private float price;

    @SerializedName("img_url")
    private String imageUrl;

    private Product(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.category = in.readString();
        this.price = in.readFloat();
        this.imageUrl = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi at malesuada diam, a cursus augue. Mauris id urna iaculis elit elementum ultricies. Proin venenatis tempor erat sed condimentum. Proin eu lectus euismod, porttitor orci sed, mattis lectus. Sed ut euismod libero. Quisque tempor elementum lorem ac venenatis. Cras orci arcu, congue nec ultricies sit amet, viverra eget magna. Vivamus augue quam, luctus non gravida id, imperdiet nec ipsum. Duis eget mi eget odio tincidunt euismod. Pellentesque lorem risus, aliquet quis lacinia at, lacinia nec nunc.";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String img_url) {
        this.imageUrl = img_url;
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
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
        dest.writeString(category);
        dest.writeFloat(price);
        dest.writeString(imageUrl);
    }
}
