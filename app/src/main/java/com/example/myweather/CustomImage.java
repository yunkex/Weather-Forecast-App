package com.example.myweather;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class CustomImage extends AppCompatImageView {
    private int mResId;

    public CustomImage(Context context) {
        this(context, null, 0);
    }

    public CustomImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            String namespace = "http://schemas.android.com/apk/res/android";
            String attribute = "src";
            mResId = attrs.getAttributeResourceValue(namespace, attribute, 0);
        }
    }

    public int getImageId() {
        return mResId;
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mResId = resId;
    }
}
