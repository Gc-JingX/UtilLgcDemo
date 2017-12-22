package com.lgc.demo.util.net;

import android.text.TextUtils;
import android.util.Log;


import com.lgc.garylianglib.model.BaseDto;
import com.lgc.garylianglib.net.retrofit.PersistentCookieStore;
import com.lgc.garylianglib.net.retrofit.ResponseCallBack;
import com.lgc.garylianglib.util.L;
import com.lgc.garylianglib.util.config.MyApplication;

import org.json.JSONObject;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitManager
 */
public class RMer {

    public static final int RESPONSE_SUCCESS_CODE = 1;
    public static final int RESPONSE_TOKEN_INVALID_CODE = 20082;
    public static final String MEDIA_TYPE_APPLICATION_JSON = "application/json";
    public static final String MEDIA_TYPE_APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
    /**
     * 超时时间
     */
    public static final int DEFAULT_TIMEOUT = 26;
    //通用参数map
    private static HashMap<String, String> GENERAL_PARAMS_MAP = new HashMap<String, String>();

    private Retrofit mRetrofit;
    private static RMer mRmer;
    private ApiService mApiService;
    private OkHttpClient mOkHttpClient;
    private OnRequestCallBack mOnRequestCallBack;
    private PersistentCookieStore mPersistentCookieStore;

    private RMer() {
    }

    public static RMer instance() {
        if (mRmer == null) {
            mRmer = new RMer();
            mRmer.mPersistentCookieStore = new PersistentCookieStore(MyApplication.getInstance());
            CookieManager cookieManager = new CookieManager(mRmer.mPersistentCookieStore, CookiePolicy.ACCEPT_ALL);
//            CookieManager cookieManager = new CookieManager();
//            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

            //初始化Retrofit
            mRmer.mOkHttpClient = new OkHttpClient.Builder()
//                    .addNetworkInterceptor(mRmer.mOnResponseInterceptor)
                    .addInterceptor(mRmer.mBeforeRequestInterceptor)
                    .addInterceptor(mRmer.mOnResponseInterceptor)
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .cookieJar(new JavaNetCookieJar(cookieManager))
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();
            mRmer.mRetrofit = new Retrofit.Builder()
                    .baseUrl(URLManager.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mRmer.mOkHttpClient)
                    .build();
            mRmer.mApiService = mRmer.create(ApiService.class);
            mRmer.mOnRequestCallBack = new OnRequestCallBack() {
                @Override
                public void init() {
                    //添加固定通用参数
//                    GENERAL_PARAMS_MAP.put("xx", "xxx");
                }

                @Override
                public void onBeforeRequest() {
                    //添加需更新通用参数
                    //GENERAL_PARAMS_MAP.put("xx", "xxx");
                }
            };
            mRmer.mOnRequestCallBack.init();
        }
        return mRmer;
    }

    private Interceptor mBeforeRequestInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            String method = originalRequest.method();
            RequestBody originalRequestBody = null;
            RequestBody newRequestBody = null;

            L.i("xx", "请求 url " + originalRequest.url());
            //请求之前回调
            mOnRequestCallBack.onBeforeRequest();

            if (GENERAL_PARAMS_MAP.isEmpty()) {
                return chain.proceed(originalRequest);
            }

