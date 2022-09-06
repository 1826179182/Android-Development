package com.example.colearn.utils;

public class ButtonClickUtils {

    private static long lastClickTime;//记录最近一次点击时间
    private static long interval = 100;//间隔为0.1秒
    private static int lastButtonId;//存放最近一次传入的按钮id

    // 如果需要不同的间隔时间，直接调用这个方法设置所需间隔毫秒数即可
    public static void setInterval(long interval) {
        ButtonClickUtils.interval = interval;
    }

    // 不需要传入任何参数 直接在点击事件下调用此方法即可
    public static boolean isFastClick() {
        if (System.currentTimeMillis() - lastClickTime < interval) {
            return true;
        }
        lastClickTime = System.currentTimeMillis();
        return false;
    }

    // 需要传入ButtonId
    public static boolean isFastClick(int buttonId) {
        if (lastButtonId == buttonId && System.currentTimeMillis() - lastClickTime < interval) {
            return true;
        }
        lastClickTime = System.currentTimeMillis();
        lastButtonId = buttonId;
        return false;
    }
}

