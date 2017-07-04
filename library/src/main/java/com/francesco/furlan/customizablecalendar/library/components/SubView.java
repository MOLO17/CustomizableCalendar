package com.francesco.furlan.customizablecalendar.library.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by francescofurlan on 23/06/17.
 */

public class SubView extends RelativeLayout {
    public SubView(Context context) {
        this(context, null);
    }

    public SubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
    }
}
