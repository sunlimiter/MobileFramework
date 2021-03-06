package com.tywho.appdemo.mvp.presenter.impl;

import com.tywho.appdemo.framework.helper.StorageUtil;
import com.tywho.appdemo.framework.net.HttpFactory;
import com.tywho.appdemo.mvp.presenter.DownLoadProgressPresenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 下载断点续传
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-05-10 11:10
 */
public class DownLoadTempImpl extends DownLoadProgressPresenter {

    @Override
    public void downFile() {
        saveFile("7zip.exe")
                .sample(10, TimeUnit.MILLISECONDS)//sample 操作函数可以指定生产者发射数据的最大速度，多余的数据被丢弃了。
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("结束");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        view.successProgress("进度条：" + s);
                        System.out.println("进度条：" + s);
                    }
                });
    }

    public Observable<String> saveFile(final String fileName) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                InputStream input = null;
                OutputStream output = null;
                try {
                    Response<ResponseBody> response = HttpFactory.provideUserService().getFile_().execute();
                    if (response.isSuccessful()) {
                        input = response.body().byteStream();
                        long tlength = response.body().contentLength();

                        File file = new File(StorageUtil.getSDCardPath(), fileName);
                        File tempFile = new File(StorageUtil.getSDCardPath(), fileName + "TEMP_SUFFIX");
                        long previousFileSize = 0;
                        if (tempFile.exists()) {

                        }


                        output = new FileOutputStream(file);
                        byte data[] = new byte[1024];

                        subscriber.onNext("0%");
                        long total = 0;
                        int count;
                        while ((count = input.read(data)) != -1) {
                            total += count;
                            subscriber.onNext(String.valueOf(total * 100 / tlength) + "%");
                            output.write(data, 0, count);
                        }
                        output.flush();
                        output.close();
                        input.close();
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                } finally {
                    if (input != null) {
                        try {
                            input.close();
                        } catch (IOException ioe) {
                        }
                    }
                    if (subscriber != null) {
                        try {
                            output.close();
                        } catch (IOException e) {
                        }
                    }
                }
                subscriber.onCompleted();
            }
        });

    }
}
