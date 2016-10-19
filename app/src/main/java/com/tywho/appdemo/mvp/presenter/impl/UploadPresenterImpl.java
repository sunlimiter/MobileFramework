package com.tywho.appdemo.mvp.presenter.impl;

import android.content.Context;

import com.tywho.appdemo.framework.bean.BaseBean;
import com.tywho.appdemo.mvp.model.UploadBean;
import com.tywho.appdemo.mvp.presenter.UploadPresenter;
import com.tywho.appdemo.mvp.view.UploadView;
import com.tywho.appdemo.framework.net.HttpFactory;
import com.tywho.appdemo.framework.net.ProgressSubscriber;
import com.tywho.appdemo.framework.net.RedirectResponseTransformer;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-05-04 16:06
 */
public class UploadPresenterImpl extends UploadPresenter {

    @Override
    public void uploadFile() {
//        RequestBody file = RequestBody.create(MediaType.parse("image/jpeg"), new File("/storage/emulated/0/Pictures/MyCameraApp/IMG_20160126_164551.jpg"));
//        RequestBody extname = RequestBody.create(MediaType.parse("text/html"), ".jpg");
//        RequestBody filesource = RequestBody.create(MediaType.parse("text/html"), "10f4fe1edeae11e5b7be086266812821");
//
//        HttpFactory.provideUserService().getHotCars(extname, file, filesource)
//                .compose(new RedirectResponseTransformer<UploadBean>())
//                .subscribe(new ProgressSubscriber<UploadBean>(uploadView.getContext(), "加载中...") {
//                    @Override
//                    public void onNext(UploadBean uploadBean) {
//                        uploadView.success(uploadBean);
//                    }
//                });


        String[] names = new String[]{"/storage/emulated/0/Pictures/MyCameraApp/IMG_20160126_164551.jpg", "/storage/emulated/0/Pictures/MyCameraApp/IMG_20160126_164551.jpg"};
        Observable.from(names)
//                .map(new Func1<String, String>() {
//                    @Override
//                    public String call(String s) {
//                        String value_ = s + "_value";
//                        return value_;
//                    }
//                })
                .flatMap(new Func1<String, Observable<BaseBean<UploadBean>>>() {
                    @Override
                    public Observable<BaseBean<UploadBean>> call(String s) {
                        RequestBody file = RequestBody.create(MediaType.parse("image/jpeg"), new File(s));
                        RequestBody extname = RequestBody.create(MediaType.parse("text/html"), ".jpg");
                        RequestBody filesource = RequestBody.create(MediaType.parse("text/html"), "10f4fe1edeae11e5b7be086266812821");
                        return HttpFactory.provideUserService().getHotCars(extname, file, filesource);
                    }
                })
                .compose(new RedirectResponseTransformer<UploadBean>())
                .subscribe(new ProgressSubscriber<UploadBean>(view.getContext(), "加载中...") {
                    @Override
                    public void onNext(UploadBean uploadBean) {
                        System.out.println("uploadBean:"+uploadBean.result);
                        view.success(uploadBean);
                    }
                });
    }

    private Observable<File> listFiles(File f) {
        if (f.isDirectory()) {
            return Observable.from(f.listFiles()).flatMap(new Func1<File, Observable<File>>() {
                @Override
                public Observable<File> call(File file) {
                    return listFiles(file);
                }
            });
        } else {
            return Observable.just(f);
        }
    }

}
