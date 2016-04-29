package com.crux.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Utilities for {@link Toast} & {@link Snackbar}
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public class ToastUtils {

    public static void showSnackBar(View rootView, String text) {
        Snackbar.make(rootView, text, Snackbar.LENGTH_LONG).show();
    }

    public static void showError(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showError(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
