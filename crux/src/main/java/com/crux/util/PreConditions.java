package com.crux.util;

/**
 * Utility for validating preconditons
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public class PreConditions {

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference, String errorMessage) {
        if (reference == null) {
            throw new NullPointerException(errorMessage);
        }
        return reference;
    }

    public static int checkIndex(int index, int size) {
        return checkIndex(index, 0, size);
    }

    public static int checkIndex(int index, int start, int end) {
        if (index < start || index > end) {
            throw new IndexOutOfBoundsException();
        }
        return index;
    }

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

}
