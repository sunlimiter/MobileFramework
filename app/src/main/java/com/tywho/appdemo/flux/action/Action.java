package com.tywho.appdemo.flux.action;


/**
 * http://www.tywho.com
 * 传递的action基类
 *
 * @author：litianyi
 * @create：2016-07-20 10:25
 */
public class Action {

    // 数据类型
    private String type;
    // 数据
    private Object data;
    // 异常、错误类型
    private String error;

    public Action() {

    }

    public Action(String type) {
        this.type = type;
    }

    public Action(String type, Object data) {
        this.type = type;
        this.data = data;
    }

    public Action(String type, String error) {
        this.type = type;
        this.error = error;
    }

    public Action(String type, Object data, String error) {
        this.type = type;
        this.data = data;
        this.error = error;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
