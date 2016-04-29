package com.crux.database;

import java.util.Collection;

/**
 * Data Access Object that allows CRUD on object
 *
 * @author gauravarora
 * @since 29/04/16.
 */
public interface Dao<T> {

    T create(T object);

    T update(T object);

    T createOrUpdate(T object);

    void delete(String objectId);

    T findById(String objectId);

    Collection<T> findByIds(Collection<String> id);

    Collection<T> findAll();

    int count();

}
