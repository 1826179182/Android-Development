package com.example.colearn.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.colearn.R;
import com.example.colearn.components.Habit;
import com.xuexiang.xui.widget.imageview.IconImageView;

import java.util.ArrayList;
import java.util.List;

public class HasDoneListAdapter extends ArrayAdapter {
    private static List<Habit> doneListItems = new ArrayList<>();
    private int resourceId;
    private LayoutInflater inflater;

    private TextView finishTime;
    private CardView habitIcon;
    private IconImageView habitIconRes;
    private TextView habitName;

    public HasDoneListAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        doneListItems = objects;
        resourceId = resource;
    }

    public static List<Habit> getDoneListItems() {
        return doneListItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Habit habit = (Habit) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        finishTime = view.findViewById(R.id.finish_time);
        habitIcon = view.findViewById(R.id.habit_icon);
        habitIconRes = view.findViewById(R.id.habit_icon_res);
        habitName = view.findViewById(R.id.habit);

        habitIcon.setCardBackgroundColor(doneListItems.get(position).getHabitIcon());
        habitIconRes.setImageResource(doneListItems.get(position).getHabitIconRes());
        habitName.setText(doneListItems.get(position).getHabitName());
        finishTime.setText(doneListItems.get(position).getFinishTime().split(" ")[1]);
        return view;
    }

}