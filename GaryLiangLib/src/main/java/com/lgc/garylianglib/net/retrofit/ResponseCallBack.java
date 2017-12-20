package com.lgc.garylianglib.net.retrofit;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface ResponseCallBack<T> extends Callback<T> {

    /**
     * 返回body为空时回调
     *
     * @param call
     * @param response
     */
    void onBodyNull(Call<T> call, Response<T> response);

    /**
     * 请求成功并且status code为与后端约定成功码时回调（当前是1）
     *
     * @param call
     * @param response
     */
    void onSuccess(Call<T> call, Response<T> response);

    /**
     * 请求成功,但status code为与后端约定的某个错误码
     *
     * @param call
     * @param response
     * @param status
     */
    void onFailure(Call<T> call, Response<T> response, int status);

    /**
     * 当http响应状态码不为200时回调，服务器404或500等错误
     *
     * @param call
     * @param response
     */
    void onServerError(Call<T> call, Response<T> response);
}
