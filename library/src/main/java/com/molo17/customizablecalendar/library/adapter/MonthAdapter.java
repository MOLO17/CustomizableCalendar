package com.molo17.customizablecalendar.library.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.molo17.customizablecalendar.library.R;
import com.molo17.customizablecalendar.library.interactors.AUCalendar;
import com.molo17.customizablecalendar.library.interactors.ViewInteractor;
import com.molo17.customizablecalendar.library.model.CalendarFields;
import com.molo17.customizablecalendar.library.model.CalendarItem;
import com.molo17.customizablecalendar.library.presenter.interfeaces.CustomizableCalendarPresenter;
import com.molo17.customizablecalendar.library.utils.DateUtils;
import com.molo17.customizablecalendar.library.view.MonthView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by francescofurlan on 23/06/17.
 */

public class MonthAdapter extends BaseAdapter implements MonthView {
    private Context context;
    private AUCalendar calendar;
    private int layoutResId;
    private List<CalendarItem> days;
    private ViewInteractor viewInteractor;

    private DateTime currentMonth;
    private DateTime firstSelectedDay;
    private DateTime lastSelectedDay;
    private boolean multipleSelection;
    private int firstDayOfWeek;

    private CompositeDisposable subscriptions;
    private boolean subscribed;

    public MonthAdapter(Context context, DateTime currentMonth) {
        this.context = context;
        this.subscriptions = new CompositeDisposable();
        this.calendar = AUCalendar.getInstance();
        this.layoutResId = R.layout.calendar_cell;
        this.currentMonth = currentMonth.withDayOfMonth(1).withMillisOfDay(0);
        initFromCalendar();
        subscribe();
    }

