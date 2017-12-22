package com.lgc.demo.dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.lgc.demo.R;
import com.lgc.garylianglib.util.myactivity.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2017/12/22 16:28
 *     desc   : 弹出框测试
 *     version: 1.0
 * </pre>
 */
public class DialogActivity extends MyActivity {

    @BindView(R.id.demo_txt_tv_1)
    TextView demo_txt_tv_1;


    @BindView(R.id.demo_txt_tv_2)
    TextView demo_txt_tv_2;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.f_title_tv)
    TextView f_title_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void init() {
        super.init();
        mImmersionBar
                .statusBarView(R.id.top_view)
                .fullScreen(false)
                .addTag("dialog")  //给上面参数打标记，以后可以通过标记恢复
                .init();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.demo_txt_tv_1, R.id.demo_txt_tv_2})
    void click(View v) {
        switch (v.getId()) {
            case R.id.demo_txt_tv_1:
                loadDialog("测试提示");
                break;
            case R.id.demo_txt_tv_2:
                showToast(demo_txt_tv_2, "测试提示");
                break;
        }
    }

}
