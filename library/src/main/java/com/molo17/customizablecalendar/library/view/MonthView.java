package com.molo17.customizablecalendar.library.view;


import org.joda.time.DateTime;

/**
 * Created by francescofurlan on 23/06/17.
 */

public interface MonthView extends BaseView {
    void setSelected(DateTime dateSelected);

    void refreshDays();

    void unsubscribe();
}
