package com.francesco.furlan.customizablecalendar.library.interactors;

import com.francesco.furlan.customizablecalendar.library.view.CalendarView;
import com.francesco.furlan.customizablecalendar.library.view.HeaderView;
import com.francesco.furlan.customizablecalendar.library.view.SubView;
import com.francesco.furlan.customizablecalendar.library.view.WeekDaysView;

/**
 * Created by francescofurlan on 30/06/17.
 */

public interface ViewInjector {
    void injectCalendarView(CalendarView calendarView);

    void injectHeaderView(HeaderView headerView);

    void injectSubView(SubView subView);

    void injectWeekDaysView(WeekDaysView weekDaysView);
}
