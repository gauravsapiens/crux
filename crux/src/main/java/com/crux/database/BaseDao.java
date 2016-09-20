package com.crux.database;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.crux.util.CollectionUtils;
import com.crux.util.StringUtils;

import java.util.Collection;

/**
 * Base implementation of {@link Dao} using ActiveAndroid <a href="https://github.com/pardom/ActiveAndroid</a>
 *
 * @author gauravarora
 * @since 29/04/16.
 */
public class BaseDao<T extends Model> implements Dao<T> {

    private Class<T> mClass;

    public BaseDao(Class<T> clazz) {
        mClass = clazz;
    }

    @Override
    public T create(T object) {
        return update(object);
    }

    @Override
    public Collection<T> create(Collection<T> objects) {
        return update(objects);
    }

    @Override
    public T update(T object) {
        object.save();
        return object;
    }

    @Override
    public Collection<T> update(Collection<T> objects) {
        if (CollectionUtils.isEmpty(objects)) {
            return null;
        }

        ActiveAndroid.beginTransaction();
        try {
            for (T object : objects) {
                object.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        return objects;
    }

    @Override
    public T createOrUpdate(T object) {
        object.save();
        return object;
    }

    @Override
    public void delete(String objectId) {
        new Delete()
                .from(getBeanClass())
                .where(getIdFieldName() + "=?", objectId)
                .execute();
    }

    @Override
    public void deleteAll() {
        new Delete()
                .from(getBeanClass())
                .execute();
    }

    @Override
    public T findById(String objectId) {
        return new Select()
                .from(getBeanClass())
                .where(getIdFieldName() + "=?", objectId)
                .executeSingle();
    }

    @Override
    public Collection<T> findByIds(Collection<String> id) {
        return new Select()
                .from(getBeanClass())
                .where(getIdFieldName() + " IN ", StringUtils.delimiter(id, ','))
                .execute();
    }

    @Override
    public Collection<T> findAll() {
        return new Select()
                .all()
                .from(getBeanClass())
                .execute();
    }

    @Override
    public int count() {
        return findAll().size();
    }

    protected String getIdFieldName() {
        return "id";
    }

    private Class<T> getBeanClass() {
        return mClass;
    }

}
