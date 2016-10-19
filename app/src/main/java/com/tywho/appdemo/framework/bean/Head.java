package com.tywho.appdemo.framework.bean;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-05-06 14:05
 */
public class Head {
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
}
