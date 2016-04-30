package com.example.crux.api.dto;

import com.example.crux.bean.Product;

import java.util.List;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class ProductResponse {

    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
