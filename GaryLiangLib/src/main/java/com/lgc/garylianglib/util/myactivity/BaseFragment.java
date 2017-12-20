package com.lgc.garylianglib.util.myactivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lgc.garylianglib.R;
import com.lgc.garylianglib.util.CustomToast;
import com.lgc.garylianglib.util.data.SnackbarUtils;
import com.lgc.garylianglib.util.dialog.QMUITipDialog;

import me.dkzwm.widget.srl.SmoothRefreshLayout;


/**
 * 基类
 *
 * @autor omesoft_lgc
 * <p>
 * create at 2017/2/16 15:12
 */
public abstract class BaseFragment extends RootFragment {

    private static final String TAG = "BaseFragment";

    public FragmentManager mFragmentManager;
    public FragmentTransaction mFragmentTransaction;

    public static Context context;
    public Activity mActivity;
    protected Handler handler;
    protected Handler handler_ble;


    public QMUITipDialog tipDialog = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 该函数主要用于判断碎片是否在显示，显示的话则需要加载数据，不显示的话暂时不加载数据，这样可以提高app打开时候的速度
     */
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    protected void initTitleBar() {
    }

    protected void init() {

    }

    protected void initView() {
    }

    protected void loadView() {
    }


    protected void initHandler() {
        handler_ble = new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
                super.dispatchMessage(msg);
                switch (msg.what) {

                }
            }
        };
    }

    protected void sendMsg(int whatId, Object objs) {
        Message msg = new Message();
        msg.what = whatId;
        msg.obj = objs;
        Log.v("sendMsg", "msg.what=" + msg.what);
        if (handler != null) {
            handler.sendMessage(msg);
        }
    }

    protected void sendMsg(Handler handler, int whatId, Object objs) {
        Message msg = new Message();
        msg.what = whatId;
        msg.obj = objs;
        Log.v("sendMsg", "msg.what=" + msg.what);
        if (handler != null) {
            handler.sendMessage(msg);
        }
    }

    protected void showToast(String text) {
        CustomToast.showToasts(context, text);
    }

    protected void showToast(int text) {
        CustomToast.showToasts(context, text);
    }

    protected void onActivityResult() {
        // TODO Auto-generated method stub
    }

    public static int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        return sdkVersion;
    }




    /**
     * 统一 跳转activity 方法
     *
     * @param classActivity
     */
    public void jumpActivity(final Context context, final Class<?> classActivity) {
        startActivity(new Intent(context, classActivity));
        getActivity().finish();
    }

    /**
     * 统一 跳转activity 方法
     *
     * @param classActivity
     */
    public void jumpActivityNotFinish(final Context context, final Class<?> classActivity) {
        startActivity(new Intent(context, classActivity));
        getActivity().overridePendingTransition(R.anim.in_righttoleft, R.anim.out_righttoleft);

    }

    /**
     * 统一 跳转activity 方法
     *
     * @param classActivity
     */
    public void jumpActivityNotFinish(final Context context, final Class<?> classActivity, int type) {
        Intent intent = new Intent(context, classActivity);
        intent.putExtra("loadtype", type);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.in_righttoleft, R.anim.out_righttoleft);
    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }


    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
    }
    protected void showToast(View view, String text) {

        SnackbarUtils.with(view)
                .setMessage(text)
                .setMessageColor(Color.WHITE)
                .show();
    }

    protected void showToast(View view,int text) {

        SnackbarUtils.with(view)
                .setMessage(context.getString(text))
                .setMessageColor(Color.WHITE)
                .show();
    }
    /**
     * 无数据View
     * @param ll_nodata
     * @param recyclerView_shop
     * @param vis
     */
    protected void showNoDataView(LinearLayout ll_nodata, RecyclerView recyclerView_shop, boolean vis) {
        if (vis) {

            ll_nodata.setVisibility(View.VISIBLE);
            recyclerView_shop.setVisibility(View.GONE);
        } else {

            ll_nodata.setVisibility(View.GONE);
            recyclerView_shop.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 无数据View
     * @param ll_nodata
     * @param smoothRefreshLayout
     * @param vis
     */
    protected void showNoDataView2(LinearLayout ll_nodata, SmoothRefreshLayout smoothRefreshLayout, boolean vis) {
        if (vis) {

            ll_nodata.setVisibility(View.VISIBLE);
            smoothRefreshLayout.setVisibility(View.GONE);
        } else {

            ll_nodata.setVisibility(View.GONE);
            smoothRefreshLayout.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 显示加载 zhong  context.getString(R.string.main_process_fail)
     *
     * @param res
     */

    public void loadDialog(String res) {
        tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(res)
                .create();
        tipDialog.show();
    }

    /**
     * 显示加载 成功  context.getString(R.string.main_process_fail)
     *
     * @param res
     */
    public void loadSuccess(String res) {
        if (tipDialog != null) {
            if (tipDialog.isShowing()) {
                tipDialog.dismiss();
            }
            tipDialog = new QMUITipDialog.Builder(context)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                    .setTipWord(res)
                    .create();
            tipDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tipDialog.dismiss();
                }
            }, 2000);
        }

    }

    /**
     * 显示加载错误  context.getString(R.string.main_process_fail)
     *
     * @param res
     */
    public void loadError(String res) {
        if (tipDialog != null) {
            if (tipDialog.isShowing()) {
                tipDialog.dismiss();
            }
            tipDialog = new QMUITipDialog.Builder(context)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                    .setTipWord(res)
                    .create();
            tipDialog.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tipDialog.dismiss();
                }
            }, 2000);
        }

    }

    public void loadDiss() {
        if (tipDialog != null) {
            if (tipDialog.isShowing()) {
                tipDialog.dismiss();
            }
        }
    }
}

