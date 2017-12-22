package com.lgc.demo.util.net;



import com.lgc.demo.testnet.model.AboutDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * @autor feijin_lgc
 * <p>
 * create at 2017/9/9 12:18
 */
public interface ApiService {


    /**
     * 获取关于我们信息
     *
     * @return
     */
    @Headers("Content-Type: application/json")
    @GET(value = URLManager.GET_ABOUT)
    Call<AboutDto> getAbout();

}
