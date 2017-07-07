package com.molo17.customizablecalendar.library.presenter.interfeaces;

import com.molo17.customizablecalendar.library.interactors.ViewInjector;
import com.molo17.customizablecalendar.library.view.CustomizableCalendarView;

import java.util.List;

/**
 * Created by francescofurlan on 23/06/17.
 */

public interface CustomizableCalendarPresenter extends BasePresenter<CustomizableCalendarView>, ViewInjector {
    List<String> setupWeekDays();
}
