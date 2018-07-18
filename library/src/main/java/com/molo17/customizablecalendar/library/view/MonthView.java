package com.molo17.customizablecalendar.library.view;


import org.threeten.bp.LocalDate;

/**
 * Created by francescofurlan on 23/06/17.
 */

public interface MonthView extends BaseView {
    void setSelected(LocalDate dateSelected);

    void refreshDays();

    void unsubscribe();
}
