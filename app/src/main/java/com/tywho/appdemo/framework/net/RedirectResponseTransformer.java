package com.tywho.appdemo.framework.net;

import com.tywho.appdemo.framework.bean.BaseBean;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http://www.tywho.com
 * 普通http请求rx方法封装
 * @author：sunlimiter
 * @create：2016-05-04 13:46
 */
public class RedirectResponseTransformer<T> implements Observable.Transformer<BaseBean<T>, T> {
    @Override
    public Observable<T> call(Observable<BaseBean<T>> CommonResponseObservable) {
        return CommonResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .lift(new Observable.Operator<T, BaseBean<T>>() {
                    @Override
                    public Subscriber<? super BaseBean<T>> call(final Subscriber<? super T> subscriber) {
                        return new Subscriber<BaseBean<T>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(BaseBean<T> tBaseBean) {
                                if (tBaseBean.isSuccess()) {
                                    subscriber.onNext(tBaseBean.data);
                                } else {
                                    subscriber.onError(new Throwable(tBaseBean.message));
                                }
                            }

                        };
                    }
                });
    }
}