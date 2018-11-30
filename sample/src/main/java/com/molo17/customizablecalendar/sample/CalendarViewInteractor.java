package com.molo17.customizablecalendar.sample;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.molo17.customizablecalendar.library.adapter.WeekDaysViewAdapter;
import com.molo17.customizablecalendar.library.components.CalendarRecyclerView;
import com.molo17.customizablecalendar.library.interactors.AUCalendar;
import com.molo17.customizablecalendar.library.interactors.ViewInteractor;
import com.molo17.customizablecalendar.library.model.Calendar;
import com.molo17.customizablecalendar.library.model.CalendarItem;


import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

/**
 * Created by francescofurlan on 03/07/17.
 */

public class CalendarViewInteractor implements ViewInteractor {
    private Context context;
    private Calendar calendar;
    private TextView firstDaySelectedTxt;
    private TextView lastDaySelectedTxt;
    private CalendarRecyclerView calendarRecyclerView;

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
        int x = 0;
        x++;
    }

    @Override
    public void onWeekDayBindView(WeekDaysViewAdapter.WeekDayVH holder, String weekDay) {
        int x = 0;
        x++;
    }

    @Override
    public void onSubViewBindView(View view, String month) {
        LocalDate currentMonth = calendar.getCurrentMonth();
        String text = DateTimeFormatter.ofPattern("MMMM yyyy").format(currentMonth);
        ((TextView)view.findViewById(android.R.id.message)).setText(text);

        view.findViewById(R.id.prevIcon).setOnClickListener(v -> {
            LinearLayoutManager layoutManager = (LinearLayoutManager)calendarRecyclerView.getLayoutManager();
            int position = layoutManager.findFirstCompletelyVisibleItemPosition();

            LocalDate currentMonth1 = calendar.getCurrentMonth();
            if (currentMonth1.compareTo(calendar.getFirstMonth()) > 0) {
                layoutManager.scrollToPosition(position - 1);
                calendarRecyclerView.post(() -> AUCalendar.getInstance().setCurrentMonth(currentMonth.minusMonths(1)));
            }
        });
        view.findViewById(R.id.nextIcon).setOnClickListener(v -> {
            LinearLayoutManager layoutManager = (LinearLayoutManager)calendarRecyclerView.getLayoutManager();
            int position = layoutManager.findFirstCompletelyVisibleItemPosition();

            LocalDate currentMonth1 = calendar.getCurrentMonth();
            LocalDate lastMonth = calendar.getMonths().get(calendar.getMonths().size() - 1);
            if (currentMonth1.compareTo(lastMonth) < 0) {
                layoutManager.scrollToPosition(position + 1);
                calendarRecyclerView.post(() -> AUCalendar.getInstance().setCurrentMonth(currentMonth.plusMonths(1)));
            }
        });
    }

    public void scrollToCurrentMonth() {
        new Handler().postDelayed(() -> {
            LocalDate currentMonth = calendar.getCurrentMonth();
            if (calendarRecyclerView != null && currentMonth != null) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)calendarRecyclerView.getLayoutManager();

                for (int position = 0; position < calendar.getMonths().size(); position++) {
                    if (calendar.getMonths().get(position).compareTo(currentMonth) == 0) {
                        layoutManager.scrollToPosition(position);
                        break;
                    }
                }
            }
        }, 300);
    }

    @Override
    public void onCalendarBindView(View view) {
        calendarRecyclerView = (CalendarRecyclerView) view;
    }

    @Override
    public void onMonthBindView(View view) {
        int x = 0;
        x++;
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

            if (currentItem.getDateTime().compareTo(LocalDate.now()) == 0) {
                dayView.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                dayView.setTextColor(ContextCompat.getColor(context, R.color.black));
            }

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
        return -1;
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