            if ("GET".equalsIgnoreCase(method)) {
                String originalGetUrl = originalRequest.url().toString();
                StringBuilder newGetUrl = new StringBuilder(originalGetUrl);
                //添加通用参数
                int i = 0;
                for (Map.Entry<String, String> entry : GENERAL_PARAMS_MAP.entrySet()) {
                    if (i == 0) {
                        newGetUrl.append(originalGetUrl.indexOf("?") != -1 ? "&" : "?")
                                .append(entry.getKey())
                                .append("=")
                                .append(entry.getValue());
                    } else {
                        newGetUrl.append("&")
                                .append(entry.getKey())
                                .append("=")
                                .append(entry.getValue());
                    }
                    i++;
                }
                Request getRequest = originalRequest.newBuilder().url(newGetUrl.toString()).build();
                return chain.proceed(getRequest);
            } else if ("POST".equalsIgnoreCase(method)) {
                originalRequestBody = originalRequest.body();
                MediaType mediaType = originalRequestBody.contentType();
                if (null == mediaType) {
                    return chain.proceed(originalRequest);
                }

                String contentType = mediaType.toString();

                if (contentType.contains(MEDIA_TYPE_APPLICATION_JSON)) {
                    try {
                        String jsonStr = bodyToString(originalRequestBody);
                        JSONObject jsonObject = null;
                        if (TextUtils.isEmpty(jsonStr)) {
                            jsonObject = new JSONObject();
                        } else {
                            jsonObject = new JSONObject(jsonStr);
                        }
                        //添加通用参数
                        for (Map.Entry<String, String> entry : GENERAL_PARAMS_MAP.entrySet()) {
                            jsonObject.put(entry.getKey(), entry.getValue());
                        }
                        jsonStr = jsonObject.toString();
                        newRequestBody = RequestBody.create(originalRequestBody.contentType(), jsonStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return chain.proceed(originalRequest);
                    }
                } else if (contentType.contains(MEDIA_TYPE_APPLICATION_FORM_URLENCODED)) {
                    FormBody originalFormBody = null;
                    try {
                        originalFormBody = (FormBody) originalRequest.body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FormBody.Builder newFormBody = new FormBody.Builder();
                    //添加通用参数
                    for (Map.Entry<String, String> entry : GENERAL_PARAMS_MAP.entrySet()) {
                        newFormBody.addEncoded(entry.getKey(), entry.getValue());
                    }
                    //该请求原参数
                    if (originalFormBody != null) {
                        for (int i = 0; i < originalFormBody.size(); i++) {
                            String name = originalFormBody.name(i);
                            if (GENERAL_PARAMS_MAP.containsKey(name)) {
                                continue;
                            }
                            String value = originalFormBody.value(i);
                            newFormBody.add(name, value);
                        }
                    }
                    newRequestBody = newFormBody.build();
                } else {
                    return chain.proceed(originalRequest);
                }
            }

            Request.Builder rb = originalRequest.newBuilder();
            Request request = rb.method(originalRequest.method(), newRequestBody).build();
            return chain.proceed(request);
        }
    };

    public String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            L.i("xx", "请求 copy " + copy.toString());
            final Buffer buffer = new Buffer();
            if (copy != null) {
                copy.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "";
        }
    }

    private Interceptor mOnResponseInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response response = chain.proceed(chain.request());
            String bodyString = response.body().string();
            Log.e("xx", response.body().contentType() + "===response===" + bodyString);

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();
        }
    };

    public <T> T create(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }

    public ApiService api() {
        return mApiService;
    }

    public <T> void enqueue(Call<T> call, final Callback<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (callback != null) {
                    if (callback instanceof ResponseCallBack) {
                        ResponseCallBack<T> rcb = (ResponseCallBack<T>) callback;
                        if (response.code() != 200) {
                            rcb.onServerError(call, response);
                        } else if (response.body() == null) {
                            rcb.onBodyNull(call, response);
                        } else {
                            L.e("xx", "dto " + response.body().toString());
                            if (response.body() instanceof BaseDto) {
                                BaseDto dto = (BaseDto) response.body();
                                L.e("xx", "dto " + dto.getData().toString());
                                if (dto.getResult() == RESPONSE_SUCCESS_CODE) {
                                    L.e("xx", " 訪問进入 1 ");
                                    rcb.onSuccess(call, response);
                                } else {
                                    L.e("xx", " 訪問进入 2 ");
                                    rcb.onFailure(call, response, dto.getResult());
                                }
                            }
                        }
                    }
                    L.e("xx", " 訪問进入 3 ");
                    callback.onResponse(call, response);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                t.printStackTrace();
                if (callback != null) {
                    L.e("xx", " 訪問进入 4 ");
                    callback.onFailure(call, t);
                }
            }
        });
    }

    public static <T> void cancel(Call<T> call) {
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
    }

    /**
     * 清除cookie缓存
     */
    public void cookieStoreRemoveAll() {
        mRmer.mPersistentCookieStore.removeAll();
    }

    public interface OnRequestCallBack {
        void init();

        void onBeforeRequest();
    }
}
