package com.lgc.demo.testnet;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.lgc.demo.R;
import com.lgc.demo.testnet.actions.AboutAction;
import com.lgc.demo.testnet.event.UserEvent;
import com.lgc.demo.testnet.model.AboutDto;
import com.lgc.garylianglib.actions.Action;
import com.lgc.garylianglib.event.EventBusUtils;
import com.lgc.garylianglib.util.L;
import com.lgc.garylianglib.util.myactivity.MyActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2017/12/22 14:50
 *     desc   : 访问网络测试
 *     version: 1.0
 * </pre>
 */
public class TestNetActivity extends MyActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.f_title_tv)
    TextView f_title_tv;

    @BindView(R.id.demo_txt_tv)
    TextView demo_txt_tv;

    @BindView(R.id.demo_jump_tv)
    TextView demo_jump_tv;

    private AboutAction aboutAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_net);
        ButterKnife.bind(this);
        init();

    }
    @OnClick({R.id.demo_jump_tv})
    void click(View v) {
        switch (v.getId()) {
            case R.id.demo_jump_tv:
                jumpActivityNotFinish(context, TestNetActivity.class);//接口测试
                break;
        }
    }
    @Override
    protected void init() {
        super.init();
        aboutAction = new AboutAction();
          aboutAction.getAbout();

        mImmersionBar
                .statusBarView(R.id.top_view)
                .fullScreen(false)
                .addTag("net")  //给上面参数打标记，以后可以通过标记恢复
                .init();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true,priority = 100)
    public void MessageEvent(Action action) {
        switch (action.getType()) {
            case UserEvent.ACTION_KEY_SUCCESS_GET_ABOUT:
                L.e("接收信息成功.....");
                AboutDto aboutDto = (AboutDto) action.getUserData();
                AboutDto.AboutBean bean=aboutDto.getData();
                if (null != bean) {
                    demo_txt_tv.setText(bean.toString());
                }
                break;
            case UserEvent.ACTION_KEY_ERROR:
                int errorType = action.getErrorType();

                String msg = action.getMsg(action);
                L.e("接收信息失败....errorType ." + errorType + " msg " + msg);
                demo_txt_tv.setText("errorType ." + errorType + " msg " + msg);
                break;
        }


        L.e("xx", "action   接收到数据更新.....onStoreChange 1  action : " + action.toString());

    }

    @Override
    public void onStart() {
        super.onStart();

        EventBusUtils.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBusUtils.unregister(this);
    }

    @Override
    public void finish() {
        super.finish();
        //取消访问
        aboutAction.cancelNet();
    }
}
