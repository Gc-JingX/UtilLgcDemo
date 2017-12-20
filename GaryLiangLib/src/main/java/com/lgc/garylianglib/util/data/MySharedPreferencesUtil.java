package com.lgc.garylianglib.util.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.lgc.garylianglib.util.L;

;


/**
 * @autor feijin_lgc
 * <p>
 * create at 2017/9/9 12:02
 */
public class MySharedPreferencesUtil {
    public final static String PROJECTNAME = "ws";

    public static SharedPreferences getProjectSP(Context context) {
        return context.getSharedPreferences(PROJECTNAME, Context.MODE_PRIVATE);
    }

    /**
     * 本地 时间戳的存储
     */
    public static boolean setLocalTime(Context context, String time) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("local_time", time).commit();
    }

    /**
     * 得到本地时间戳
     */
    public static String getLocalTime(Context context) {
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("local_time", "1980-01-01 00:00:00");
    }

    /**
     * 设置服务器的时间戳
     */
    public static boolean setBodyTime(Context context, String time) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("bodyTime", time).commit();
    }

    /**
     * 得到服务器时间戳
     */
    public static String getBodyTime(Context context) {
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("bodyTime", "0");
    }

    /**
     * 获取用户登录名
     *
     * @param context
     * @return
     */
    public static String getUserName(Context context) {
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("userName", null);
    }

    /**
     * 设置用户登录名
     *
     * @param context
     * @param userName
     */
    public static boolean setUserName(Context context, String userName) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("userName", userName).commit();
    }

    /**
     * 获取用户登录密码
     *
     * @param context
     * @return
     */
    public static String getPassWord(Context context) {
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("passWord", null);
    }

    /**
     * 设置用户登录密码
     *
     * @param context
     * @param passWord
     */
    public static boolean setPassWord(Context context, String passWord) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("passWord", passWord).commit();
    }

    /**
     * 获取用户钥匙
     *
     * @param context
     * @return
     */
    public static String getJSESSIONID(Context context) {
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("JSESSIONID", null);
    }

    /**
     * 设置用户钥匙
     *
     * @param context
     * @param clientKey
     */
    public static boolean setJSESSIONID(Context context, String clientKey) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("JSESSIONID", clientKey).commit();
    }

    /**
     * 获取用户ID
     *
     * @param context
     * @return
     */
    public static String getMemberId(Context context) {
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("memberId", null);
    }

    /**
     * 设置用户ID
     *
     * @param context
     * @param menberId
     */
    public static boolean setMemberId(Context context, String menberId) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("memberId", menberId).commit();
    }

    /**
     * 设置用户职业（是否是班主任）
     *
     * @param context
     * @param menberId
     */
    public static boolean setHeadStudent(Context context, String menberId) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("headStudent", menberId).commit();
    }


    /**
     * 获取用户职业（是否是班主任）
     *
     * @param context
     * @return
     */
    public static String getHeadStudent(Context context) {
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("headStudent", null);
    }



    /**
     * 获取用户标识ID
     *
     * @param context
     * @return
     */
    public static int getUId(Context context) {
        SharedPreferences sp = getProjectSP(context);
        return sp.getInt("uid", -1);
    }

    /**
     * 设置用户标识ID
     *
     * @param context
     * @param uid
     */
    public static boolean setUId(Context context, int uid) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putInt("uid", uid).commit();
    }

    /**
     * 判断用户是否登录，用户登录名和密码是否等于空
     */
    public static boolean isLoginIn(Context context) {
        String clientKey = getJSESSIONID(context);
        String memberId = getMemberId(context);
        if (clientKey != null && memberId != null) {
            L.e("MySharedPreferencesUtil", " 登陆了");
            return true;
        } else {
            L.e("MySharedPreferencesUtil", " 没有 登陆");
            return false;
        }
    }

    /**
     * 获取用户信息
     *
     * @param context
     * @return
     */
    public static String getUserInfoBean(Context context) {
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("UserInfoBean", null);
    }

    /**
     * 设置用户信息
     *
     * @param context
     * @param userName
     */
    public static boolean setUserInfoBean(Context context, String userName) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("UserInfoBean", userName).commit();
    }

    public static void cleanSp(Context context) {
        setJSESSIONID(context, null);
        setMemberId(context, null);
        setBodyTime(context, "0");
        setLocalTime(context, "1980-01-01 00:00:00:00");
        setUserName(context, null);
        setUId(context, -1);
        setUserInfoBean(context, null);
        setHeadStudent(context, null);

    }

    /**
     * 存储购物车
     * @param context
     * @param userName
     * @return
     */
    public static boolean setCarBean(Context context, String userName) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("CarBean", userName).commit();
    }

    /**
     * 获取用户信息
     *
     * @param context
     * @return
     */
    public static String getCarBean(Context context) {
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("CarBean", null);
    }



    public static void cleanDB(Context context) {


    }
}
