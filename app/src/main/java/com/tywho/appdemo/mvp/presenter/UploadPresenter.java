package com.tywho.appdemo.mvp.presenter;

import com.tywho.appdemo.framework.control.BasePresenter;
import com.tywho.appdemo.mvp.view.UploadView;

/**
 * Created by sunlimiter on 2016/05/04, 0004.
 */
public abstract class UploadPresenter extends BasePresenter<UploadView> {
    public abstract void uploadFile();
}
