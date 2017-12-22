package com.lgc.garylianglib.util.myactivity;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.lgc.garylianglib.R;
import com.lgc.garylianglib.util.CustomToast;
import com.lgc.garylianglib.util.L;
import com.lgc.garylianglib.util.data.IsFastClick;
import com.lgc.garylianglib.util.data.SnackbarUtils;
import com.lgc.garylianglib.util.dialog.QMUITipDialog;
import com.lgc.garylianglib.util.rom.HuaweiUtils;
import com.lgc.garylianglib.util.rom.MeizuUtils;
import com.lgc.garylianglib.util.rom.MiuiUtils;
import com.lgc.garylianglib.util.rom.QikuUtils;
import com.lgc.garylianglib.util.rom.RomUtils;
import com.squareup.otto.Bus;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.dkzwm.widget.srl.SmoothRefreshLayout;

/**
 * 基类
 *
 * @autor feijin_lgc
 * <p>
 * create at 2017/9/9 12:08
 */
public class BaseActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String TAG = "MyActivity";
    // 是否能够退出
    private boolean isBack = false;
    // 上次按退出的时间
    private long downTime;

    protected static Context context;
    protected static Activity activity;

    protected int anim = -1;// 跳转动画选择
    // 动画常量
    /**
     * 退出动画-向左
     */
    protected final static int OANIM2L = 1;
    /**
     * 退出动画-向右
     */
    protected final static int OANIM2R = 2;
    /**
     * 退出动画-向下
     */
    protected final static int OANIM2B = 3;

    /**
     * 需要进行检测的权限数组
     */
    public String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE
    };

    private static final int PERMISSION_REQUEST_CODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    public boolean isNeedCheck = true;


    protected ImmersionBar mImmersionBar;


    private boolean isClick = true;

    private Dialog dialog;

    public QMUITipDialog tipDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        context = this;
        activity = this;

        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //在BaseActivity里销毁
    }

    /**
     * 检测权限
     */
    public void checkPermissions(String... permissions) {
        List<String> needRequestPermissionList = findDeniedPermissions(permissions);
        if (null != needRequestPermissionList && needRequestPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissionList.toArray(new String[needRequestPermissionList.size()]), PERMISSION_REQUEST_CODE);
        } else {
//            applyOrShowFloatWindow(context);
        }
    }

    public void applyOrShowFloatWindow(Context context) {
        if (checkPermission(context)) {
        } else {
            applyPermission(context);
        }
    }

    private boolean checkPermission(Context context) {
        //6.0 版本之后由于 google 增加了对悬浮窗权限的管理，所以方式就统一了
        if (Build.VERSION.SDK_INT < 23) {
            if (RomUtils.checkIsMiuiRom()) {
                return miuiPermissionCheck(context);
            } else if (RomUtils.checkIsMeizuRom()) {
                return meizuPermissionCheck(context);
            } else if (RomUtils.checkIsHuaweiRom()) {
                return huaweiPermissionCheck(context);
            } else if (RomUtils.checkIs360Rom()) {
                return qikuPermissionCheck(context);
            }
        }
        return commonROMPermissionCheck(context);
    }

    private boolean huaweiPermissionCheck(Context context) {
        return HuaweiUtils.checkFloatWindowPermission(context);
    }

    private boolean miuiPermissionCheck(Context context) {
        return MiuiUtils.checkFloatWindowPermission(context);
    }

    private boolean meizuPermissionCheck(Context context) {
        return MeizuUtils.checkFloatWindowPermission(context);
    }

    private boolean qikuPermissionCheck(Context context) {
        return QikuUtils.checkFloatWindowPermission(context);
    }

    private boolean commonROMPermissionCheck(Context context) {
        //最新发现魅族6.0的系统这种方式不好用，天杀的，只有你是奇葩，没办法，单独适配一下
        if (RomUtils.checkIsMeizuRom()) {
            return meizuPermissionCheck(context);
        } else {
            Boolean result = true;
            if (Build.VERSION.SDK_INT >= 23) {
                try {
                    Class clazz = Settings.class;
                    Method canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
                    result = (Boolean) canDrawOverlays.invoke(null, context);
                } catch (Exception e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
            return result;
        }
    }

    private void applyPermission(Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            if (RomUtils.checkIsMiuiRom()) {
                miuiROMPermissionApply(context);
            } else if (RomUtils.checkIsMeizuRom()) {
                meizuROMPermissionApply(context);
            } else if (RomUtils.checkIsHuaweiRom()) {
                huaweiROMPermissionApply(context);
            } else if (RomUtils.checkIs360Rom()) {
                ROM360PermissionApply(context);
            }
        }
        commonROMPermissionApply(context);
    }

    private void ROM360PermissionApply(final Context context) {
        showConfirmDialog(context, new OnConfirmResult() {
            @Override
            public void confirmResult(boolean confirm) {
                if (confirm) {
                    QikuUtils.applyPermission(context);
                } else {
                    Log.e(TAG, "ROM:360, user manually refuse OVERLAY_PERMISSION");
                }
            }
        });
    }

    private void huaweiROMPermissionApply(final Context context) {
        showConfirmDialog(context, new OnConfirmResult() {
            @Override
            public void confirmResult(boolean confirm) {
                if (confirm) {
                    HuaweiUtils.applyPermission(context);
                } else {
                    Log.e(TAG, "ROM:huawei, user manually refuse OVERLAY_PERMISSION");
                }
            }
        });
    }

    private void meizuROMPermissionApply(final Context context) {
        showConfirmDialog(context, new OnConfirmResult() {
            @Override
            public void confirmResult(boolean confirm) {
                if (confirm) {
                    MeizuUtils.applyPermission(context);
                } else {
                    Log.e(TAG, "ROM:meizu, user manually refuse OVERLAY_PERMISSION");
                }
            }
        });
    }

    private void miuiROMPermissionApply(final Context context) {
        showConfirmDialog(context, new OnConfirmResult() {
            @Override
            public void confirmResult(boolean confirm) {
                if (confirm) {
                    MiuiUtils.applyMiuiPermission(context);
                } else {
                    Log.e(TAG, "ROM:miui, user manually refuse OVERLAY_PERMISSION");
                }
            }
        });
    }

    /**
     * 通用 rom 权限申请
     */
    private void commonROMPermissionApply(final Context context) {
        //这里也一样，魅族系统需要单独适配
        if (RomUtils.checkIsMeizuRom()) {
            meizuROMPermissionApply(context);
        } else {
            if (Build.VERSION.SDK_INT >= 23) {
                showConfirmDialog(context, new OnConfirmResult() {
                    @Override
                    public void confirmResult(boolean confirm) {
                        if (confirm) {
                            try {
                                Class clazz = Settings.class;
                                Field field = clazz.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");

                                Intent intent = new Intent(field.get(null).toString());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("package:" + context.getPackageName()));
                                context.startActivity(intent);
                            } catch (Exception e) {
                                Log.e(TAG, Log.getStackTraceString(e));
                            }
                        } else {
                            Log.d(TAG, "user manually refuse OVERLAY_PERMISSION");
                            //需要做统计效果
                        }
                    }
                });
            }
        }
    }

    private void showConfirmDialog(Context context, OnConfirmResult result) {
        showConfirmDialog(context, context.getString(R.string.tip_dialog_1), result);
    }

    private void showConfirmDialog(Context context, String message, final OnConfirmResult result) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new AlertDialog.Builder(context).setCancelable(true).setTitle("")
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.tip_dialog_2),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirmResult(true);
                                dialog.dismiss();
                            }
                        }).setNegativeButton(context.getString(R.string.tip_dialog_3),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirmResult(false);
                                dialog.dismiss();
                            }
                        }).create();
        dialog.show();
    }

    private interface OnConfirmResult {
        void confirmResult(boolean confirm);
    }

    /**
     * 获取权限集中需要申请权限的列表
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissionList.add(perm);
            }
        }
        return needRequestPermissionList;
    }

    /**
     * 检测是否所有的权限都已经授权
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                L.e("xx", "存在未授权的........");
                showMissingPermissionDialog();
                isNeedCheck = false;
            } else {

                L.e("xx", "已经授权完成........");
            }
        }
    }

    /**
     * 显示提示信息
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.ok_notifyTitle);
        builder.setMessage(R.string.ok_notifyMsg);
        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.ok_cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityStack.getInstance().exit();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });

        builder.setPositiveButton(R.string.ok_setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        isNeedCheck = true;
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    protected void init() {

    }

    protected void initView() {

    }

    protected void loadView() {

    }

    protected void initTitlebar() {

    }

    protected void showToast(String text) {
        CustomToast.showToasts(context, text);
    }

    protected void showToast(int text) {
        CustomToast.showToasts(context, text);
    }

    protected void initHandler() {
        // TODO Auto-generated method stub
    }

    @Override
    public void finish() {
        super.finish();
        loadAnim();
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
//        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }


    // Press the back button in mobile phone
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(0, R.anim.base_slide_right_out);
    }

    protected void loadAnim() {
        switch (anim) {
            case OANIM2L:
                // 向左
                overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
                break;
            case OANIM2R:
                // 向右
                overridePendingTransition(0, R.anim.base_slide_right_out);
                break;
            default:
                overridePendingTransition(R.anim.in_lefttoright, R.anim.out_lefttoright);
                break;
        }
    }

    /**
     * 统一 跳转activity 方法
     *
     * @param classActivity
     */
    public void jumpActivity(final Context context, final Class<?> classActivity) {
        startActivity(new Intent(context, classActivity));
        finish();
    }

    /**
     * 统一 跳转activity 方法
     *
     * @param classActivity
     */
    public void jumpActivityNotFinish(final Context context, final Class<?> classActivity) {
        if (IsFastClick.isFastClick()) {
            startActivity(new Intent(context, classActivity));
        }

    }

    public void jumpActivityNotFinish2(final Context context, final Class<?> classActivity) {
        if (IsFastClick.isFastClick()) {
            startActivity(new Intent(context, classActivity));
        }
    }

    public void jumpActivityNotFinish2(final Context context, final Class<?> classActivity, Object object) {
        Intent intent = new Intent(context, classActivity);
        Bundle bundle = new Bundle();
//        bundle.putSerializable("userBean", object);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startOneTimeActivity(Intent intent) {
        if (IsFastClick.isFastClick()) {
            startActivity(intent);
        }
    }

    public static int getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
        }
        return version;
    }


    /**
     * 版本号
     *
     * @return
     */
    public static int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        return sdkVersion;
    }

    public String getVersionName() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo;
        String version;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            version = "  " + packInfo.versionName;
            // version = "Version" + packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return " 1.0";
    }

    /**
     * 隱藏 輸入法
     */
    public void hideInput() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 显示 輸入法
     */
    public void showInput(View v) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 显示加载 zhong  context.getString(R.string.main_process_fail)
     *
     * @param res
     */

    public void loadDialog(String res) {
        tipDialog = new QMUITipDialog.Builder(this)
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
            tipDialog = new QMUITipDialog.Builder(this)
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
            tipDialog = new QMUITipDialog.Builder(this)
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

    protected void showToast(View view, String text) {

        SnackbarUtils.with(view)
                .setMessage(text)
                .setMessageColor(Color.WHITE)
                .show();
    }

    protected void showToast(View view, int text) {

        SnackbarUtils.with(view)
                .setMessage(context.getString(text))
                .setMessageColor(Color.WHITE)
                .show();
    }

    /**
     * 无数据View
     *
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
     *
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
}

