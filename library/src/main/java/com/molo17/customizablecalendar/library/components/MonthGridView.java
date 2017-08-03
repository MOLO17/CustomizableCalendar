package com.molo17.customizablecalendar.library.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.molo17.customizablecalendar.library.R;
import com.molo17.customizablecalendar.library.adapter.MonthAdapter;
import com.molo17.customizablecalendar.library.interactors.AUCalendar;
import com.molo17.customizablecalendar.library.interactors.ViewInteractor;
import com.molo17.customizablecalendar.library.model.CalendarItem;
import com.molo17.customizablecalendar.library.presenter.interfeaces.CustomizableCalendarPresenter;
import com.molo17.customizablecalendar.library.view.BaseView;

import org.joda.time.DateTime;

/**
 * Created by francescofurlan on 23/06/17.
 */

public class MonthGridView extends LinearLayout implements BaseView {
    protected DateTime monthDateTime;
    private MonthAdapter calendarAdapter;
    private GridView calendarGrid;
    private DateTime currentMonth;
    private
    @LayoutRes
    int layoutResId = -1;
    private
    @LayoutRes
    int dayLayoutResId = -1;
    private ViewInteractor viewInteractor;

    public MonthGridView(Context context) {
        this(context, null);
    }

    public MonthGridView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        monthDateTime = new DateTime();
        layoutResId = R.layout.calendar_view;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomizableCalendar);
        if (typedArray != null) {
            layoutResId = typedArray.getResourceId(R.styleable.CustomizableCalendar_month_layout, R.layout.calendar_view);
            dayLayoutResId = typedArray.getResourceId(R.styleable.CustomizableCalendar_cell_layout, R.layout.calendar_cell);
            typedArray.recycle();
        }

    }

    @Override
    public void setLayoutResId(@LayoutRes int layoutResId) {
        if (layoutResId != -1) {
            this.layoutResId = layoutResId;
        }
    }

    public void setDayLayoutResId(int dayLayoutResId) {
        if (layoutResId != -1) {
            this.dayLayoutResId = dayLayoutResId;
        }
    }

    @Override
    public void injectViewInteractor(ViewInteractor viewInteractor) {
        this.viewInteractor = viewInteractor;
        bindViews();
        setupCalendar();
    }

    @Override
    public void injectPresenter(CustomizableCalendarPresenter presenter) {

    }

    private void bindViews() {
        LinearLayout calendarLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(layoutResId, this);
        calendarGrid = (GridView) calendarLayout.findViewById(android.R.id.widget_frame);
    }

    private void setupCalendar() {
        if (currentMonth == null) {
            currentMonth = AUCalendar.getInstance().getCurrentMonth();
        }
        calendarAdapter = new MonthAdapter(getContext(), currentMonth);

        calendarAdapter.setLayoutResId(dayLayoutResId);

        calendarAdapter.injectViewInteractor(viewInteractor);

        calendarAdapter.refreshDays();

        calendarGrid.setAdapter(calendarAdapter);

        calendarGrid.setOnItemClickListener((parent, view, position, id) -> {
            Object currentObj = calendarAdapter.getItem(position);
            if (currentObj != null) {
                CalendarItem calendarItem = (CalendarItem) currentObj;
                if (calendarItem.isSelectable()) {
                    calendarAdapter.setSelected(calendarItem.getDateTime());
                }
            }
        });
    }

    @Override
    public void refreshData() {
        setupCalendar();
    }

    public void subscribe() {
        if (calendarAdapter != null) {
            calendarAdapter.subscribe();
        }
    }

    public void unsubscribe() {
        if (calendarAdapter != null) {
            calendarAdapter.unsubscribe();
        }
    }

    public void setCurrentMonth(DateTime currentMonth) {
        this.currentMonth = currentMonth;
        setupCalendar();
    }
}