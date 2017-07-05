package com.molo17.customizablecalendar.library.view;

import android.support.annotation.LayoutRes;

/**
 * Created by francescofurlan on 03/07/17.
 */

public interface CalendarView extends BaseView {
    void setMonthLayoutResId(@LayoutRes int layoutResId);

    void setDayLayoutResId(@LayoutRes int layoutResId);
}
