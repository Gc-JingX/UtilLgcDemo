package com.lgc.garylianglib.util.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lgc.garylianglib.R;


/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2017/10/17 15:34
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MyProgressBarDialog extends Dialog {
    private Context context;
    private ProgressBar progressBar;
    private TextView txt;
    private View view;

    public MyProgressBarDialog(Context context) {
        super(context, R.style.myDialog);
        this.context = context;
        // 加载布局文件
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.my_dialog_progressbar, null);

        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                        // bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        //布局位于状态栏下方
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        //全屏
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        //隐藏导航栏
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                if (Build.VERSION.SDK_INT >= 19) {
                    uiOptions |= 0x00001000;
                } else {
                    uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
                }
                getWindow().getDecorView().setSystemUiVisibility(uiOptions);
            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        txt = (TextView) view.findViewById(R.id.dialog_text);
        // 给图片添加动态效果
        Animation anim = AnimationUtils.loadAnimation(context,
                R.anim.enteralpha);
        view.setAnimation(anim);
        setCanceledOnTouchOutside(false);
        // dialog添加视图
        setContentView(view);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

    }

    public void setMsg(String msg) {
        if (txt != null) {
            txt.setText(msg);
        }
    }

    public void setMsg(int msgId) {
        if (txt != null) {
            txt.setText(msgId);
        }
    }

    public void dismiss() {
        // Animation anim=AnimationUtils.loadAnimation(context,
        // R.anim.exitalpha);
        // view.startAnimation(null);
        super.dismiss();
    }

}

