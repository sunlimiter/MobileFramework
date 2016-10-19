package com.tywho.appdemo.mvp.presenter.impl;

import com.tywho.appdemo.api.DailyManager;
import com.tywho.appdemo.framework.bean.BaseBean;
import com.tywho.appdemo.mvp.model.GalleryBean;
import com.tywho.appdemo.framework.net.ProgressSubscriber;
import com.tywho.appdemo.framework.net.RedirectResponseTransformer;
import com.tywho.appdemo.mvp.presenter.GalleryPresenter;
import com.tywho.appdemo.mvp.view.GalleryView;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-05-04 15:26
 */
public class GalleryPresenterImpl extends GalleryPresenter {

    @Override
    public void treasurebox(final String type) {
        //服务端数据源
        Observable<BaseBean<List<GalleryBean>>> network = DailyManager.getIns().treasurebox(type);

        //缓存数据源
        Observable<BaseBean<List<GalleryBean>>> cache = DailyManager.getIns().getGallerys(type);


        //输出数据前缓存到本地
        network = network.doOnNext(new Action1<BaseBean<List<GalleryBean>>>() {
            @Override
            public void call(BaseBean<List<GalleryBean>> listBaseBean) {
                DailyManager.getIns().cacheGallery(type, listBaseBean);
            }
        });

        //先获取缓存里面的数据
        Observable.concat(cache, network)
                .first(new Func1<BaseBean<List<GalleryBean>>, Boolean>() {
                    @Override
                    public Boolean call(BaseBean<List<GalleryBean>> listBaseBean) {
                        //如果本地数据存在的话
                        return listBaseBean != null;
                    }
                })
                .doOnNext(new Action1<BaseBean<List<GalleryBean>>>() {
                    @Override
                    public void call(BaseBean<List<GalleryBean>> listBaseBean) {
                        if (listBaseBean != null) {
                            DailyManager.getIns().cacheGallery(type, listBaseBean);
                        }
                    }
                })
                .compose(new RedirectResponseTransformer<List<GalleryBean>>())
                .subscribe(new ProgressSubscriber<List<GalleryBean>>(view.getContext()) {
                    @Override
                    public void onNext(List<GalleryBean> galleryBeen) {
                        view.success(galleryBeen);
                    }
                });

//        HttpFactory.provideUserService().treasurebox(type)
//                .compose(new RedirectResponseTransformer<List<GalleryBean>>())
//                .subscribe(new ProgressSubscriber<List<GalleryBean>>(context, "加载中...") {
//                    @Override
//                    public void onNext(List<GalleryBean> galleryListBeen) {
//                        galleryView.success(galleryListBeen);
//                    }
//                });
    }
}
