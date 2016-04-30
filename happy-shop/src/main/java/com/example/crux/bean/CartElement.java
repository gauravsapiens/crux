package com.example.crux.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author gauravarora
 * @since 29/04/16.
 */
@Table(name = "CartItem")
public class CartElement extends Model {

    @Column(name = "productId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String id;

    @Column
    private Product product;

    @Column
    private int quantity;

    public CartElement() {
    }

    public CartElement(String id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
