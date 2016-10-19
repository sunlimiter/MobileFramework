package com.tywho.appdemo.flux;

import com.tywho.appdemo.flux.action.Action;
import com.tywho.appdemo.flux.action.ActionCreator;

import rx.Subscriber;

/**
 * http://www.tywho.com
 *
 * @author：litianyi
 * @create：2016-07-20 10:25
 */
public class CallBackSubscriber<T> extends Subscriber<T> {

    protected ActionCreator mActionCreator;

    public CallBackSubscriber(ActionCreator creator) {
        this.mActionCreator = creator;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        onFail("error:" + e.getMessage());
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    /**
     * 请求成功将数据分发到store中处理
     *
     * @param response
     */
    protected void onSuccess(T response) {
        mActionCreator.dispatchAction(new Action(mActionCreator.getSuccessType(), response, null));
    }

    /**
     * 请求失败，将错误信息分发到store中处理
     *
     * @param error
     */
    protected void onFail(String error) {
        mActionCreator.dispatchAction(new Action(mActionCreator.getFaileType(), null, error));
    }
}
