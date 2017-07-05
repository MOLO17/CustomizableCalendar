package com.molo17.customizablecalendar.library.interactors;

import android.support.annotation.LayoutRes;

/**
 * Created by francescofurlan on 26/06/17.
 */

public interface LayoutInteractor {
    void setCustomizableCalendarLayoutResId(@LayoutRes int resId);

    void setHeaderLayoutResId(@LayoutRes int resId);

    void setWeekDayResId(@LayoutRes int resId);

    void setSubViewResId(@LayoutRes int resId);

    void setMonthResId(@LayoutRes int resId);

    void setDayResId(@LayoutRes int resId);
}
