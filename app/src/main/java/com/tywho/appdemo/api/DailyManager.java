package com.tywho.appdemo.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tywho.appdemo.framework.helper.SpUtil;
import com.tywho.appdemo.framework.bean.BaseBean;
import com.tywho.appdemo.mvp.model.GalleryBean;
import com.tywho.appdemo.framework.net.HttpFactory;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-05-05 11:14
 */
public class DailyManager implements DailyService {
    private static DailyManager mInstance;

    public static DailyManager getIns() {
        if (mInstance == null) {
            synchronized (DailyManager.class) {
                if (mInstance == null) mInstance = new DailyManager();
            }
        }
        return mInstance;
    }


    @Override
    public Observable<BaseBean<List<GalleryBean>>> treasurebox(String type) {
        return HttpFactory.provideUserService().treasurebox("/mobile/treasurebox.json",type);
    }

    @Override
    public void cacheGallery(String type, BaseBean<List<GalleryBean>> galleryList) {
        SpUtil.saveOrUpdate(type, JSON.toJSONString(galleryList));
    }


    @Override
    public Observable<BaseBean<List<GalleryBean>>> getGallerys(final String type) {
        return Observable.create(new Observable.OnSubscribe<BaseBean<List<GalleryBean>>>() {
            @Override
            public void call(Subscriber<? super BaseBean<List<GalleryBean>>> subscriber) {
                try {
                    subscriber.onStart();
                    String json = SpUtil.find(type);
                    BaseBean<List<GalleryBean>> gallery = JSON.parseObject(json, new TypeReference<BaseBean<List<GalleryBean>>>(){});
                    subscriber.onNext(gallery);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
