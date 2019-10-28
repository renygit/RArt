package com.reny.git.rart.utils.click;

public class FastClickCheck {

    private final static int SPACE_TIME = 500;//2次点击的间隔时间，单位ms
    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        if (System.currentTimeMillis() - lastClickTime >= SPACE_TIME) {
            lastClickTime = System.currentTimeMillis();
            return false;
        } else {
            return true;
        }
    }
}
