package com.tywho.appdemo.app;

/**
 * http://www.tywho.com
 *
 * @author：litianyi
 * @create：2016-07-08 15:50
 */
public class AppConfig {
    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final AppConfig INSTANCE = new AppConfig();
    }

    //获取单例
    public static AppConfig getInstance() {
        return SingletonHolder.INSTANCE;
    }



}
