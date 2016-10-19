/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.tywho.appdemo.flux.action;


import com.tywho.appdemo.flux.dispatcher.Dispatcher;
import com.tywho.appdemo.framework.net.HttpFactory;

/**
 * http://www.tywho.com
 * action 生成器，进行网络请求等操作，通过dispatcher发送封装有数据的action
 * @author：litianyi
 * @create：2016-07-20 10:25
 */
public abstract class ActionCreator {

    protected Dispatcher mDispatcher;

    public ActionCreator(Dispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }

    public void dispatchAction(String type) {
        mDispatcher.dispatch(new Action(type));
    }

    public void dispatchAction(Action action) {
        mDispatcher.dispatch(action);
    }

    protected <S> S createService(Class<S> serviceClass) {
        return HttpFactory.provideService(serviceClass);
    }


    public abstract String getSuccessType();
    public abstract String getFaileType();
}
