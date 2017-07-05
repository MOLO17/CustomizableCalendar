package com.molo17.customizablecalendar.library.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.molo17.customizablecalendar.library.model.CalendarFields.CURRENT_MONTH;
import static com.molo17.customizablecalendar.library.model.CalendarFields.FIRST_DAY_OF_WEEK;
import static com.molo17.customizablecalendar.library.model.CalendarFields.FIRST_SELECTED_DAY;
import static com.molo17.customizablecalendar.library.model.CalendarFields.LAST_SELECTED_DAY;
import static com.molo17.customizablecalendar.library.model.CalendarFields.MONTHS;
import static com.molo17.customizablecalendar.library.model.CalendarFields.MULTIPLE_SELECTION;

/**
 * Created by francescofurlan on 30/06/17.
 */

@StringDef({
        FIRST_SELECTED_DAY,
        LAST_SELECTED_DAY,
        CURRENT_MONTH,
        MONTHS,
        MULTIPLE_SELECTION,
        FIRST_DAY_OF_WEEK
})

@Retention(RetentionPolicy.SOURCE)
public @interface CalendarFields {
    String FIRST_SELECTED_DAY = "firstSelectedDay";
    String LAST_SELECTED_DAY = "lastSelectedDay";
    String CURRENT_MONTH = "currentMonth";
    String MONTHS = "months";
    String MULTIPLE_SELECTION = "multipleSelection";
    String FIRST_DAY_OF_WEEK = "firstDayOfWeek";
}