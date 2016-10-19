package com.tywho.appdemo.mvp.presenter;

import com.tywho.appdemo.framework.control.BasePresenter;
import com.tywho.appdemo.mvp.view.DownLoadProgressView;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-05-05 10:09
 */
public abstract class DownLoadProgressPresenter extends BasePresenter<DownLoadProgressView> {
    public abstract void downFile();
}
