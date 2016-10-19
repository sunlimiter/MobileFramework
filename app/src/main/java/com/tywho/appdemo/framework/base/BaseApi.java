package com.tywho.appdemo.framework.base;

import android.content.Context;

import com.tywho.appdemo.framework.helper.HttpConstant;

import java.lang.reflect.Field;
import java.util.TreeMap;

/**
 * http://www.tywho.com
 *
 * @author：litianyi
 * @create：2016-07-08 15:44
 */
public abstract class BaseApi {
    private int page;
    private int pageSize = HttpConstant.PAGE_SIZE;

    public static class Head {
        public String apiVersion = "1.0";
        /* 设备唯一标识md5 */
        public String clientKey;
        public String userId;
        /* 屏幕宽度 */
        public int screenWidth;
        /* 2 : Android */
        public int mobileType = 2;
        /* 品牌类型 */
        public String sourceType;
        /* 系统版本 */
        public String mobileVersion;
        /* 渠道 */
        public String channel;
        /* 请求时间 */
        public String time;
        /* 请求时间 */
        public String token;

        public int netType;
        public String ip;
        public String SSID;
        public String mobilePhone;
    }

    public enum Method {
        GET,
        POST,
    }

    protected abstract String getPath();

    public abstract Method requestMethod();

    public String getUrl() {
        return HttpConstant.API_URL + getPath();
    }


    public TreeMap<String, Object> getParams() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        Class clazz = getClass();
        Field[] field = clazz.getDeclaredFields();
        try {
            for (Field f : field) {
                f.setAccessible(true);
                if (f.get(this) != null) {
                    params.put(f.getName(), f.get(this));
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if(page > 0) {
            params.put("currentPage", page);
            params.put("pageSize", pageSize);
        }

        return params;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void handleParams(TreeMap<String, Object> params) {
    }
}
