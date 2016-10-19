package com.tywho.appdemo.framework.control;

/**
 * Created by sunlimiter on 2016/05/05, 0005.
 */
public abstract class BasePresenter<T> {
    public T view;

    void attachView(T view) {
        this.view = view;
    }
}
