package com.lgc.garylianglib.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 自定义的toast
 *
 * @author omesoft_lgc
 *
 */
public class CustomToast {
	/**
	 * Toast的封装
	 */
	private static String oldMsg;
	protected static Toast toast = null;
	private static long oneTime = 0;
	private static long twoTime = 0;

	public static void showToast(Context context, String s) {
		if (toast == null) {
			toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (s.equals(oldMsg)) {
				if (twoTime - oneTime > Toast.LENGTH_SHORT) {
					toast.show();
				}
			} else {
				oldMsg = s;
				toast.setText(s);
				toast.show();
			}
		}
		oneTime = twoTime;
	}

	/**
	 * 两个方法，调用
	 *
	 * @param context
	 * @param resId
	 */
	public static void showToasts(Context context, String resId) {
		showToast(context, resId);
	}

	/**
	 * 两个方法，调用
	 *
	 * @param context
	 * @param resId
	 */
	public static void showToasts(Context context, int resId) {

		showToast(context, context.getResources().getString(resId));
	}

	/**
	 * 显示在屏幕中间
	 *
	 * @param context
	 * @param s
	 */
	public static void showToast2(Context context, String s) {
		if (toast == null) {
			toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 10);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (s.equals(oldMsg)) {
				if (twoTime - oneTime > Toast.LENGTH_SHORT) {
					toast.setGravity(Gravity.CENTER, 0, 10);
					toast.show();
				}
			} else {
				oldMsg = s;
				toast.setText(s);
				toast.setGravity(Gravity.CENTER, 0, 10);
				toast.show();
			}
		}
		oneTime = twoTime;
	}

	public static void showToastsByStr(Context context, String resId) {
		showToast(context, resId);
	}

	public static void showToastById(Context context, String resStr) {

		showToast(context, resStr);
	}
}
