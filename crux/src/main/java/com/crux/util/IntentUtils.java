package com.crux.util;

import android.content.Intent;

/**
 * Utils associated with {@link Intent}
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public class IntentUtils {

    public static void makeIntentNode(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

}
