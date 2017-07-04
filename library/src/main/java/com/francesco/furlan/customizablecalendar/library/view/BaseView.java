package com.francesco.furlan.customizablecalendar.library.view;

import android.support.annotation.LayoutRes;

import com.francesco.furlan.customizablecalendar.library.interactors.ViewInteractor;
import com.francesco.furlan.customizablecalendar.library.presenter.interfeaces.CustomizableCalendarPresenter;

/**
 * Created by francescofurlan on 23/06/17.
 */

public interface BaseView {
    void refreshData();

    void setLayoutResId(@LayoutRes int layoutResId);

    void injectViewInteractor(ViewInteractor viewInteractor);

    void injectPresenter(CustomizableCalendarPresenter presenter);
}