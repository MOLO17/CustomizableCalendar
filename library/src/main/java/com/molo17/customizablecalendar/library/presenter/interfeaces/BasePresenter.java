package com.molo17.customizablecalendar.library.presenter.interfeaces;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.molo17.customizablecalendar.library.interactors.ViewInteractor;
import com.molo17.customizablecalendar.library.view.BaseView;

/**
 * Created by francescofurlan on 23/06/17.
 */

public interface BasePresenter<T extends BaseView> {
    void setView(T view);

    void onBindView(View rootView);

    void injectViewInteractor(ViewInteractor viewInteractor);

    void setLayoutResId(@LayoutRes int layoutResId);
}