    private void initFromCalendar() {
        firstSelectedDay = calendar.getFirstSelectedDay();
        if (firstSelectedDay != null) {
            firstSelectedDay = firstSelectedDay.withMillisOfDay(0);
        }
        lastSelectedDay = calendar.getLastSelectedDay();
        if (lastSelectedDay != null) {
            lastSelectedDay = lastSelectedDay.withMillisOfDay(0);
        }
        multipleSelection = calendar.isMultipleSelectionEnabled();
        firstDayOfWeek = calendar.getFirstDayOfWeek();
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        final CalendarItem item = days.get(position);
        if (item != null) {
            return days.get(position).getId();
        }
        return -1;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final CalendarItem currentItem = days.get(position);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layoutResId, null);
        }

        if (viewInteractor != null && viewInteractor.hasImplementedMonthCellBinding()) {
            view = viewInteractor.onMonthCellBindView(view, currentItem);
        } else {
            final TextView dayView = (TextView) view.findViewById(android.R.id.title);
            final View background = view.findViewById(android.R.id.background);
            final View startSelectionView = view.findViewById(android.R.id.startSelectingText);
            final View endSelectionView = view.findViewById(android.R.id.stopSelectingText);

            startSelectionView.setVisibility(View.GONE);
            endSelectionView.setVisibility(View.GONE);

            if (currentItem == null) {
                background.setBackground(null);
                dayView.setText(null);
            } else if (currentItem.compareTo(calendar.getFirstMonth().withMillisOfDay(0)) < 0) {
                currentItem.setSelectable(false);
                background.setBackground(null);
                dayView.setTextColor(Color.BLACK);
                dayView.setText(currentItem.getDayString());
                dayView.setAlpha(0.6f);
            } else {
                currentItem.setSelectable(true);
                dayView.setAlpha(1f);
                Integer backgroundResource = null;
                if (firstSelectedDay != null) {
                    int startSelectedCompared = currentItem.compareTo(firstSelectedDay);
                    if (!multipleSelection) {
                        if (startSelectedCompared == 0) {
                            backgroundResource = R.drawable.empty_circle;
                        }
                    } else if (startSelectedCompared == 0) {
                        if (lastSelectedDay == null || lastSelectedDay.equals(currentItem.getDateTime())) {
                            backgroundResource = R.drawable.circle;
                        } else {
                            backgroundResource = R.drawable.left_rounded_rectangle;
                            endSelectionView.setVisibility(View.VISIBLE);
                        }
                    } else if (startSelectedCompared > 0 && lastSelectedDay != null) {
                        int endSelectedCompared = currentItem.compareTo(lastSelectedDay);
                        if (endSelectedCompared == 0) {
                            backgroundResource = R.drawable.right_rounded_rectangle;
                            startSelectionView.setVisibility(View.VISIBLE);
                        } else if (endSelectedCompared < 0) {
                            backgroundResource = R.drawable.rectangle;
                            startSelectionView.setVisibility(View.VISIBLE);
                            endSelectionView.setVisibility(View.VISIBLE);
                        }
                    }
                }

                int color = Color.BLACK;
                if (backgroundResource != null) {
                    background.setBackgroundResource(backgroundResource);
                    if (multipleSelection) {
                        color = Color.WHITE;
                    }
                } else {
                    background.setBackground(null);
                }

                dayView.setTextColor(color);
                dayView.setText(currentItem.getDayString());
            }
        }

        return view;
    }

    @Override
    public void refreshData() {
        refreshDays();
    }

    @Override
    public void setLayoutResId(@LayoutRes int layoutResId) {
        if (layoutResId != -1) {
            this.layoutResId = layoutResId;
        }
    }

    @Override
    public void injectViewInteractor(ViewInteractor viewInteractor) {
        this.viewInteractor = viewInteractor;
    }

    @Override
    public void injectPresenter(CustomizableCalendarPresenter presenter) {
    }

    /**
     * Select the day specified if multiple selection mode is not enabled,
     * otherwise adjust the ends of the selection:
     * first end will be set if the day specified is before the first end;
     * last end will be set if the day specified is after the last end;
     *
     * @param dateSelected a DateTime object that represents the day that
     *                     should be selected
     */
    @Override
    public void setSelected(DateTime dateSelected) {
        if (viewInteractor != null && viewInteractor.hasImplementedSelection()) {
            int itemSelected = viewInteractor.setSelected(multipleSelection, dateSelected);
            switch (itemSelected) {
                case 0:
                    notifyFirstSelectionUpdated(dateSelected);
                    break;
                case 1:
                    notifyLastSelectionUpdated(dateSelected);
                    break;
                default:
                    return;
            }
        } else {
            if (!multipleSelection) {
                notifyFirstSelectionUpdated(dateSelected);
            } else {
                if (firstSelectedDay != null) {
                    int startSelectedCompared = dateSelected.compareTo(firstSelectedDay);
                    if (startSelectedCompared < 0) {
                        notifyFirstSelectionUpdated(dateSelected);
                    } else if (lastSelectedDay != null) {
                        int endSelectedCompared = dateSelected.compareTo(lastSelectedDay);
                        if ((startSelectedCompared >= 0 && endSelectedCompared < 0) || endSelectedCompared > 0) {
                            notifyLastSelectionUpdated(dateSelected);
                        }
                    } else {
                        notifyLastSelectionUpdated(dateSelected);
                    }
                } else {
                    notifyFirstSelectionUpdated(dateSelected);
                }
            }
        }
        notifyDataSetChanged();
    }

    private void notifyFirstSelectionUpdated(DateTime startSelected) {
        this.firstSelectedDay = startSelected;
        this.calendar.setFirstSelectedDay(startSelected);
    }

    private void notifyLastSelectionUpdated(DateTime endSelected) {
        this.lastSelectedDay = endSelected;
        this.calendar.setLastSelectedDay(endSelected);
    }

    @Override
    public final void refreshDays() {
        final int empties;
        final int year = currentMonth.getYear();
        final int month = currentMonth.getMonthOfYear();
        final int firstDayOfMonth = currentMonth.getDayOfWeek() + 1;
        final int lastDayOfMonth = DateUtils.getDaysInMonth(month - 1, year);
        List<CalendarItem> updatedDays = new ArrayList<>();

        if (viewInteractor != null && viewInteractor.hasImplementedDayCalculation()) {
            days = viewInteractor.calculateDays(year, month, firstDayOfMonth, lastDayOfMonth);
        } else {
            // default days calculation
            if (firstDayOfMonth == firstDayOfWeek) {
                empties = 0;
            } else if (firstDayOfMonth < firstDayOfWeek) {
                empties = Calendar.SATURDAY - (firstDayOfWeek - 1);
            } else {
                empties = firstDayOfMonth - firstDayOfWeek;
            }

            int totDays = lastDayOfMonth + empties;
            for (int day = 1, position = 1; position <= totDays; position++) {
                if (position > empties) {
                    updatedDays.add(new CalendarItem(day++, month, year));
                } else {
                    updatedDays.add(null);
                }
            }

        }
        if (!updatedDays.equals(days)) {
            days = updatedDays;
            notifyDataSetChanged();
        }
    }

    public void subscribe() {
        if (!subscribed) {
            subscriptions.add(
                    calendar.observeChangesOnCalendar()
                            .subscribe(changeSet -> {
                                if (changeSet.isFieldChanged(CalendarFields.FIRST_DAY_OF_WEEK)
                                        || changeSet.isFieldChanged(CalendarFields.FIRST_SELECTED_DAY)
                                        || changeSet.isFieldChanged(CalendarFields.LAST_SELECTED_DAY)) {
                                    initFromCalendar();
                                    refreshDays();
                                }
                            })
            );
            subscribed = true;
        }
    }

    @Override
    public void unsubscribe() {
        if (subscribed) {
            subscriptions.clear();
            subscribed = false;
        }
    }
}