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

    Collection<T> create(Collection<T> objects);

    T update(T object);

    Collection<T> update(Collection<T> objects);

    T createOrUpdate(T object);

    void delete(String objectId);

    void deleteAll();

    T findById(String objectId);

    Collection<T> findByIds(Collection<String> id);

    Collection<T> findAll();

    int count();

}
