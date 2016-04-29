package com.crux.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Utils for {@link Map}
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public class MapUtils {

    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return !isEmpty(map);
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.size() == 0;
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    public static <K, V> HashMap<K, V> newHashMapWithExpectedCapacity(int capacity) {
        return new HashMap<K, V>(capacity);
    }

}
