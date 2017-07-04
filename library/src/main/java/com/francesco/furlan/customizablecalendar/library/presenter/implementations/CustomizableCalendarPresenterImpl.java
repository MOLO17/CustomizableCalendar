package com.francesco.furlan.customizablecalendar.library.presenter.implementations;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;

import com.francesco.furlan.customizablecalendar.library.interactors.AUCalendar;
import com.francesco.furlan.customizablecalendar.library.interactors.ViewInteractor;
import com.francesco.furlan.customizablecalendar.library.model.CalendarFields;
import com.francesco.furlan.customizablecalendar.library.presenter.interfeaces.CustomizableCalendarPresenter;
import com.francesco.furlan.customizablecalendar.library.view.CalendarView;
import com.francesco.furlan.customizablecalendar.library.view.CustomizableCalendarView;
import com.francesco.furlan.customizablecalendar.library.view.HeaderView;
import com.francesco.furlan.customizablecalendar.library.view.SubView;
import com.francesco.furlan.customizablecalendar.library.view.WeekDaysView;

import org.joda.time.DateTime;

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
                            if (changeSet.isFieldChanged(CalendarFields.CURRENT_MONTH)) {
                                onCurrentMonthChanged(calendar.getCurrentMonth());
                            }
                            if (changeSet.isFieldChanged(CalendarFields.FIRST_DAY_OF_WEEK)) {
                                if (weekDaysView != null) {
                                    weekDaysView.onFirstDayOfWeek(calendar.getFirstDayOfWeek());
                                }
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
    public void setCustomizableCalendarLayoutResId(@LayoutRes int resId) {
        if (view != null) {
            view.setLayoutResId(resId);
        }
    }

    @Override
    public void setHeaderLayoutResId(@LayoutRes int resId) {
        if (headerView != null) {
            headerView.setLayoutResId(resId);
        }
    }

    @Override
    public void setWeekDayResId(@LayoutRes int resId) {
        if (weekDaysView != null) {
            weekDaysView.setLayoutResId(resId);
        }
    }

    @Override
    public void setSubViewResId(@LayoutRes int resId) {
        if (subView != null) {
            subView.setLayoutResId(resId);
        }
    }

    @Override
    public void setMonthResId(@LayoutRes int resId) {
        if (calendarView != null) {
            calendarView.setMonthLayoutResId(resId);
        }
    }

    @Override
    public void setDayResId(@LayoutRes int resId) {
        if (calendarView != null) {
            calendarView.setDayLayoutResId(resId);
        }
    }
}
