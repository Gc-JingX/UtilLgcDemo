package com.lgc.garylianglib.util.data;

import com.lgc.garylianglib.util.L;

/**
 * Created by ${DaHe} on 2017/10/21.
 */

public class IsFastClick {

    private static long lastClickTime = 0;//上次点击的时间

    private static int spaceTime = 800;//时间间隔

    public static boolean isFastClick() {

        long currentTime = System.currentTimeMillis();//当前系统时间

        boolean isAllowClick;//是否允许点击
        L.e("xx", "time " + (currentTime - lastClickTime));
        if (currentTime - lastClickTime > spaceTime) {

            isAllowClick = true;

        } else {

            isAllowClick = false;

        }

        lastClickTime = currentTime;

        return isAllowClick;

    }
}
