package com.example.crux.database;

import com.activeandroid.serializer.TypeSerializer;
import com.example.crux.bean.Product;
import com.google.gson.Gson;

/**
 * @author gauravarora
 * @since 30/04/16.
 */
public class ProductSerializer extends TypeSerializer {

    private Gson gson = new Gson();

    @Override
    public Class<?> getDeserializedType() {
        return Product.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }

    @Override
    public String serialize(Object data) {
        return gson.toJson(data);
    }

    @Override
    public Product deserialize(Object data) {
        return gson.fromJson((String) data, Product.class);
    }
}
