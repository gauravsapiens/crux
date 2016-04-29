package com.crux.util;

import java.util.Collection;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class StringUtils {

    public static boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }

    public static boolean isEmpty(String text) {
        return text == null || text.equals("");
    }

    public static String join(final Object[] array, final String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    public static String join(final Object[] array, String separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = "";
        }

        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return "";
        }

        final StringBuilder buf = new StringBuilder(noOfItems * 16);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static String toFormattedTime(long seconds) {
        int hours = (int) (seconds / 3600);
        int minutes = (int) (seconds / 60);
        if (hours > 0) {
            minutes = minutes - (hours * 60);
            seconds = seconds - (hours * 3600) - (minutes * 60);
            return hours + ":" + toDoubleDigits(minutes) + ":" + toDoubleDigits(seconds);
        } else {
            seconds = seconds - (minutes * 60);
            return minutes + ":" + toDoubleDigits(seconds);
        }
    }

    public static String delimiter(Collection collection, char delimiter) {
        if (CollectionUtils.isEmpty(collection)) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Object object : collection) {
            stringBuilder.append(object.toString()).append(delimiter);
        }
        return stringBuilder.toString();
    }

    private static String toDoubleDigits(long number) {
        return String.format("%02d", number);
    }

}
