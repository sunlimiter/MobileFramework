package com.tywho.appdemo.framework.net;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http://www.tywho.com
 * 下载无进度条rx方法封装
 * @author：sunlimiter
 * @create：2016-05-04 17:34
 */
public class DownResponseTransformer implements Observable.Transformer<File, File> {
    @Override
    public Observable<File> call(Observable<File> CommonResponseObservable) {
        return CommonResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .lift(new Observable.Operator<File, File>() {
                    @Override
                    public Subscriber<? super File> call(final Subscriber<? super File> subscriber) {
                        return new Subscriber<File>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(File file) {
                                if (file.exists()) {
                                    subscriber.onNext(file);
                                } else {
                                    subscriber.onError(new Throwable("文件不存在"));
                                }
                            }
                        };
                    }
                });
    }
}