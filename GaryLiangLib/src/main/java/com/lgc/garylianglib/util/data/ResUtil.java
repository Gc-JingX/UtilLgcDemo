package com.lgc.garylianglib.util.data;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.lgc.garylianglib.util.config.MyApplication;


/**
 * @autor feijin_lgc
 * <p>
 * create at 2017/9/9 12:15
 */
public class ResUtil {

    public static String getString(@StringRes int strId) {
        return MyApplication.getInstance().getString(strId);
    }

    public static int getColor(@ColorRes int colorId) {
        return ContextCompat.getColor(MyApplication.getInstance(), colorId);
    }

    public static Drawable getDrawable(@DrawableRes int drawableId) {
        return ContextCompat.getDrawable(MyApplication.getInstance(), drawableId);
    }

    public static int getDimen(@DimenRes int dimenId) {
        return MyApplication.getInstance().getResources().getDimensionPixelSize(dimenId);
    }

    public static String getFormatString(@StringRes int strId, Object... args) {
        String src = MyApplication.getInstance().getString(strId);
        return String.format(src, args);
    }

}
