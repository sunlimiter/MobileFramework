package com.tywho.appdemo.flux.actioncreators;

import com.tywho.appdemo.api.Api;
import com.tywho.appdemo.flux.CallBackSubscriber;
import com.tywho.appdemo.flux.action.ActionCreator;
import com.tywho.appdemo.flux.dispatcher.Dispatcher;
import com.tywho.appdemo.framework.net.RedirectResponseTransformer;
import com.tywho.appdemo.mvp.model.GalleryBean;

import java.util.List;

/**
 * http://www.tywho.com
 *
 * @author：litianyi
 * @create：2016-07-25 11:07
 */
public class GalleryActionCreator extends ActionCreator {

    /**
     * 加载失败
     */
    public static final String ACTION_LOADING_FAIL = "Gallery_loading_fail";
    /**
     * 加载成功
     */
    public static final String ACTION_LOADING_SUCCESS = "Gallery_loading_success";

    private Api api;

    public GalleryActionCreator(Dispatcher dispatcher) {
        super(dispatcher);
        api = createService(Api.class);
    }

    public void treasurebox(String type) {
        api.treasurebox_(type)
                .compose(new RedirectResponseTransformer<List<GalleryBean>>())
                .subscribe(new CallBackSubscriber<List<GalleryBean>>(this));
    }

    @Override
    public String getSuccessType() {
        return ACTION_LOADING_SUCCESS;
    }

    @Override
    public String getFaileType() {
        return ACTION_LOADING_FAIL;
    }
}