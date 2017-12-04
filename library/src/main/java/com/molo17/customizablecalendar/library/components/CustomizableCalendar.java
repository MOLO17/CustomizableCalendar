package com.molo17.customizablecalendar.library.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.molo17.customizablecalendar.library.R;
import com.molo17.customizablecalendar.library.interactors.ViewInteractor;
import com.molo17.customizablecalendar.library.presenter.implementations.CustomizableCalendarPresenterImpl;
import com.molo17.customizablecalendar.library.presenter.interfeaces.CustomizableCalendarPresenter;
import com.molo17.customizablecalendar.library.view.CustomizableCalendarView;

/**
 * Created by francescofurlan on 23/06/17.
 */

public class CustomizableCalendar extends LinearLayout implements CustomizableCalendarView {

    HeaderView headerView;

    WeekDaysView weekDaysView;

    SubView subView;

    CalendarRecyclerView calendarRecyclerView;

    CustomizableCalendarPresenter presenter;

    private int layoutResId = -1;

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
        if (presenter == null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomizableCalendar);
            presenter = new CustomizableCalendarPresenterImpl();
            presenter.setView(this);
            layoutResId = R.layout.customizable_calendar;
            if (typedArray != null) {
                layoutResId = typedArray.getResourceId(R.styleable.CustomizableCalendar_layout, R.layout.customizable_calendar);
                typedArray.recycle();
            }

            LayoutInflater.from(context).inflate(layoutResId, this);
            headerView = (HeaderView) findViewById(android.R.id.primary);
            weekDaysView = (WeekDaysView) findViewById(android.R.id.text1);
            subView = (SubView) findViewById(android.R.id.text2);
            calendarRecyclerView = (CalendarRecyclerView) findViewById(android.R.id.content);
        }
    }

    public CustomizableCalendarPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void injectViewInteractor(ViewInteractor viewInteractor) {
        viewInteractor.onCustomizableCalendarBindView(this);
        presenter.injectViewInteractor(viewInteractor);
        headerView.injectPresenter(presenter);
        calendarRecyclerView.injectPresenter(presenter);
        subView.injectPresenter(presenter);
        weekDaysView.injectPresenter(presenter);
    }

    @Override
    public void injectPresenter(CustomizableCalendarPresenter presenter) {
        this.presenter = presenter;
        presenter.setView(this);
    }

    @Override
    public void onCurrentMonthChanged(String month) {
        subView.onMonthChanged(month);
    }

    @Override
    public void refreshData() {

    }

    @Override
    public void setLayoutResId(int layoutResId) {
        this.layoutResId = layoutResId;
    }
}