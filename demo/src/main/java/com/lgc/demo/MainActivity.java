package com.lgc.demo;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.lgc.demo.dialog.DialogActivity;
import com.lgc.demo.image.ImageActivity;
import com.lgc.demo.testnet.TestNetActivity;
import com.lgc.garylianglib.util.myactivity.ActivityStack;
import com.lgc.garylianglib.util.myactivity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2017/12/22 11:04
 *     desc   :    首页
 *     version: 1.0
 * </pre>
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.demo_img_tv)
    TextView demo_img_tv;

    @BindView(R.id.demo_net_tv)
    TextView demo_net_tv;


    @BindView(R.id.demo_dialog_tv)
    TextView demo_dialog_tv;

    // 是否能够退出
    private boolean isBack = false;
    // 上次按退出的时间
    private long downTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        init();
    }

    @Override
    protected void init() {
        super.init();
        mImmersionBar
                .statusBarView(R.id.top_view)
                .fullScreen(false)
                .addTag("main")  //给上面参数打标记，以后可以通过标记恢复
                .init();
    }

    @OnClick({R.id.demo_img_tv, R.id.demo_net_tv,R.id.demo_dialog_tv})
    void click(View v) {
        switch (v.getId()) {
            case R.id.demo_img_tv:
                jumpActivityNotFinish(context, ImageActivity.class);//图片测试
                break;
            case R.id.demo_net_tv:
                jumpActivityNotFinish(context, TestNetActivity.class);//接口测试
                break;
            case R.id.demo_dialog_tv:
                jumpActivityNotFinish(context, DialogActivity.class);//接口测试
                break;
        }
    }

    @Override
    protected void initView() {
        super.initView();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:

                if (!isBack) {
                    showToast(demo_net_tv,R.string.main_exit);
                    downTime = event.getDownTime();
                    isBack = true;
                    return true;
                } else {
                    if (event.getDownTime() - downTime <= 2000) {
                        ActivityStack.getInstance().exit();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    } else {
                        showToast(demo_net_tv,R.string.main_exit);
                        downTime = event.getDownTime();
                        return true;
                    }
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
