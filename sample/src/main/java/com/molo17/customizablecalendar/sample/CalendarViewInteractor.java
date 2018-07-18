package com.molo17.customizablecalendar.sample;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.molo17.customizablecalendar.library.adapter.WeekDaysViewAdapter;
import com.molo17.customizablecalendar.library.interactors.AUCalendar;
import com.molo17.customizablecalendar.library.interactors.ViewInteractor;
import com.molo17.customizablecalendar.library.model.Calendar;
import com.molo17.customizablecalendar.library.model.CalendarItem;


import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;

/**
 * Created by francescofurlan on 03/07/17.
 */

public class CalendarViewInteractor implements ViewInteractor {
    private Context context;
    private Calendar calendar;
    private TextView firstDaySelectedTxt;
    private TextView lastDaySelectedTxt;

    CalendarViewInteractor(Context context) {
        this.context = context;
    }

    @Override
    public void onCustomizableCalendarBindView(View view) {

    }

    @Override
    public void onHeaderBindView(ViewGroup view) {
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.calendar_header, view);
        firstDaySelectedTxt = layout.findViewById(R.id.first_day_selected);
        lastDaySelectedTxt = layout.findViewById(R.id.last_day_selected);
        updateCalendar(calendar);
    }

    @Override
    public void onWeekDaysBindView(View view) {

    }

    @Override
    public void onWeekDayBindView(WeekDaysViewAdapter.WeekDayVH holder, String weekDay) {

    }

    @Override
    public void onSubViewBindView(View view, String month) {
        LocalDate currentMonth = calendar.getCurrentMonth();
        String text = DateTimeFormatter.ofPattern("MMMM yyyy").format(currentMonth);
        ((TextView)view.findViewById(android.R.id.message)).setText(text);

        view.findViewById(R.id.prevIcon).setOnClickListener(v -> AUCalendar.getInstance().setCurrentMonth(currentMonth.minusMonths(1)));
        view.findViewById(R.id.nextIcon).setOnClickListener(v -> AUCalendar.getInstance().setCurrentMonth(currentMonth.plusMonths(1)));
    }

    @Override
    public void onCalendarBindView(View view) {
    }

    @Override
    public void onMonthBindView(View view) {
    }

    @Override
    public View onMonthCellBindView(View view, CalendarItem currentItem) {
        final TextView dayView = view.findViewById(android.R.id.title);
        final View backgroundView = view.findViewById(android.R.id.background);
        final View selectedBackgroundView = view.findViewById(R.id.selectedBackgroundView);
        final View startSelectionView = view.findViewById(android.R.id.startSelectingText);
        final View endSelectionView = view.findViewById(android.R.id.stopSelectingText);

        selectedBackgroundView.setVisibility(View.GONE);
        startSelectionView.setVisibility(View.GONE);
        endSelectionView.setVisibility(View.GONE);
        backgroundView.setVisibility(View.GONE);

        if (currentItem == null) {
            dayView.setText(null);
        } else  {
            dayView.setText(currentItem.getDayString());
            dayView.setTextColor(ContextCompat.getColor(context, R.color.black));

            currentItem.setSelectable(true);

            LocalDate firstSelectedDay = calendar.getFirstSelectedDay();
            LocalDate lastSelectedDay = calendar.getLastSelectedDay();

            if (firstSelectedDay != null) {
                int startSelectedCompared = currentItem.compareTo(firstSelectedDay);
                int endSelectedCompared = 0;
                if (lastSelectedDay != null) {
                    endSelectedCompared = currentItem.compareTo(lastSelectedDay);
                }

                if (startSelectedCompared == 0 && endSelectedCompared == 0) {
                    selectedBackgroundView.setVisibility(View.VISIBLE);
                    dayView.setTextColor(ContextCompat.getColor(context, android.R.color.white));
                } else if (startSelectedCompared == 0) {
                    selectedBackgroundView.setVisibility(View.VISIBLE);
                    dayView.setTextColor(ContextCompat.getColor(context, android.R.color.white));
                    endSelectionView.setVisibility(View.VISIBLE);
                } else if (endSelectedCompared == 0) {
                    selectedBackgroundView.setVisibility(View.VISIBLE);
                    dayView.setTextColor(ContextCompat.getColor(context, android.R.color.white));
                    startSelectionView.setVisibility(View.VISIBLE);
                } else if (startSelectedCompared > 0 && endSelectedCompared < 0) {
                    backgroundView.setVisibility(View.VISIBLE);
                }
            }
        }

        return view;
    }

    @Override
    public boolean hasImplementedDayCalculation() {
        return false;
    }

    @Override
    public List<CalendarItem> calculateDays(int year, int month, int firstDayOfMonth, int lastDayOfMonth) {
        return null;
    }

    @Override
    public boolean hasImplementedSelection() {
        return false;
    }

    @Override
    public int setSelected(boolean multipleSelection, LocalDate dateSelected) {
//        DateTime firstSelectedDay = calendar.getFirstSelectedDay();
//        DateTime lastSelectedDay = calendar.getLastSelectedDay();
//
//        if (firstSelectedDay == null) {
//            return 0;
//        }
        return -1; // сбросить lastSelectedDay
    }

    @Override
    public boolean hasImplementedMonthCellBinding() {
        return true;
    }

    @Override
    public View getMonthGridView(View rootView) {
        return null;
    }

    @Override
    public boolean hasImplementedWeekDayNameFormat() {
        return true;
    }

    @Override
    public String formatWeekDayName(String nameOfDay) {
        return nameOfDay.substring(0, 1).toUpperCase() + nameOfDay.substring(1);
    }

    void updateCalendar(Calendar calendar) {
        this.calendar = calendar;
        if (firstDaySelectedTxt != null && lastDaySelectedTxt != null) {
            LocalDate firstDate = calendar.getFirstSelectedDay();
            LocalDate lastDate = calendar.getLastSelectedDay();
            if (firstDate != null) {
                firstDaySelectedTxt.setText(DateTimeFormatter.ofPattern("dd MMMM yyyy").format(firstDate));
            }
            if (lastDate != null) {
                lastDaySelectedTxt.setText(DateTimeFormatter.ofPattern("dd MMMM yyyy").format(lastDate));
            }
        }
    }
}
