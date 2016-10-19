package com.tywho.appdemo.mvp.presenter;

import com.tywho.appdemo.framework.control.BasePresenter;
import com.tywho.appdemo.mvp.view.GalleryView;

/**
 * Created by sunlimiter on 2016/05/04, 0004.
 */
public abstract class GalleryPresenter extends BasePresenter<GalleryView> {
    public abstract void treasurebox(String type);
}
