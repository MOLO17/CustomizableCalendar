package com.francesco.furlan.customizablecalendar.library.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.francesco.furlan.customizablecalendar.library.R;

/**
 * Created by francescofurlan on 23/06/17.
 */

public class MonthGridView extends LinearLayout {
    public MonthGridView(Context context) {
        this(context, null);
    }

    public MonthGridView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomizableCalendar);
        if (typedArray != null) {
            typedArray.recycle();
        }
    }
}
