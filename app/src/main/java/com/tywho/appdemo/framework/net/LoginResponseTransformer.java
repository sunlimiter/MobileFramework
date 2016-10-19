package com.tywho.appdemo.framework.net;

import com.tywho.appdemo.mvp.model.TokenBean;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http://www.tywho.com
 * 登录rx方法封装
 * @author：sunlimiter
 * @create：2016-05-04 15:13
 */
public class LoginResponseTransformer implements Observable.Transformer<TokenBean, TokenBean> {
    @Override
    public Observable<TokenBean> call(Observable<TokenBean> CommonResponseObservable) {
        return CommonResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .lift(new Observable.Operator<TokenBean, TokenBean>() {
                    @Override
                    public Subscriber<? super TokenBean> call(final Subscriber subscriber) {
                        return new Subscriber<TokenBean>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(TokenBean tTokenBean) {
                                if (tTokenBean.isSuccess()) {
                                    subscriber.onNext(tTokenBean);
                                } else {
                                    subscriber.onError(new Throwable(tTokenBean.message));
                                }
                            }

                        };
                    }
                });
    }
}