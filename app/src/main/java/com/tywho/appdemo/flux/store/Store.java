package com.tywho.appdemo.flux.store;



import com.tywho.appdemo.flux.action.Action;
import com.tywho.appdemo.flux.dispatcher.Dispatcher;
import com.tywho.appdemo.flux.event.BaseEvent;
import com.tywho.appdemo.flux.event.LoadFailEvent;
import com.tywho.appdemo.flux.event.LoadSuccessEvent;
import com.tywho.appdemo.flux.event.OtherEvent;

import org.greenrobot.eventbus.Subscribe;

/**
 * http://www.tywho.com
 * Store基类 store中的操作全部是同步的<br\>
 * 可处理数据缓存，数据更新等操作
 * @author：litianyi
 * @create：2016-07-20 10:25
 */
public abstract class Store<T> {
    protected Action mAction;
    protected Dispatcher dispatcher;

    protected Store(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }


    /**
     * 处理接收到不同的事件
     */
    protected void onAction(Action action) {
        if (action != null) {
            mAction = action;
        }
    }


    protected Action getAction() {
        return mAction;
    }
    protected T getData() {
        return (T)getAction().getData();
    }
    protected String getError() {
        return getAction().getError();
    }

    protected abstract BaseEvent changeEvent(String type);
}
