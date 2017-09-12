package com.molo17.customizablecalendar.library.presenter.implementations;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;

import com.molo17.customizablecalendar.library.interactors.AUCalendar;
import com.molo17.customizablecalendar.library.interactors.ViewInteractor;
import com.molo17.customizablecalendar.library.model.CalendarFields;
import com.molo17.customizablecalendar.library.presenter.interfeaces.CustomizableCalendarPresenter;
import com.molo17.customizablecalendar.library.view.CalendarView;
import com.molo17.customizablecalendar.library.view.CustomizableCalendarView;
import com.molo17.customizablecalendar.library.view.HeaderView;
import com.molo17.customizablecalendar.library.view.SubView;
import com.molo17.customizablecalendar.library.view.WeekDaysView;

import org.joda.time.DateTime;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by francescofurlan on 23/06/17.
 */

public class CustomizableCalendarPresenterImpl implements CustomizableCalendarPresenter {
    private AUCalendar calendar;
    private CalendarView calendarView;
    private HeaderView headerView;
    private SubView subView;
    private WeekDaysView weekDaysView;
    private ViewInteractor viewInteractor;
    private CustomizableCalendarView view;
    private CompositeDisposable subscriptions = new CompositeDisposable();

    @Override
    public void injectViewInteractor(ViewInteractor viewInteractor) {
        this.viewInteractor = viewInteractor;
    }

    @Override
    public void setLayoutResId(@LayoutRes int layoutResId) {
        view.setLayoutResId(layoutResId);
    }

    @Override
    public void setView(CustomizableCalendarView view) {
        this.view = view;
        if (viewInteractor != null) {
            view.injectViewInteractor(viewInteractor);
        }
        calendar = AUCalendar.getInstance();
        subscriptions.add(
                calendar.observeChangesOnCalendar()
                        .subscribe(changeSet -> {
                            boolean currentMonthChanged = changeSet.isFieldChanged(CalendarFields.CURRENT_MONTH);
                            boolean firstDayOfWeekChanged = changeSet.isFieldChanged(CalendarFields.FIRST_DAY_OF_WEEK);
                            boolean firstSelectedDayChanged = changeSet.isFieldChanged(CalendarFields.FIRST_SELECTED_DAY);
                            boolean lastSelectedDayChanged = changeSet.isFieldChanged(CalendarFields.LAST_SELECTED_DAY);

                            if (currentMonthChanged) {
                                onCurrentMonthChanged(calendar.getCurrentMonth());
                            }

                            if (firstDayOfWeekChanged) {
                                if (weekDaysView != null) {
                                    weekDaysView.onFirstDayOfWeek(calendar.getFirstDayOfWeek());
                                }
                            }

                            if (firstDayOfWeekChanged || firstSelectedDayChanged || lastSelectedDayChanged) {
                                if (calendarView != null) {
                                    calendarView.refreshData();
                                }
                            }
                        })
        );
    }

    @Override
    public void onBindView(View rootView) {
        viewInteractor.onCustomizableCalendarBindView(rootView);
    }

    private void onCurrentMonthChanged(DateTime currentMonth) {
        String month = currentMonth.toString("MMMMM", Locale.getDefault());
        if (view != null && !TextUtils.isEmpty(month)) {
            String formattedMonth = month.substring(0, 1).toUpperCase() + month.substring(1);
            view.onCurrentMonthChanged(formattedMonth);
        }
    }

    @Override
    public void injectCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
        this.calendarView.injectViewInteractor(viewInteractor);
    }

    @Override
    public void injectHeaderView(HeaderView headerView) {
        this.headerView = headerView;
        this.headerView.injectViewInteractor(viewInteractor);
    }

    @Override
    public void injectSubView(SubView subView) {
        this.subView = subView;
        this.subView.injectViewInteractor(viewInteractor);
    }

    @Override
    public void injectWeekDaysView(WeekDaysView weekDaysView) {
        this.weekDaysView = weekDaysView;
        this.weekDaysView.injectViewInteractor(viewInteractor);
    }

    @Override
    public List<String> setupWeekDays() {
        String[] namesOfDays = DateFormatSymbols.getInstance(Locale.getDefault()).getShortWeekdays();
        int firstDayOfWeek = calendar.getFirstDayOfWeek();

        List<String> weekDays = new ArrayList<>();
        for (int i = firstDayOfWeek; i < namesOfDays.length; i++) {
            String nameOfDay = namesOfDays[i];
            String formattedNameOfDay = getFormattedDayOfDay(nameOfDay);
            if (formattedNameOfDay != null) {
                weekDays.add(formattedNameOfDay);
            }
        }
        for (int i = 0; i < firstDayOfWeek; i++) {
            String nameOfDay = namesOfDays[i];
            String formattedNameOfDay = getFormattedDayOfDay(nameOfDay);
            if (formattedNameOfDay != null) {
                weekDays.add(formattedNameOfDay);
            }
        }
        return weekDays;
    }

    private String getFormattedDayOfDay(String nameOfDay) {
        if (!TextUtils.isEmpty(nameOfDay)) {
            if (viewInteractor.hasImplementedWeekDayNameFormat()) {
                return viewInteractor.formatWeekDayName(nameOfDay);
            } else {
                return nameOfDay.substring(0, 1).toUpperCase();
            }
        }
        return null;
    }

//    @Override
//    public void setCustomizableCalendarLayoutResId(@LayoutRes int resId) {
//        if (view != null) {
//            view.setLayoutResId(resId);
//        }
//    }
//
//    @Override
//    public void setHeaderLayoutResId(@LayoutRes int resId) {
//        if (headerView != null) {
//            headerView.setLayoutResId(resId);
//        }
//    }
//
//    @Override
//    public void setWeekDayResId(@LayoutRes int resId) {
//        if (weekDaysView != null) {
//            weekDaysView.setLayoutResId(resId);
//        }
//    }
//
//    @Override
//    public void setSubViewResId(@LayoutRes int resId) {
//        if (subView != null) {
//            subView.setLayoutResId(resId);
//        }
//    }
//
//    @Override
//    public void setMonthResId(@LayoutRes int resId) {
//        if (calendarView != null) {
//            calendarView.setMonthLayoutResId(resId);
//        }
//    }
//
//    @Override
//    public void setDayResId(@LayoutRes int resId) {
//        if (calendarView != null) {
//            calendarView.setDayLayoutResId(resId);
//        }
//    }
}
