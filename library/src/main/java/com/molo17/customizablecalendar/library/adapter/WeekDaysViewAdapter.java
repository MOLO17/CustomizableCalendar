package com.molo17.customizablecalendar.library.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.molo17.customizablecalendar.library.interactors.ViewInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by francescofurlan on 28/06/17.
 */

public class WeekDaysViewAdapter extends RecyclerView.Adapter<WeekDaysViewAdapter.WeekDayVH> {

    private Context context;
    private List<String> weekDays = new ArrayList<>();
    private ItemClickListener clickListener;
    private ViewInteractor viewInteractor;
    private @LayoutRes int layoutResId;

    public WeekDaysViewAdapter(Context context, List<String> weekDays, @LayoutRes int layoutResId, ViewInteractor viewInteractor) {
        this.context = context;
        this.weekDays = weekDays;
        this.layoutResId = layoutResId;
        this.viewInteractor = viewInteractor;
    }

    @Override
    public WeekDayVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutResId, parent, false);
        return new WeekDayVH(view);
    }

    @Override
    public void onBindViewHolder(WeekDayVH holder, int position) {
        String weekDay = getItem(position);
        holder.weekDayTxt.setText(weekDay);
        viewInteractor.onWeekDayBindView(holder, weekDay);
    }

    @Override
    public int getItemCount() {
        return weekDays.size();
    }

    public String getItem(int position) {
        return weekDays.get(position);
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class WeekDayVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView weekDayTxt;

        public WeekDayVH(View itemView) {
            super(itemView);
            weekDayTxt = (TextView) itemView.findViewById(android.R.id.summary);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}

