package com.lgc.garylianglib.net.retrofit;

import retrofit2.Call;
import retrofit2.Response;

public class SimpleResponseCallBackImpl<T> implements ResponseCallBack<T> {

    @Override
    public void onBodyNull(Call<T> call, Response<T> response) {

    }

    @Override
    public void onSuccess(Call<T> call, Response<T> response) {

    }

    @Override
    public void onFailure(Call<T> call, Response<T> response, int status) {

    }

    @Override
    public void onServerError(Call<T> call, Response<T> response) {

    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }
}
