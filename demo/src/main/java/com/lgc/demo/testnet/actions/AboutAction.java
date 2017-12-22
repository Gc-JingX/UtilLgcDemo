package com.lgc.demo.testnet.actions;


import com.lgc.demo.util.net.ApiService;
import com.lgc.demo.util.net.RMer;
import com.lgc.demo.testnet.event.UserEvent;
import com.lgc.demo.testnet.model.AboutDto;
import com.lgc.garylianglib.actions.Action;
import com.lgc.garylianglib.actions.ActionCreator;
import com.lgc.garylianglib.net.retrofit.DefResponseCallBackImpl;
import com.lgc.garylianglib.util.L;

import retrofit2.Call;
import retrofit2.Response;


/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2017/11/24 16:45
 *     desc   :    登录
 *     version: 1.0
 * </pre>
 */
public class AboutAction extends ActionCreator {


    public AboutAction() {
    }


    private Call<AboutDto> callAbout;

    /**
     * @return
     */
    public void getAbout() {
        ApiService api = RMer.instance().api();
        Call<AboutDto> call = api.getAbout();
        RMer.instance().enqueue(call, new DefResponseCallBackImpl<AboutDto>() {
            @Override
            public void onSuccess(Call<AboutDto> call, Response<AboutDto> response) {
                L.e("xx", "onSuccess....getAbout" + response.body().getData().toString());
                sendEvent(UserEvent.ACTION_KEY_SUCCESS_GET_ABOUT, response.body().getResult(),
                        Action.KEY_OBJ, response.body());
            }

            @Override
            public void onFailure(Call<AboutDto> call, Response<AboutDto> response, int status) {
                sendEvent(UserEvent.ACTION_KEY_ERROR, response.body().getResult(),
                        Action.KEY_MSG, response.body().getMsg());
            }

            @Override
            public void onFailure(Call<AboutDto> call, Throwable t) {
                sendEvent(UserEvent.ACTION_KEY_ERROR, netError,
                        Action.KEY_MSG, NETWORK_TIPS);
            }
        });
        callAbout = call;
    }

    /**
     * 取消访问
     */
    public void cancelNet() {
        if (callAbout != null) {
            callAbout.cancel();
        }
    }
}
