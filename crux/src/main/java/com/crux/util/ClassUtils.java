package com.crux.util;

/**
 * Utils associated with class
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public class ClassUtils {

    public static boolean isSubclass(Class parentClass, Class clazz) {
        return !(parentClass == null || clazz == null) && clazz.isAssignableFrom(parentClass);
    }

}
