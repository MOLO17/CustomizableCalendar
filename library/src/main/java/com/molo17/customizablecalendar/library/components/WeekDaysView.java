package com.molo17.customizablecalendar.library.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.molo17.customizablecalendar.library.R;
import com.molo17.customizablecalendar.library.adapter.WeekDaysViewAdapter;
import com.molo17.customizablecalendar.library.interactors.AUCalendar;
import com.molo17.customizablecalendar.library.interactors.ViewInteractor;
import com.molo17.customizablecalendar.library.presenter.interfeaces.CustomizableCalendarPresenter;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by francescofurlan on 23/06/17.
 */

public class WeekDaysView extends RecyclerView implements com.molo17.customizablecalendar.library.view.WeekDaysView {
    WeekDaysViewAdapter weekDaysViewAdapter;
    private Context context;
    private @LayoutRes int layoutResId = -1;
    private ViewInteractor viewInteractor;
    private Integer firstDayOfWeek;
    private CustomizableCalendarPresenter presenter;
    private List<String> weekDays;

    public WeekDaysView(Context context) {
        this(context, null);
    }

    public WeekDaysView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekDaysView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomizableCalendar);
        layoutResId = R.layout.week_day_view;
        if (typedArray != null) {
            layoutResId = typedArray.getResourceId(R.styleable.CustomizableCalendar_layout, R.layout.week_day_view);
            typedArray.recycle();
        }
    }

    public void setWeekDays() {
        weekDaysViewAdapter = new WeekDaysViewAdapter(context, weekDays, layoutResId, viewInteractor);
        setAdapter(weekDaysViewAdapter);
        setLayoutManager(new GridLayoutManager(context, weekDays.size()));
    }

    @Override
    public void refreshData() {

    }

    @Override
    public void setLayoutResId(@LayoutRes int layoutResId) {
        this.layoutResId = layoutResId;
    }

    @Override
    public void injectViewInteractor(ViewInteractor viewInteractor) {
        this.viewInteractor = viewInteractor;
        viewInteractor.onWeekDaysBindView(this);
        weekDays = presenter.setupWeekDays();
        updateWeekDays();
    }

    @Override
    public void injectPresenter(CustomizableCalendarPresenter presenter) {
        this.presenter = presenter;
        presenter.injectWeekDaysView(this);
        updateWeekDays();
    }

    @Override
    public void onFirstDayOfWeek(int firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    private void updateWeekDays() {
        viewInteractor.onWeekDaysBindView(this);
        weekDays = presenter.setupWeekDays();
        setWeekDays();
    }
}
