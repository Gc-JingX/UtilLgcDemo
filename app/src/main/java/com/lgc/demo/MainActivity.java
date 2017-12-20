package com.lgc.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.lgc.garylianglib.util.config.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.demo_ico_iv)
    ImageView demo_ico_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        try {
            GlideApp.with(this)
                    .load("http://img5.imgtn.bdimg.com/it/u=1467751238,3257336851&fm=11&gp=0.jpg")
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(demo_ico_iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
