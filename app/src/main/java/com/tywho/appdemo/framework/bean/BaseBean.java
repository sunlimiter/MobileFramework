package com.tywho.appdemo.framework.bean;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-04-19 13:42
 */
public class BaseBean<T> {
    public int code;
    public String message;
    public T data;


    /**
     * 根据 code 码判断业务状态
     * code == 10000，表示业务成功。为空表示登录成功 其他表示业务失败，message 进一步描述业务状态。
     */
    public boolean isSuccess() {
        return code == 10000 || code == 0;
    }
}
