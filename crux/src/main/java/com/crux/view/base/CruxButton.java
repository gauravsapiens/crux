package com.crux.view.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.crux.Crux;
import com.crux.R;
import com.crux.util.ResourceUtils;

/**
 * A base class for creating buttons. Applies font passed by {@link R.styleable#CruxTextView_font} attribute or uses values passed in
 * {@link com.crux.Configuration} as default
 *
 * @author gauravarora
 * @since 27/04/16.
 */
public class CruxButton extends AppCompatButton {

    public CruxButton(Context context) {
        super(context);
        init(null);
    }

    public CruxButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CruxButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    protected void setFont(String fontFileName) {
        String fontPath = ResourceUtils.getFontFileAbsolutePath(fontFileName);
        if (ResourceUtils.doesAssetExists(fontPath)) {
            Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontPath);
            setTypeface(myTypeface);
        }
    }

    private void init(AttributeSet attrs) {
        String font = getDefaultFont();
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CruxTextView);
            font = typedArray.getString(R.styleable.CruxTextView_typeface);
            typedArray.recycle();
        }

        setFont(font);
    }

    private String getDefaultFont() {
        return Crux.getConfiguration().getDefaultFont();
    }

}
