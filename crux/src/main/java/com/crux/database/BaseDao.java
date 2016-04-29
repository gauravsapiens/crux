package com.crux.database;

import com.crux.util.StringUtils;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Collection;

/**
 * Base implementation of {@link Dao} using DBFlow <a href="https://github.com/Raizlabs/DBFlow</a>
 *
 * @author gauravarora
 * @since 29/04/16.
 */
public class BaseDao<T extends BaseModel> implements Dao<T> {

    private Class<T> mClass;

    public BaseDao(Class<T> clazz) {
        mClass = clazz;
    }

    @Override
    public T create(T object) {
        object.save();
        return object;
    }

    @Override
    public T update(T object) {
        object.update();
        return object;
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
                .queryClose();
    }

    @Override
    public T findById(String objectId) {
        return new Select()
                .from(getBeanClass())
                .where(getIdFieldName() + "=?", objectId)
                .querySingle();
    }

    @Override
    public Collection<T> findByIds(Collection<String> id) {
        return new Select()
                .from(getBeanClass())
                .where(getIdFieldName() + " IN ", StringUtils.delimiter(id, ','))
                .queryList();
    }

    @Override
    public Collection<T> findAll() {
        return new Select()
                .all()
                .from(getBeanClass())
                .queryList();
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
