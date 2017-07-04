package com.francesco.furlan.customizablecalendar.library.presenter.interfeaces;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.francesco.furlan.customizablecalendar.library.interactors.ViewInteractor;
import com.francesco.furlan.customizablecalendar.library.view.BaseView;

/**
 * Created by francescofurlan on 23/06/17.
 */

public interface BasePresenter<T extends BaseView> {
    void setView(T view);

    void onBindView(View rootView);

    void setLayoutResId(@LayoutRes int layoutResId);
}