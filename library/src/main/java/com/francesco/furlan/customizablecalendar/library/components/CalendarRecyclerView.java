package com.francesco.furlan.customizablecalendar.library.components;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by francescofurlan on 23/06/17.
 */

public class CalendarRecyclerView extends RecyclerView {
    private Context context;

    public CalendarRecyclerView(Context context) {
        this(context, null);
    }

    public CalendarRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
    }
}