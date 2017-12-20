package com.lgc.garylianglib.net.retrofit;

import retrofit2.Call;
import retrofit2.Response;

/**
* 
* @autor feijin_lgc 
*
* create at 2017/9/9 12:19
*
*/
public abstract class SimpleAfterDefResponseCallBack<T> extends AfterDefResponseCallBack<T> {

    /**
     * 继承本类,覆写本方法实现拦截自定义处理
     *
     * @param call
     * @param response
     * @return
     */
    public int onBodyNullDefaultHandle(Call<T> call, Response<T> response) {
        return 0;
    }

    /**
     * 继承本类,覆写本方法实现拦截自定义处理
     *
     * @param call
     * @param response
     * @return
     */
    public int onSuccessDefaultHandle(Call<T> call, Response<T> response) {
        return 0;
    }

    /**
     * 继承本类,覆写本方法实现拦截自定义处理
     *
     * @param call
     * @param response
     * @param status
     * @return
     */
    public int onFailureDefaultHandle(Call<T> call, Response<T> response, int status) {
        return 0;
    }

    /**
     * 继承本类,覆写本方法实现拦截自定义处理
     *
     * @param call
     * @param response
     * @return
     */
    public int onServerErrorDefaultHandle(Call<T> call, Response<T> response) {
        return 0;
    }

    /**
     * 继承本类,覆写本方法实现拦截自定义处理
     *
     * @param call
     * @param response
     * @return
     */
    public int onResponseDefaultHandle(Call<T> call, Response<T> response) {
        return 0;
    }

    /**
     * 继承本类,覆写本方法实现拦截自定义处理
     *
     * @param call
     * @param t
     * @return
     */
    public int onFailureDefaultHandle(Call<T> call, Throwable t) {
        return 0;
    }

}
