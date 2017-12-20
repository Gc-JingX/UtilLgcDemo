package com.lgc.garylianglib.util.myactivity;

import android.app.Activity;

import com.lgc.garylianglib.util.L;
import com.lgc.garylianglib.util.config.MyApplication;

import java.util.LinkedList;
import java.util.List;

/**
 * Activity堆栈管理。
 *
 * @autor feijin_lgc
 * <p>
 * create at 2017/9/9 11:58
 */
public class ActivityStack extends MyApplication {
    private final static String TAG = "ActivityStack";

    // 运用list来保存们每一个activity是关键
    private List<Activity> mList = new LinkedList<Activity>();
    // 为了实现每次使用该类时不创建新的对象而创建的静态对象
    private static ActivityStack instance;

    // 构造方法
    private ActivityStack() {
    }

    // 实例化一次
    public synchronized static ActivityStack getInstance() {
        if (null == instance) {
            instance = new ActivityStack();
        }
        return instance;
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    // 关闭每一个list内的activity
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /**
     * 退出指定的activity
     */
    public void popActivityByClass(Class<?>[] class1) {
        if (mList != null) {
            for (int i = 0; i < mList.size(); i++) {

                Activity activity = mList.get(i);
                for (Class<?> k : class1) {
                    L.v("lgc", "iii::" + i);
                    if (activity.getClass().equals(k)) {
                        L.v("lgc", "k::" + k.getName());
                        popActivity(activity);
                    } else {
                        L.v("lgc", "这个是：：" + activity.getClass().getName());
                    }
                }
            }
        }
    }

    public void popActivityByClass(Class<?> class1) {
        if (mList != null) {
            for (int i = 0; i < mList.size(); i++) {

                Activity activity = mList.get(i);
                if (activity.getClass().equals(class1)) {
                    L.v("lgc", "k::" + class1.getName());
                    popActivity(activity);
                } else {
                    L.v("lgc", "这个是：：" + activity.getClass().getName());
                }
            }
        }
    }

    // 退出栈顶Activity
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            mList.remove(activity);
            // mActivityStack.pop();
            activity = null;
        }
    }

    // 退出栈顶Activity
    public void removeActivity(Activity activity) {
        if (activity != null) {
            mList.remove(activity);
        }
    }

    /**
     * 退出
     */
    public void exitIsNotHaveThis(Activity myActivity) {
        try {
            for (Activity activity : mList) {
                if (!activity.getClass().equals(myActivity.getClass()))
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitIsNotHaveMain(Class<?> classA) {
        try {
            for (Activity activity : mList) {
                L.e("lgc", "这个是：：" + activity.getClass().getName());
                if (!activity.getClass().equals(classA))
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void exitAll() {
        try {
            for (Activity activity : mList) {
                activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 退出栈中所有Activity
    public void popAllActivityExceptOne() {
        Activity activity = currentActivity();
        popActivity(activity);
    }

    // 获得当前栈顶Activity
    public Activity currentActivity() {
        Activity activity = mList.get(mList.size() - 1);
        // Activity activity = mActivityStack.pop();
        return activity;
    }

    // 杀进程
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishCurrentActivity() {
        if (mList == null || mList.isEmpty()) {
            return;
        }
        Activity activity = mList.get(mList.size() - 1);
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (mList == null || mList.isEmpty()) {
            return;
        }
        if (activity != null) {
            mList.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (mList == null || mList.isEmpty()) {
            return;
        }
        for (Activity activity : mList) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 按照指定类名找到activity
     *
     * @param cls
     * @return
     */
    public Activity findActivity(Class<?> cls) {
        Activity targetActivity = null;
        if (mList != null) {
            for (Activity activity : mList) {
                if (activity.getClass().equals(cls)) {
                    targetActivity = activity;
                    break;
                }
            }
        }
        return targetActivity;
    }

    /**
     * @return 作用说明 ：获取当前最顶部activity的实例
     */
    public Activity getTopActivity() {
        Activity mBaseActivity = null;
        synchronized (mList) {
            final int size = mList.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = mList.get(size);
        }
        return mBaseActivity;

    }

    /**
     * @return 作用说明 ：获取当前最顶部的acitivity 名字
     */
    public String getTopActivityName() {
        Activity mBaseActivity = null;
        synchronized (mList) {
            final int size = mList.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = mList.get(size);
        }
        return mBaseActivity.getClass().getName();
    }
}

