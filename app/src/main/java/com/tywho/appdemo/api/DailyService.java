package com.tywho.appdemo.api;

import com.tywho.appdemo.framework.bean.BaseBean;
import com.tywho.appdemo.mvp.model.GalleryBean;

import java.util.List;

import rx.Observable;

/**
 * Created by sunlimiter on 2016/05/05, 0005.
 */
public interface DailyService {

    Observable<BaseBean<List<GalleryBean>>> treasurebox(String type);


    void cacheGallery(String type, BaseBean<List<GalleryBean>> galleryList);


    Observable<BaseBean<List<GalleryBean>>> getGallerys(String type);

}
