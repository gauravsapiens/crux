package com.example.crux.database;

import com.crux.database.BaseDao;
import com.example.crux.bean.CartElement;

/**
 * @author gauravarora
 * @since 30/04/16.
 */
public class CartDao extends BaseDao<CartElement> {

    public CartDao() {
        super(CartElement.class);
    }

    protected String getIdFieldName() {
        return "productId";
    }

}
