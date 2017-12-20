package com.lgc.garylianglib.net.retrofit;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;

/**
* 
* @autor feijin_lgc 
*
* create at 2017/9/9 12:18
*
*/
public class LoginResponseCallBack<T> extends SimpleAfterDefResponseCallBack<T> {

    @Override
    public int onSuccessDefaultHandle(Call<T> call, Response<T> response) {
        Log.e("xx", "onSuccessDefaultHandle。。。。。。");
        return 0;
    }

    @Override
    public int onFailureDefaultHandle(Call<T> call, Response<T> response, int status) {
        //检测登录状态
        Log.e("xx", "onFailureDefaultHandle。。。。。。");
//        if (ConfigManger.getLoginInfo(CustomApplication.getInstance()) == null) {
////            LoginActivity.start(CustomApplication.getInstance(), true);
//        }
        return RESPONSE_CODE_STOP_DISPATCH;
    }
}
