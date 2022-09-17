package com.example.colearn.pojo;

import static com.xuexiang.xui.XUI.getContext;

import com.alibaba.fastjson.JSONObject;
import com.example.colearn.utils.SPUtils;

import java.time.LocalDate;

public class Task {

    public static String getTask() {
        return "今日完成两个以上目标，并成功打卡！";
    }

    public static boolean hasFinish() {
        String hasDoneListStr = SPUtils.getString(LocalDate.now() + "hasDoneList".concat(User.getUser() == null ? "" : User.getUser().getAccount())
                , null, getContext());
        if (hasDoneListStr != null) {
            return JSONObject.parseArray(hasDoneListStr, Habit.class).size() >= 2;
        }
        return false;
    }
}
