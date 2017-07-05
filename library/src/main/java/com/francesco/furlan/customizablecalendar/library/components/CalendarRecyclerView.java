package com.francesco.furlan.customizablecalendar.library.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.AttributeSet;
import android.view.View;

import com.francesco.furlan.customizablecalendar.library.R;
import com.francesco.furlan.customizablecalendar.library.adapter.CalendarViewAdapter;
import com.francesco.furlan.customizablecalendar.library.interactors.AUCalendar;
import com.francesco.furlan.customizablecalendar.library.interactors.ViewInteractor;
import com.francesco.furlan.customizablecalendar.library.presenter.interfeaces.CustomizableCalendarPresenter;
import com.francesco.furlan.customizablecalendar.library.view.CalendarView;

import org.joda.time.DateTime;

/**
 * Created by francescofurlan on 23/06/17.
 */

public class CalendarRecyclerView extends RecyclerView implements CalendarView {
    private LinearLayoutManager linearLayoutManager;
    private CalendarViewAdapter calendarViewAdapter;
    private ViewInteractor viewInteractor;
    private Context context;
    private CustomizableCalendarPresenter presenter;
    private int currentPosition = 0;
    private int prevPosition = 0;
    private int detachPosition = 0;
    private AUCalendar calendar;
    private
    @LayoutRes
    int monthResId = R.layout.calendar_view;
    private
    @LayoutRes
    int monthCellResId = R.layout.calendar_cell;

    public CalendarRecyclerView(Context context) {
        this(context, null);
    }

    public CalendarRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomizableCalendar);
        if (typedArray != null) {
            monthResId = typedArray.getResourceId(R.styleable.CustomizableCalendar_month_layout, R.layout.calendar_view);
            monthCellResId = typedArray.getResourceId(R.styleable.CustomizableCalendar_cell_layout, R.layout.calendar_cell);
            typedArray.recycle();
        }

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        setLayoutManager(linearLayoutManager);
        this.calendar = AUCalendar.getInstance();
    }

    @Override
    public void refreshData() {
        calendarViewAdapter.refreshData();
    }

    @Override
    public void setLayoutResId(@LayoutRes int layoutResId) {

    }

    @Override
    public void injectViewInteractor(ViewInteractor viewInteractor) {
        this.viewInteractor = viewInteractor;
        if (viewInteractor != null) {
            viewInteractor.onCalendarBindView(this);
        }
        setupCalendarAdapter();
        setupCalendarScroll();
    }

    @Override
    public void injectPresenter(CustomizableCalendarPresenter presenter) {
        this.presenter = presenter;
        this.presenter.injectCalendarView(this);
    }

    private void setupCalendarAdapter() {
        calendarViewAdapter = new CalendarViewAdapter(context);
        calendarViewAdapter.injectViewInteractor(viewInteractor);
        calendarViewAdapter.setLayoutResId(monthResId);
        calendarViewAdapter.setDayLayoutResId(monthCellResId);
        setAdapter(calendarViewAdapter);
    }

    private void setupCalendarScroll() {
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(this);

        addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                prevPosition = currentPosition;
                currentPosition = getChildAdapterPosition(view);
                updateCurrentMonth();
                MonthGridView monthGridView = (MonthGridView) view;
                monthGridView.refreshData();
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                MonthGridView monthGridView = (MonthGridView) view;
                detachPosition = getChildAdapterPosition(view);
                if (detachPosition == currentPosition) {
                    currentPosition = prevPosition;
                    updateCurrentMonth();
                }
                monthGridView.unsubscribe();
            }
        });
    }

    private void updateCurrentMonth() {
        DateTime currentMonth = calendar.getMonths().get(currentPosition);
        calendar.setCurrentMonth(currentMonth);
    }

    @Override
    public void setMonthLayoutResId(@LayoutRes int layoutResId) {
        calendarViewAdapter.setLayoutResId(layoutResId);
    }

    @Override
    public void setDayLayoutResId(@LayoutRes int layoutResId) {
        calendarViewAdapter.setDayLayoutResId(layoutResId);
    }
}