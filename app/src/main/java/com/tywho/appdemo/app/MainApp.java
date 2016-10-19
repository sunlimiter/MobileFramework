package com.tywho.appdemo.app;

import android.app.Application;
import android.os.Build;
import android.preference.PreferenceManager;

import com.alibaba.fastjson.JSON;
import com.tywho.appdemo.BuildConfig;
import com.tywho.appdemo.flux.net.OKHttpConfig;
import com.tywho.appdemo.flux.net.OKHttpManager;
import com.tywho.appdemo.framework.bean.Head;
import com.tywho.appdemo.framework.control.PresenterProxy;
import com.tywho.appdemo.framework.helper.DeviceId;
import com.tywho.appdemo.framework.helper.SpUtil;
import com.tywho.appdemo.framework.helper.SysUtils;
import com.tywho.appdemo.mvp.presenter.GalleryPresenter;
import com.tywho.appdemo.mvp.presenter.LoginPresenter;
import com.tywho.appdemo.mvp.presenter.UploadPresenter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Cache;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-05-05 12:01
 */
public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SpUtil.init(PreferenceManager.getDefaultSharedPreferences(this));//初始化存储
//        PresenterProxy.getInstance().init(LoginPresenter.class, GalleryPresenter.class, UploadPresenter.class);

        SpUtil.saveOrUpdate("clientKey",DeviceId.getDeviceId(this));
        SpUtil.saveOrUpdate("screenWidth",SysUtils.getScreenWidth(this)+"");


        //okhttpmanager 传递Context，便于在自定义Interceptor中使用，仅供参考
        // 自定义缓存目录和大小
//        File cacheFile = new File(getCacheDir(), "okcache");
//        Cache cache = new Cache(cacheFile, 100 * 1024 * 1024);// 100mb
//
//        // 程序初始化时，初始okhttp配置
//        OKHttpConfig OKHttpConfig = new OKHttpConfig.Builder()
//                .setConnectTimeout(10).setReadTimeout(10).setWriteTimeout(10).setCacheTime(1000).setCache(cache).build();
//        OKHttpManager.init(this, OKHttpConfig);
    }
}
