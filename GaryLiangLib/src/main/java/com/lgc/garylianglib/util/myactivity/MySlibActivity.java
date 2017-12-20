package com.lgc.garylianglib.util.myactivity;


import android.os.Bundle;
import android.view.LayoutInflater;

import com.lgc.garylianglib.R;
import com.lgc.garylianglib.util.cusview.SwipeBackLayout;


/**
 * 基类
 *
 * @autor feijin_lgc
 * <p>
 * create at 2017/9/9 12:08
 */
public class MySlibActivity extends MyActivity {


    /**
     * 侧滑
     */
    protected SwipeBackLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.slib_base, null);
        layout.attachToActivity(this);
        context = this;
        activity = this;
    }

}

