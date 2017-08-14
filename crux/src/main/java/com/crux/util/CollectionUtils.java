package com.crux.util;

import com.crux.Transformer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Utils for {@link Collection}
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.size() == 0);
    }

    public static int size(Collection collection) {
        if (isEmpty(collection)) {
            return 0;
        }
        return collection.size();
    }

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    public static <E> ArrayList<E> newArrayListWithExpectedCapacity(int capacity) {
        return new ArrayList<>(capacity);
    }

    public static <From, To> Collection<To> transform(Collection<From> collection, Transformer<From, To> transformer) {
        if (isEmpty(collection) || transformer == null) {
            return newArrayList();
        }

        Collection<To> toCollection = new ArrayList<>();
        for (From element : collection) {
            toCollection.add(transformer.transform(element));
        }
        return toCollection;
    }

}
