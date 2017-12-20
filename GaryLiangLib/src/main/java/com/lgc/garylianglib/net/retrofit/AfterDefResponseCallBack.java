package com.lgc.garylianglib.net.retrofit;

import retrofit2.Call;
import retrofit2.Response;

/**
* 
* @autor feijin_lgc 
*
* create at 2017/9/9 12:18
*
*/
public abstract class AfterDefResponseCallBack<T> extends DefResponseCallBackImpl<T> {

    public static final int RESPONSE_CODE_STOP_DISPATCH = -999;

    @Override
    public final void onBodyNull(Call<T> call, Response<T> response) {
        super.onBodyNull(call, response);
        int code = onBodyNullDefaultHandle(call, response);
        if (code == RESPONSE_CODE_STOP_DISPATCH) {
            return;
        }
        onBodyNull(call, response, code);
    }

    @Override
    public final void onSuccess(Call<T> call, Response<T> response) {
        super.onSuccess(call, response);
        int code = onSuccessDefaultHandle(call, response);
        if (code == RESPONSE_CODE_STOP_DISPATCH) {
            return;
        }
        onSuccess(call, response, code);
    }

    @Override
    public final void onFailure(Call<T> call, Response<T> response, int status) {
        super.onFailure(call, response, status);
        int code = onFailureDefaultHandle(call, response, status);
        if (code == RESPONSE_CODE_STOP_DISPATCH) {
            return;
        }
        onFailure(call, response, status, code);
    }

    @Override
    public final void onServerError(Call<T> call, Response<T> response) {
        super.onServerError(call, response);
        int code = onServerErrorDefaultHandle(call, response);
        if (code == RESPONSE_CODE_STOP_DISPATCH) {
            return;
        }
        onServerError(call, response, code);
    }

    @Override
    public final void onResponse(Call<T> call, Response<T> response) {
        super.onResponse(call, response);
        int code = onResponseDefaultHandle(call, response);
        if (code == RESPONSE_CODE_STOP_DISPATCH) {
            return;
        }
        onResponse(call, response, code);
    }

    @Override
    public final void onFailure(Call<T> call, Throwable t) {
        super.onFailure(call, t);
        int code = onFailureDefaultHandle(call, t);
        if (code == RESPONSE_CODE_STOP_DISPATCH) {
            return;
        }
        onFailure(call, t, code);
    }

    /**
     * 继承本类,覆写本方法实现拦截自定义处理
     *
     * @param call
     * @param response
     * @return
     */
    public abstract int onBodyNullDefaultHandle(Call<T> call, Response<T> response);

    /**
     * 继承本类,覆写本方法实现拦截自定义处理
     *
     * @param call
     * @param response
     * @return
     */
    public abstract int onSuccessDefaultHandle(Call<T> call, Response<T> response);

    /**
     * 继承本类,覆写本方法实现拦截自定义处理
     *
     * @param call
     * @param response
     * @param status
     * @return
     */
    public abstract int onFailureDefaultHandle(Call<T> call, Response<T> response, int status);

    /**
     * 继承本类,覆写本方法实现拦截自定义处理
     *
     * @param call
     * @param response
     * @return
     */
    public abstract int onServerErrorDefaultHandle(Call<T> call, Response<T> response);

    /**
     * 继承本类,覆写本方法实现拦截自定义处理
     *
     * @param call
     * @param response
     * @return
     */
    public abstract int onResponseDefaultHandle(Call<T> call, Response<T> response);

    /**
     * 继承本类,覆写本方法实现拦截自定义处理
     *
     * @param call
     * @param t
     * @return
     */
    public abstract int onFailureDefaultHandle(Call<T> call, Throwable t);


    public void onBodyNull(Call<T> call, Response<T> response, int code) {
    }

    public void onSuccess(Call<T> call, Response<T> response, int code) {
    }

    public void onFailure(Call<T> call, Response<T> response, int status, int code) {
    }

    public void onServerError(Call<T> call, Response<T> response, int code) {
    }

    public void onResponse(Call<T> call, Response<T> response, int code) {
    }

    public void onFailure(Call<T> call, Throwable t, int code) {
    }


}
