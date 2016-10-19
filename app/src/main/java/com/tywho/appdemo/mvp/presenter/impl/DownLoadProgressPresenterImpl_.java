package com.tywho.appdemo.mvp.presenter.impl;

import com.tywho.appdemo.framework.helper.StorageUtil;
import com.tywho.appdemo.framework.net.HttpFactory;
import com.tywho.appdemo.mvp.presenter.DownLoadProgressPresenter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http://www.tywho.com
 *
 * @author：litianyi
 * @create：2016-07-08 11:37
 */
public class DownLoadProgressPresenterImpl_  extends DownLoadProgressPresenter {
    public static final int DOWNLOAD_STATUS_INIT = -1;
    public static final int DOWNLOAD_STATUS_PREPARE = 0;
    public static final int DOWNLOAD_STATUS_START= 1;
    public static final int DOWNLOAD_STATUS_DOWNLOADING = 2;
    public static final int DOWNLOAD_STATUS_CANCEL = 3;
    public static final int DOWNLOAD_STATUS_ERROR = 4;
    public static final int DOWNLOAD_STATUS_COMPLETED = 5;
    public static final int DOWNLOAD_STATUS_PAUSE = 6;


    private RandomAccessFile randomAccessFile;
    private long toolSize=6342063;//存放本地，下载文件的总大小
    private long completedSize;
    private int UPDATE_SIZE = 50 * 1024;

    private int downloadStatus = DOWNLOAD_STATUS_INIT;

    @Override
    public void downFile() {
        saveFile("cheguo.apk")
                .sample(10, TimeUnit.MILLISECONDS)//sample 操作函数可以指定生产者发射数据的最大速度，多余的数据被丢弃了。
//              .onBackpressureBuffer(10000)//该方法将告诉Observable发射的数据如果比观察者消费的数据要更快的话，
                // 它必须把它们存储在缓存中并提供一个合适的时间给它们。
                //onBackpressureBuffer 会缓存所有当前无法消费的数据，直到 Observer 可以处理为止。你可以指定缓冲的数量，如果缓冲满了则会导致数据流失败。

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
                InputStream inputStream = null;
                BufferedInputStream bis = null;

                try {
                    randomAccessFile = new RandomAccessFile(new File(StorageUtil.getSDCardPath(), fileName+".tmp"), "rwd");
                    completedSize = randomAccessFile.length();

                    long fileLength = randomAccessFile.length();
                    if(fileLength!=0&&toolSize<=fileLength){
                        downloadStatus = DOWNLOAD_STATUS_COMPLETED;
                        toolSize = completedSize = fileLength;
                        subscriber.onNext(String.valueOf(completedSize * 100 / toolSize) + "%");
                        return;
                    }

                    randomAccessFile.seek(completedSize);
                    Response<ResponseBody> response = HttpFactory.provideUserService().getFile_("http://10.10.13.186/upload/cheguo.apk","bytes=" + completedSize + "-").execute();
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();

                        inputStream = responseBody.byteStream();
                        long tlength = responseBody.contentLength();

                        if(toolSize<=0)toolSize = tlength;


                        bis = new BufferedInputStream(inputStream);
                        byte[] buffer = new byte[2 * 1024];
                        int length =0;
                        int buffOffset = 0;

                        subscriber.onNext((completedSize * 100 / toolSize) + "%");
                        while ((length = bis.read(buffer)) > 0 && downloadStatus != DOWNLOAD_STATUS_CANCEL &&downloadStatus!=DOWNLOAD_STATUS_PAUSE) {
                            randomAccessFile.write(buffer, 0, length);
                            completedSize += length;
                            buffOffset += length;
                            if (buffOffset >= UPDATE_SIZE) {
                                buffOffset = 0;
                                subscriber.onNext((completedSize * 100 / toolSize) + "%");
                            }
                        }
                        subscriber.onNext((completedSize * 100 / toolSize) + "%");
                        new File(StorageUtil.getSDCardPath(), fileName+".tmp").renameTo(new File(StorageUtil.getSDCardPath(), fileName));
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                } finally {
                    if (bis != null) try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (inputStream != null) try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (randomAccessFile != null) try {
                        randomAccessFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        });

    }
}
