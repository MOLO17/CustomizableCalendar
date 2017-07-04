package com.francesco.furlan.customizablecalendar.library.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.francesco.furlan.customizablecalendar.library.R;

import butterknife.InjectView;

/**
 * Created by francescofurlan on 23/06/17.
 */

public class CustomizableCalendar extends LinearLayout {

    @InjectView(android.R.id.primary)
    HeaderView headerView;

    @InjectView(android.R.id.text1)
    WeekDaysView weekDaysView;

    @InjectView(android.R.id.text2)
    SubView subView;

    @InjectView(android.R.id.content)
    CalendarRecyclerView calendarRecyclerView;

    public CustomizableCalendar(Context context) {
        this(context, null);
    }

    public CustomizableCalendar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomizableCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomizableCalendar);
        if (typedArray != null) {
            typedArray.recycle();
        }
    }
}