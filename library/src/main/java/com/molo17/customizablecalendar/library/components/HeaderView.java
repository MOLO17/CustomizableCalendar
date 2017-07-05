package com.molo17.customizablecalendar.library.components;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.molo17.customizablecalendar.library.interactors.ViewInteractor;
import com.molo17.customizablecalendar.library.presenter.interfeaces.CustomizableCalendarPresenter;

/**
 * Created by francescofurlan on 23/06/17.
 */

public class HeaderView extends RelativeLayout implements com.molo17.customizablecalendar.library.view.HeaderView {
    private CustomizableCalendarPresenter presenter;
    private ViewInteractor viewInteractor;
    private Context context;

    public HeaderView(Context context) {
        this(context, null);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
    }

    @Override
    public void refreshData() {

    }

    @Override
    public void setLayoutResId(@LayoutRes int layoutResId) {
        if (layoutResId != -1) {
            LayoutInflater.from(context).inflate(layoutResId, this);
        }
    }

    @Override
    public void injectViewInteractor(ViewInteractor viewInteractor) {
        this.viewInteractor = viewInteractor;
        this.viewInteractor.onHeaderBindView(this);
    }

    @Override
    public void injectPresenter(CustomizableCalendarPresenter presenter) {
        this.presenter = presenter;
        this.presenter.injectHeaderView(this);
    }
}