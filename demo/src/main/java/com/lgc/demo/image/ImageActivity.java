package com.lgc.demo.image;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgc.demo.R;
import com.lgc.garylianglib.util.config.GlideApp;
import com.lgc.garylianglib.util.imageloader.GlideUtil;
import com.lgc.garylianglib.util.myactivity.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2017/12/22 11:09
 *     desc   :    图片测试类
 *     version: 1.0
 * </pre>
 */
public class ImageActivity extends MyActivity {
    @BindView(R.id.demo_ico_iv_1)
    ImageView demo_ico_iv_1;

    @BindView(R.id.demo_ico_iv_2)
    ImageView demo_ico_iv_2;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.f_title_tv)
    TextView f_title_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        init();
        initView();
    }

    @Override
    protected void init() {
        super.init();
        mImmersionBar
                .statusBarView(R.id.top_view)
                .fullScreen(false)
                .addTag("img")  //给上面参数打标记，以后可以通过标记恢复
                .init();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        try {
            GlideUtil.setImage("http://img5.imgtn.bdimg.com/it/u=1467751238,3257336851&fm=11&gp=0.jpg", demo_ico_iv_1, R.mipmap.ic_launcher);
            GlideUtil.setImageCircle("http://img5.imgtn.bdimg.com/it/u=1467751238,3257336851&fm=11&gp=0.jpg", demo_ico_iv_2, R.mipmap.ic_launcher);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
