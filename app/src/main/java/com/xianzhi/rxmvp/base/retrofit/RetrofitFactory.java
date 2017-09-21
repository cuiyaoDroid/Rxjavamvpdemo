package com.xianzhi.rxmvp.base.retrofit;

import android.util.Log;


import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;



public class RetrofitFactory {

    private static final String TAG = RetrofitFactory.class.getSimpleName();
    private static final String BASE_URL = "http://cuiyao.top/";

    private static final long TIMEOUT = 30;

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                Request.Builder builder = chain.request().newBuilder();
                return chain.proceed(builder.build());
            })
            .addInterceptor(new HttpLoggingInterceptor(message -> Log.i(TAG,message))
            .setLevel(HttpLoggingInterceptor.Level.BASIC))
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build();

    private static RetrofitService retrofitService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(RetrofitService.class);

    public static RetrofitService getInstance() {
        return retrofitService;
    }

}
