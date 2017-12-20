package com.lgc.garylianglib.util.dialog;

import android.content.Context;
import android.os.Handler;


/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2017/10/17 15:34
 *     desc   :  
 *     version: 1.0
 * </pre>
 */
public class MyProgressDialogUtil {
    public static MyProgressBarDialog dialog;

    /**
     * 显示进度对话框
     *
     * @param context
     * @param msgId
     */
    public static void show(Context context, int msgId) {
        if (dialog == null) {
            dialog = new MyProgressBarDialog(context);
            dialog.setMsg(msgId);
            dialog.show();
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            dialog = new MyProgressBarDialog(context);
            dialog.setMsg(msgId);
            dialog.show();
        }

    }

    public static void showAndDiss(Context context, int msgId) {
        if (dialog == null) {
            dialog = new MyProgressBarDialog(context);
            dialog.setMsg(msgId);
            dialog.show();
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            dialog = new MyProgressBarDialog(context);
            dialog.setMsg(msgId);
            dialog.show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);

    }

    /**
     * 显示进度对话框
     *
     * @param context
     */
    public static void show(Context context) {
        if (dialog == null) {
            dialog = new MyProgressBarDialog(context);
            dialog.show();
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            dialog = new MyProgressBarDialog(context);
            dialog.show();
        }
    }

    /**
     * 关闭进度对话框
     */
    public static void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public static void dismiss2() {
        if (dialog != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, 2000);
        }
    }

    /**
     * 设置进度对话框显示文字提示
     */
    public static void setMsg(int msgId) {
        if (dialog != null) {
            dialog.setMsg(msgId);
        }
    }

    /**
     * 关闭已弹出的对话框，再弹出一个新的进度对话框
     *
     * @param msgId
     */
    public static void dismiss2Msg(int msgId) {
        if (dialog != null) {
            dialog.setMsg(msgId);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        }, 2000);
    }

    /**
     * 关闭已弹出的对话框，再弹出一个新的进度对话框
     *
     * @param msg
     */
    public static void dismiss2Msg(String msg) {
        if (dialog != null) {
            dialog.setMsg(msg);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        }, 2000);
    }
}
