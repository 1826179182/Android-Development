package com.example.colearn.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.alibaba.fastjson.JSON;
import com.example.colearn.Home;
import com.example.colearn.R;
import com.example.colearn.pojo.Habit;
import com.example.colearn.home.CheckIn;
import com.example.colearn.pojo.User;
import com.example.colearn.utils.SPUtils;
import com.kongzue.dialogx.dialogs.CustomDialog;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xui.widget.imageview.IconImageView;
import com.xuexiang.xui.widget.layout.XUIButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TodoListAdapter extends ArrayAdapter {
    private static Lock lock = new ReentrantLock();
    public Handler notifyDataSetChanged = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            DateChange();
        }
    };
    private List<Habit> todoListItems;
    private int resourceId;
    private LayoutInflater inflater;
    private SmoothCheckBox finish;
    private Thread changeDate;
    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    private CardView habitIcon;
    private IconImageView habitIconRes;
    private TextView habitName;

    public TodoListAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        todoListItems = objects;
        resourceId = resource;
    }

    public static boolean hasHabit(String newHabitName) {
        for (Object todo : Home.getTodoAdapter().getTodoList()
        ) {
            if (((Habit) todo).getHabitName().equals(newHabitName)) {
                return true;
            }
        }
        return false;
    }

    public List<Habit> getTodoList() {
        return todoListItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Habit habit = (Habit) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        habitIcon = view.findViewById(R.id.habit_icon);
        habitIconRes = view.findViewById(R.id.habit_icon_res);
        habitName = view.findViewById(R.id.habit);
        finish = view.findViewById(R.id.finish);
        if (!Home.selectDate.toString().equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString())){
            finish.setVisibility(View.INVISIBLE);
        }

        habitIcon.setCardBackgroundColor(todoListItems.get(position).getHabitIcon());
        habitIconRes.setImageResource(todoListItems.get(position).getHabitIconRes());
        habitName.setText(todoListItems.get(position).getHabitName());

        finish.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (checkBox.isChecked()) {
                    changeDate = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(850);
                                Habit hasDoneHabit;
                                if (todoListItems.get(position).getFrequency().equals("只提醒一次")){
                                    hasDoneHabit = todoListItems.remove(position);
                                }else {
                                    hasDoneHabit = todoListItems.get(position);
                                }
                                for (int i = 0; i < Home.getAllTodoList().size(); i++) {
                                    if (Home.getAllTodoList().get(i).getHabitName().equals(hasDoneHabit.getHabitName()) && Home.getAllTodoList().get(i).getFrequency().equals("只提醒一次")) {
                                        Home.getAllTodoList().remove(i);
                                    }
                                }

                                hasDoneHabit.setFinishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
                                hasDoneHabit.setStatue(Habit.DONE);
                                HasDoneListAdapter.getDoneListItems().add(hasDoneHabit);
                                SPUtils.putString("todoList".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                                        , JSON.toJSONString(Home.getAllTodoList()), getContext());
                                SPUtils.putString(LocalDate.now() + "hasDoneList".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                                        , JSON.toJSONString(HasDoneListAdapter.getDoneListItems()), getContext());
                                Message msg = new Message();
                                notifyDataSetChanged.sendMessage(msg);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    executorService.execute(changeDate);
                }
            }
        });
        return view;
    }

    private synchronized void DateChange() {
        Home.getTodoAdapter().notifyDataSetChanged();
        Home.updateAllTodoList(Home.selectDate.getMonthOfYear(),Home.selectDate);
        CustomDialog.show(new OnBindView<CustomDialog>(R.layout.task_finish) {
            @Override
            public void onBind(final CustomDialog dialog, View v) {
                XUIButton btnOk;
                XUIButton btnCheckIn;
                dialog.setMaskColor(Color.parseColor("#4D000000"));
                dialog.setCancelable(false);
                btnOk = v.findViewById(R.id.btn_ok);
                btnCheckIn = v.findViewById(R.id.clock_in);
                btnCheckIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getContext(), CheckIn.class);
                        getContext().startActivity(intent);
                        dialog.dismiss();
                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

}