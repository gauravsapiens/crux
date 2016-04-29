package com.crux.util;

import android.content.res.Resources;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class ViewUtils {

    public static <T extends View> T findView(View rootView, int id) {
        if (rootView == null) {
            return null;
        }
        return (T) rootView.findViewById(id);
    }

    public static void setVisibility(View view, boolean isVisible) {
        int visibility = isVisible ? View.VISIBLE : View.GONE;
        view.setVisibility(visibility);
    }

    public static void setText(TextView textView, String text, int visibilityIfEmpty) {
        if (StringUtils.isEmpty(text)) {
            textView.setVisibility(visibilityIfEmpty);
            return;
        }
        textView.setText(text);
    }

    public static String getText(EditText editText){
        if(editText == null){
            return null;
        }

        Editable editable = editText.getText();
        if(editable == null){
            return null;
        }
        return editable.toString();
    }

    public static int dpToPx(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    public static float pxToDp(float pixels) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = pixels / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

}
