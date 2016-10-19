package com.tywho.appdemo.mvp.presenter.impl;

import com.tywho.appdemo.framework.helper.StorageUtil;
import com.tywho.appdemo.framework.net.DownResponseTransformer;
import com.tywho.appdemo.framework.net.HttpFactory;
import com.tywho.appdemo.framework.net.ProgressSubscriber;
import com.tywho.appdemo.mvp.presenter.DownLoadPresenter;
import com.tywho.appdemo.mvp.view.DownLoadView;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-05-04 16:58
 */
public class DownLoadPresenterImpl extends DownLoadPresenter {

    @Override
    public void downFile() {
        HttpFactory.provideUserService().getFile()
                .flatMap(new Func1<Response<ResponseBody>, Observable<File>>() {
                    @Override
                    public Observable<File> call(Response<ResponseBody> responseBodyResponse) {
                        return saveFile(responseBodyResponse, "7zip.exe");
                    }
                })
                .compose(new DownResponseTransformer())
                .subscribe(new ProgressSubscriber<File>(view.getContext(), "下载中") {
                    @Override
                    public void onNext(File file) {
                        view.success(file.getPath());
                    }
                });

    }

    public Observable<File> saveFile(final Response<ResponseBody> response, final String fileName) {
        return Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                try {
                    File file = new File(StorageUtil.getSDCardPath(), fileName);
                    BufferedSink sink = Okio.buffer(Okio.sink(file));
                    sink.writeAll(response.body().source());
                    sink.close();
                    subscriber.onNext(file);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });

    }

}
