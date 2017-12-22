package com.lgc.garylianglib.util.imageloader;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lgc.garylianglib.util.config.GlideApp;
import com.lgc.garylianglib.util.config.MyApplication;

/**
 * created by feijin_lgc.
 * created date 2017/10/20 上午10:55.
 * desc   图片工具类
 * version 1.0
 */
public class GlideUtil {

    /**
     * 带圆角的图片
     *
     * @param avator
     * @param main_user_iv
     */
    public static void setImageCircle(String avator, ImageView main_user_iv, int errorPic) {
        if (avator != null) {
            if (avator.length() > 0) {
                //圆角处理
                GlideApp.with(MyApplication.getInstance())
                        .load(avator)
                        .error(errorPic)
                        .centerCrop()
                        .circleCrop()
                        .into(main_user_iv);
            } else {
                main_user_iv.setImageResource(errorPic);
            }
        } else {
            main_user_iv.setImageResource(errorPic);
        }
    }

    /**
     * 不带圆角图片
     */
    public static void setImage(String avator, ImageView main_user_iv, int errorPic) {
        if (avator != null) {
            if (avator.length() > 0) {
                //圆角处理
                GlideApp.with(MyApplication.getInstance())
                        .load(avator)
                        .error(errorPic)
                        .centerCrop()
                        .into(main_user_iv);
            } else {
                main_user_iv.setImageResource(errorPic);
            }
        } else {
            main_user_iv.setImageResource(errorPic);
        }
    }

    /**
     * 不带圆角图片
     */
    public static void setImage(String avator, ImageView main_user_iv, int errorPic, int w, int h) {
        if (avator != null) {
            if (avator.length() > 0) {
                //圆角处理
                GlideApp.with(MyApplication.getInstance())
                        .load(avator)
                        .error(errorPic)
                        .override(w, h)
                        .centerCrop()
                        .into(main_user_iv);
            } else {
                main_user_iv.setImageResource(errorPic);
            }
        } else {
            main_user_iv.setImageResource(errorPic);
        }
    }

    public static void setImage(String avator, ImageView main_user_iv) {
        if (avator.length() > 0) {
            //圆角处理
            GlideApp.with(MyApplication.getInstance())
                    .load(avator)
                    .centerCrop()
                    .into(main_user_iv);
        }
    }

    /**
     * 停止下载
     */
    public static void pauseRequests() {
        GlideApp.with(MyApplication.getInstance()).pauseRequests();
    }

    /**
     * 恢复下载
     */
    public static void resumeRequests() {
        GlideApp.with(MyApplication.getInstance()).resumeRequests();
    }
}
