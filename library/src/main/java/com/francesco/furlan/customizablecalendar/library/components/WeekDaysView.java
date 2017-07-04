package com.francesco.furlan.customizablecalendar.library.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.francesco.furlan.customizablecalendar.library.R;

/**
 * Created by francescofurlan on 23/06/17.
 */

public class WeekDaysView extends RecyclerView {
    private Context context;

    public WeekDaysView(Context context) {
        this(context, null);
    }

    public WeekDaysView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekDaysView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomizableCalendar);
        if (typedArray != null) {
            typedArray.recycle();
        }
    }
}
