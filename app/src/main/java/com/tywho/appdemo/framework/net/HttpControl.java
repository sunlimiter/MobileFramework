package com.tywho.appdemo.framework.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * http://www.tywho.com
 * http封装，okhttp，retrofit
 * @author：sunlimiter
 * @create：2016-04-19 11:15
 */
public class HttpControl {
    private static HttpControl mInstance;
    private Retrofit retrofit;

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpControl INSTANCE = new HttpControl();
    }

    //获取单例
    public static HttpControl getInstance() {
        return SingletonHolder.INSTANCE;
    }


    private HttpControl(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(7676, TimeUnit.MILLISECONDS)
                .connectTimeout(7676, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.10.13.12:8080")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public <T> T createService(Class<T> clz) {
        return retrofit.create(clz);
    }

}
