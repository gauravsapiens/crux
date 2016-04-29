package com.crux.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.size() == 0);
    }

    public static int size(Collection collection) {
        if(isEmpty(collection)){
            return 0;
        }
        return collection.size();
    }

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }

    public static <E> ArrayList<E> newArrayListWithExpectedCapacity(int capacity) {
        return new ArrayList<E>(capacity);
    }

}
