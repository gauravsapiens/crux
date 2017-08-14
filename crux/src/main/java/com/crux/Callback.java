package com.crux;

/**
 * A general callback interface
 *
 * @author gauravarora
 * @since 14/08/17.
 */

public interface Callback<T> {

    void onSuccess(T t);

    void onFailure(Exception cause);

}
