package com.lgc.garylianglib.util.config;

import android.app.Application;


/**
 * @autor feijin_lgc
 * <p>
 * create at 2017/9/9 12:15
 */
public class MyApplication extends Application {

    private static MyApplication mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }


    public static MyApplication getInstance() {
        return mApp;
    }

}
